package com.framework.socketserver.listening;//package com.fzu.edu.framework.socketserver.listening;
//
//import com.alibaba.fastjson.JSON;
//import com.fzu.edu.framework.socketserver.SocketServer;
//import com.fzu.edu.utils.Common;
//
//import org.springframework.web.socket.TextMessage;
//
//import javax.servlet.http.HttpSession;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.net.URLDecoder;
//import java.util.*;
//
///**
// * Created by huhu on 2018/12/4.
// */
//public class EquipReturn extends Listening {
//    public static Integer servicePort;
//    public static Map<String,String[]> Reader =  new HashMap<>();;//存放Reader里的配置好的客户端ip、读写器ip、端口号
//    public static HttpSession session;//用来给指定用户发数据的session
//
//    @Override
//    public void listening(SocketServer.Config config) {
//        try {
//            Listener listener = new Listener(config.port);
//            servicePort = config.port;
//            //获得ReaderConfig文件里的IP和Port
//            String path = this.getClass().getResource("/").getPath();
//            path = URLDecoder.decode(path, "utf-8");
//            File configFile = new File(path + "reader-config");
//            BufferedReader reader = new BufferedReader(new FileReader(configFile));
//            String s;
//            while ((s = reader.readLine()) != null){
//                String[] readerStr = s.trim().split("[ ]*=[ ]*");
//                String[] r = new String[2];
//                r[0] = readerStr[1]; r[1] = readerStr[2];
//                Reader.put(readerStr[0],r);
//            }
//            reader.close();
//            Connected.Operator operator = new Connected.Operator() {
//                @Override
//                public boolean start(String str) {
////                    String rfid = str.substring(0, str.indexOf("24") + 2);//$ = '24'
//                    //用WebSocket将信息发送给Web客户端
//                    sendMsg(str);
//                    return true;
//                }
//            };
//            listener.read(operator);
//            listener.start();
//        } catch (Exception e) {
//            log.error(e);
//        }
//    }
//
//    private void sendMsg(String str) {
//        if (str == null) return;
//        Map m = new HashMap();
//        m.put("return-equip",str);
//        Common.getWebSocketHandler().sendMessage(session, new TextMessage(JSON.toJSONString(m)));
//    }
//
//    private void readAvailReader(){//读取可用的读写器的信息 ip:port
//
//    }
//}
