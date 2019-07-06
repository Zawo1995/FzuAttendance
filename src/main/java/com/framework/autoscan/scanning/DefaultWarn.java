package com.framework.autoscan.scanning;

import com.alibaba.fastjson.JSON;
import com.utils.Common;
import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huhu on 2018/12/10.
 */
public abstract class DefaultWarn extends Thread {

    private Logger log = Logger.getLogger(this.getClass());
    protected Config config;

    public abstract class Config{
        private int times = -1;
        private int defaultSleepTimes = 30000;

        public Config() {
        }

        public Config(int defaultSleepTimes) {
            this.defaultSleepTimes = defaultSleepTimes;
        }

        public Config(int times, int defaultSleepTimes) {
            this.times = times; //  线程执行次数
            this.defaultSleepTimes = defaultSleepTimes; //  线程休眠时间
        }

        public abstract Map<String, Options> getWarn();

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getDefaultSleepTimes() {
            return defaultSleepTimes;
        }

        public void setDefaultSleepTimes(int defaultSleepTimes) {
            this.defaultSleepTimes = defaultSleepTimes;
        }
    }

    public class Options{
        private String methodName;
        private Boolean autoRun = false;    //  线程启动后立即执行
        private String type;    //  消息类型

        public Options(String type, String methodName, Boolean autoRun) {
            this.methodName = methodName;
            this.autoRun = autoRun;
            this.type = type;
        }

        public Options(String type, String methodName) {
            this.methodName = methodName;
            this.type = type;
        }
    }

    public DefaultWarn(){
        config = setConfig();
    }

    public DefaultWarn(int times) {
        config = setConfig();
        config.times = times;
    }

    @Override
    public void run() {
        try {
            while (-1 == config.times || 0 < config.times--){
                int error = 0;
                try {
                    defaultScan();
                    error = 0;
                }catch (Exception e){
                    log.warn(e);
                    //  错误300次后停止
                    if (error++ > 300) throw e;
                }
                Thread.sleep(config.defaultSleepTimes);
            }
        }catch (Exception e){
            log.error(e);
        }
    }

    /**
     * 线程启动后自动默认执行
     */
    private void defaultScan() {
        Map<String, Options> method = config.getWarn();
        Map<String, Map<String, Object>> message = new HashMap<>();
        for (Map.Entry<String, Options> entry : method.entrySet()){
            try {
                Options options = entry.getValue();
                if (!options.autoRun) continue;
                setMessages(options, message, entry.getKey());
            }catch (Exception e){
                log.warn(e);
            }
        }
        sendMessage(message);
    }

    /**
     * 设置发送的数据
     * @param options
     * @param messages
     * @param type
     * @throws Exception
     */
    private void setMessages(Options options, Map<String, Map<String, Object>> messages, String type) throws Exception{
        Object o = scan(options.methodName);
        if (o != null) {
            Map<String, Object> values = messages.get(options.type);
            if (values == null){
                values = new HashMap<>();
                messages.put(options.type, values);
            }
            values.put(type, o);
        }
    }
    /**
     * 扫描所有警报
     * @throws Exception
     */
    public void scan() {
        Map<String, Options> method = config.getWarn();
        Map<String, Map<String, Object>> message = new HashMap<>();
        for (Map.Entry<String, Options> entry : method.entrySet()){
            try {
                setMessages(entry.getValue(), message, entry.getKey());
            }catch (Exception e){
                log.warn(e);
            }
        }
        sendMessage(message);
    }

    /**
     * 按类别扫描警报
     * @param types
     */
    public void scan(String[] types) {
        Map<String, Map<String, Object>> message = new HashMap<>();
        for (String type : types){
            try {
                setMessages(config.getWarn().get(type), message, type);
            }catch (Exception e){
                log.warn(e);
            }
        }
       sendMessage(message);
    }

    /**
     * 执行指定方法
     * @param methodName
     * @return  执行后的结果
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object scan(String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        if (methodName == null || methodName.trim().equals("")) return null;
        Method method = this.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        Object o = method.invoke(this);
        method.setAccessible(false);
        return o;
    }
    private void sendMessage(Map<String, Map<String, Object>> messages){
        if (messages.size() == 0) return;
        Common.getWebSocketHandler().sendMessageToAllUsers(new TextMessage(JSON.toJSONString(messages)));
    }

    /*
    配置警报名称和警报执行的方法
     */
    public abstract Config setConfig();
}
