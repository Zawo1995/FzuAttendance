package com.framework.socketserver.listening;//package com.fzu.edu.framework.socketserver.listening;
//
//import com.fzu.edu.dao.AlarmMapper;
//import com.fzu.edu.dao.EquipmentInfoMapper;
//import com.fzu.edu.framework.socketserver.SocketServer;
//import com.fzu.edu.model.Alarm;
//import com.fzu.edu.utils.SpringContextUtil;
//
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by huhu on 2018/12/4.
// */
//public class OutStock extends Listening {
//    @Override
//    public void listening(SocketServer.Config config) {
//        try {
//            Listener listener = new Listener(config.port);
//            Connected.Operator operator = new Connected.Operator() {
//                @Override
//                public boolean start(String str) {
//                    String[] values = str.split(":");
//                    String RFID = values[0];    //  RFID号
//                    Boolean isOut = Boolean.parseBoolean(values[1]);
//                    if (isOut) {
//                        EquipmentInfoMapper equipmentInfoMapper = (EquipmentInfoMapper) SpringContextUtil.getBean(EquipmentInfoMapper.class);
//                        Map m = new HashMap();
//                        m.put("Rifd", RFID);
//                        Integer check = equipmentInfoMapper.outEquipCheck(m);
//                        if (check < 1) {
//                            AlarmMapper alarmMapper = (AlarmMapper) SpringContextUtil.getBean(AlarmMapper.class);
//                            Alarm alarm = new Alarm();
//                            alarm.setState(1);
//                            alarm.setDate(new Date().getTime());
//                            alarm.setRemark("装备非法出库");
//                            alarm.setConnectId(RFID);
//                            alarmMapper.insert(alarm);
//                            try{
//                                Socket socket = new Socket("192.168.1.30", 12501);
//                                OutputStream outputStream = socket.getOutputStream();
//                                PrintWriter printWriter = new PrintWriter(outputStream);
//                                printWriter.write("false");
//                                printWriter.flush();
//                                socket.close();
//                            }catch (Exception e){
//                                System.out.println("连接失败!");
//                            }
//                        }
//                    }
//                    return true;
//                }
//            };
//            listener.read(operator);
//            listener.start();
//        } catch (Exception e) {
//            log.error(e);
//        }
//    }
//}
