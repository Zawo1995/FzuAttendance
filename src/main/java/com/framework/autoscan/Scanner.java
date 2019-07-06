package com.framework.autoscan;

import com.framework.autoscan.scanning.DefaultWarn;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by huhu on 2018/12/10.
 */
@Component
public class Scanner implements ApplicationListener<ContextRefreshedEvent> {

    private static Class<?>[] warnList;
    private static Logger log = Logger.getLogger(Scanner.class);

    /**
     * 注册警报类，如果有新增警报类，只需要在这里注册一下
     */


    /**
     * 配置
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            defaultWarn(-1);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 主动警报
     * @param times 请求警报次数
     */
    public static void defaultWarn(int times) {
        for (Class<?> cls : warnList){
            try {
                DefaultWarn warn = (DefaultWarn) cls.getConstructor(int.class).newInstance(times);
                warn.start();
            } catch (Exception e){
                log.warn(e);
            }
        }
    }

    /**
     * 主动警报
     */
    public static void warn() {
        for (Class<?> cls : warnList){
            try {
                DefaultWarn warn = (DefaultWarn) cls.newInstance();
                warn.scan();
            }catch (Exception e){
                log.warn(e);
            }
        }
    }

    /**
     * 主动警报
     * @param types 警报类型
     */
    public static void warn(String[] types) {
        for (Class cls : warnList){
            try {
                DefaultWarn warn = null;
                warn = (DefaultWarn) cls.getConstructor(int.class).newInstance(-1);
                warn.scan(types);
            } catch (Exception e) {
                log.warn(e);
            }
        }
    }
}
