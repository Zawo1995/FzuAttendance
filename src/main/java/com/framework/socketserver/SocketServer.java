package com.framework.socketserver;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huhu on 2018/12/4.
 */

public abstract class SocketServer implements ApplicationListener<ContextRefreshedEvent> {
    public static class Config{
        public int port;
        private Config(int port){
            this.port = port;
        }
    }
    private Logger log = Logger.getLogger(this.getClass());
    private static Map<String, Config> configs = new HashMap<>();

    /**
     * 代码主体
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //  读配置文件
        getConfig();
        List<Class> list = listeningList();
        for (Class c : list){
            listening(c);
        }
    }

    public abstract List<Class> listeningList();

    private void getConfig(){
        try {
            String path = this.getClass().getResource("/").getPath();
            path = URLDecoder.decode(path, "utf-8");
            File configFile = new File(path + "socket-server.config");
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String s;
            while ((s = reader.readLine()) != null){
                String[] configStr = s.trim().split("[ ]*=[ ]*");
                configs.put(configStr[0], new Config(Integer.valueOf(configStr[1])));
            }
            reader.close();
        } catch (Exception e){
            log.error(e);
        }
    }

    protected <C> void listening(Class<C> cls){
        try {
            C listening = cls.newInstance();
            String simpleName = cls.getSimpleName();
            Config config = configs.get(simpleName);
            Method method = cls.getMethod("listening", Config.class);
            method.invoke(listening, config);
        } catch (Exception e){
            log.error(e);
        }
    }

    public static Map<String, Config> getConfigs() {
        return configs;
    }
}
