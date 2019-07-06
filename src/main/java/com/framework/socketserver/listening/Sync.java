package com.framework.socketserver.listening;//package com.fzu.edu.framework.socketserver.listening;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.service.IService;
//import com.fzu.edu.dao.StorageAreaMapper;
//import com.fzu.edu.framework.socketserver.SocketServer;
//import com.fzu.edu.model.*;
//import com.fzu.edu.model.inventorytask.InventoryTask;
//import com.fzu.edu.service.EquipmentInfoService;
//import com.fzu.edu.service.InventoryEquipmentDetailService;
//import com.fzu.edu.service.InventoryTaskService;
//import com.fzu.edu.service.UserinfoService;
//import com.fzu.edu.utils.Common;
//import com.fzu.edu.utils.FatherToChild;
//import com.fzu.edu.utils.SpringContextUtil;
//import doorsocket.server.Connected;
//import doorsocket.server.Listener;
//import org.apache.log4j.Logger;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by huhu on 2019/1/8.
// * 监听pda连接，并与pda进行数据同步
// */
//public class Sync extends Listening {
//
//    public final static class UD {
//        final static String
//            upload = "webUploadDataCmd",  //  WEB上传
//            uploadEnd = "dataEndCmd",  //  WEB上传结束
//            download = "webDownloadDataCmd",    //  WEB下载
//            others = "webDownloadPicCmd",  //  WEB下载其他图片
//            init = "webInitDataCmd",    //  WEB上传初始化数据
//            initEnd = "initEndCmd",    //  WEB上传初始化数据结束
//            dataBegin = "data",
//            dataEnd = "data end",
//            imageBegin = "image:",
//            imageEnd = "image end",
//            imageReady = "imageReady",
//            allImageEnd = "images end";
////            wait = "OK";  //  已收到数据继续发送数据
////        static boolean await = false;
//    }
//
//    private final static int limitBytes = 16384;
//
//    private static Connected.Operator operator;
////    private static ArrayList<File> files = new ArrayList<>();   //  接收到的文件
//
//    private static EquipmentInfoService equipmentInfoService;
//    private static UserinfoService userinfoService;
//    private static InventoryEquipmentDetailService inventoryEquipmentDetailService;
//    private static StorageAreaMapper storageAreaMapper;
//    private static InventoryTaskService inventoryTaskService;
//
//    private static Logger log = Logger.getLogger(Sync.class);
//
//    /**
//     * 获得装备信息和盘点详细信息对应的service
//     * 数据接收设置为未开始
//     * 接收文件数组清空
//     * 通知收集器准备接收数据
//     */
//    public static void start() {
//        if (equipmentInfoService == null)
//            equipmentInfoService = (EquipmentInfoService) SpringContextUtil.getBean(EquipmentInfoService.class);
//        if (userinfoService == null)
//            userinfoService = (UserinfoService) SpringContextUtil.getBean(UserinfoService.class);
//        if (inventoryEquipmentDetailService == null)
//            inventoryEquipmentDetailService = (InventoryEquipmentDetailService) SpringContextUtil.getBean(InventoryEquipmentDetailService.class);
//        if (storageAreaMapper == null)
//            storageAreaMapper = (StorageAreaMapper) SpringContextUtil.getBean(StorageAreaMapper.class);
//        if (inventoryTaskService == null)
//            inventoryTaskService = (InventoryTaskService) SpringContextUtil.getBean(InventoryTaskService.class);
//    }
//
//    /**
//     * 获得装备信息和盘点详细信息对应的service
//     * 数据接收设置为未开始
//     * 接收文件数组清空
//     * 通知收集器准备发送数据
//     */
//    public static void download() {
//        start();
//        writeLine(UD.download);
//    }
//
//    /**
//     * 获得装备信息和盘点详细信息对应的service
//     * 获得并发送已修改装备信息，发送盘点任务
//     * 发送装备图片
//     */
//    public static void upload() {
//        start();
//        Map<String, List<List>> map = getAndSendBasicData(UD.upload);
//        sendImages(map.get("ei"));
//        writeLine(UD.uploadEnd);
//    }
//
//    /**
//     * 上传初始化数据（用户信息表，货架区域表，加上全部upload的数据）
//     */
//    public static void init() {
//        start();
//        Map<String, List<List>> map = getAndSendBasicData(UD.init);
//        sendImages(map.get("ei"));
//        writeLine(UD.initEnd);
//    }
//
//    /**
//     * 接收其他图片
//     */
//    public static void others() {
//        start();
//        writeLine(UD.others);
//    }
//
//    public static void writeLine(String s) {
//        /*try {
//            while (UD.await){
//                Thread.sleep(250);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }*/
//        operator.writeLine(s);
////        UD.await = true;
//    }
//
//
//    @Override
//    public void listening(SocketServer.Config config) {
//        try {
//            Listener listener = new Listener(config.port);
//            operator = new Connected.Operator() {
//                private OutputStream imageOutput = null;
//                private long fileLength = 0;
//
//                @Override
//                public boolean start(String str) {
//                    try {
//                        str = str.trim();
//                        if (str.equals(UD.dataBegin)) {
//                            //  TODO 开始接收数据
//                        } else if (str.equals(UD.dataEnd)) {
//                            //  TODO 结束接收数据
//                        } else if (str.equals(UD.imageEnd)){
//                            imageOutput.close();
//                            imageOutput = null;
//                            fileLength = 0;
//                        }/* else if(str.equals(UD.wait)){
//                            UD.await = false;
//                        }*//* else if (str.equals(UD.imageReady)){
//                            UD.readyImage = true;
//                        }*/ else {
//                            if (str.indexOf("ei:") == 0) {
//                                //  接收数据为装备信息数据
//                                str = str.substring(3);
//                                List<EquipmentInfo> data = JSON.parseArray(str, EquipmentInfo.class);
//                                equipmentInfoService.insertOrUpdateBatch(data);
//                            } else if (str.indexOf("ied:") == 0) {
//                                //  接收数据为盘点详细信息数据
//                                str = str.substring(4);
//                                List<InventoryEquipmentDetail> data = JSON.parseArray(str, InventoryEquipmentDetail.class);
//                                inventoryEquipmentDetailService.insertOrUpdateBatch(data);
//                                inventoryEquipmentDetailService.setAlarms(data);
//                            } else if (str.indexOf(UD.imageBegin) == 0) {
//                                //  接收图片开始
//                                try {
//                                    String imageName = str.substring(6);
//                                    Map m = getImageOutput(imageName);
//                                    fileLength = (long) m.get("fileLength");
//                                    imageOutput = (OutputStream) m.get("outputStream");
//                                    operator.read(TYPE.BYTE);
//                                } catch (IOException ioe) {
//                                    log.error(ioe);
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                        log.error(e);
//                    }
//                    return true;
//                }
//
//                @Override
//                protected boolean start(byte b) {
//                    try {
//                        if (--fileLength > 0) imageOutput.write(b);
//                        else {
//                            start(UD.imageEnd);
//                            return false;
//                        }
//                    }catch (IOException ioe){
//                        ioe.printStackTrace();
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
//
//    /**
//     * 监听获得图片
//     *
//     * @param imageName 图片路径和名称
//     * @throws IOException
//     */
//    private Map getImageOutput(String imageName) throws IOException {
//        //  图片存放路径检测
//        String path = getPath(imageName);
//        long fileLength = getLength(imageName);
//        File file = new File(path);
//        path = path.replaceAll("\\\\", "/");
//        //  文件夹路径
//        int lastIndex = path.lastIndexOf("/");
//        if (lastIndex != -1){
//            String dirPath = path.substring(0, path.lastIndexOf("/"));
//            File d = new File(dirPath);
//            if (!d.exists()) d.mkdirs();
//        }
//        if (!file.exists()) file.createNewFile();
//        OutputStream outputStream = new FileOutputStream(file);
//        return new HashMap(){{
//            put("outputStream", outputStream);
//            put("fileLength", fileLength);
//        }};
//    }
//
//    private long getLength(String path) {
//        int lastIndex = path.lastIndexOf(":");
//        String length = path.substring(lastIndex + 1);
//        return Long.parseLong(length);
//    }
//
//    private static String getPath(String path) {
//        String rootPath = Sync.class.getResource("/../../").getPath();
//        path = path.substring(8);
//        int lastIndex = path.lastIndexOf(":");
//        if (lastIndex != -1) path = path.substring(0, lastIndex);
//        return rootPath + path;
//    }
//
//    /**
//     * 发送和接收基础数据
//     *
//     * @param type
//     */
//    private static Map<String, List<List>> getAndSendBasicData(String type) {
//        Map<String, List<List>> map = getBasicData(type);
//        writeLine(type);
//        sendBasicData(map, type);
//        return map;
//    }
//
//    /**
//     * 获得基础数据
//     *
//     * @param type
//     * @return
//     */
//    private static Map<String, List<List>> getBasicData(String type) {
//        Map<String, List<List>> map = new HashMap<>();
//        if (type.equals(UD.init)) {
//            //  全部基础数据
//            List<Userinfo> userinfos = userinfoService.selectList(
//                new EntityWrapper<Userinfo>()
//                    .setSqlSelect("id, user_code, name, sex, phone, password")
//                    .eq("role", 2)
//                    .eq("flag", 0)
//            );
//            try {
//                List<UserInfoHasPassword> userInfoHasPasswords = FatherToChild.to(userinfos, UserInfoHasPassword.class);
//                map.put("ui", Common.splitArray(userInfoHasPasswords, 25));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            List<StorageArea> storageAreas = storageAreaMapper.selectList(
//                new EntityWrapper<StorageArea>()
//                    .setSqlSelect("id, RFID, floor, warehouse, shelf_area, volume")
//                    .eq("flag", 0)
//            );
//            map.put("sa", Common.splitArray(storageAreas, 25));
//            List<EquipmentInfo> equipmentInfos = equipmentInfoService.selectList(
//                new EntityWrapper<EquipmentInfo>()
//                    .setSqlSelect("id, name, code, type, model, RFID, storage_area_id, state, remark, user_id, batch_code, equipment_photo, in_date")
//                    .eq("flag", 0)
//            );
//            map.put("ei", Common.splitArray(equipmentInfos, 25));
//            List<InventoryTask> inventoryTasks = inventoryTaskService.selectList(
//                new EntityWrapper<InventoryTask>()
//                    .setSqlSelect("id, task_code, date, check_position, user_id")
//                    .eq("flag", 0)
//            );
//            map.put("it", Common.splitArray(inventoryTasks, 25));
//        } else if (type.equals(UD.upload)) {
//            //  已修改装备信息和盘点任务
//            List<EquipmentInfo> equipmentInfos = equipmentInfoService.selectList(
//                new EntityWrapper<EquipmentInfo>()
//                    .setSqlSelect("id, name, code, type, model, RFID, storage_area_id, state, remark, user_id, batch_code, equipment_photo, in_date")
//                    .eq("flag", 0)
//                    .eq("changed", 1)
//            );
//            map.put("ei", Common.splitArray(equipmentInfos, 25));
//            List<InventoryTask> inventoryTasks = inventoryTaskService.selectList(
//                new EntityWrapper<InventoryTask>()
//                    .setSqlSelect("id, task_code, date, check_position, user_id")
//                    .eq("flag", 0)
//                    .eq("changed", 1)
//            );
//            map.put("it", Common.splitArray(inventoryTasks, 25));
//        }
//        return map;
//    }
//
//    /*
//        发送基础数据
//     */
//    private static void sendBasicData(Map<String, List<List>> map, String type) {
//        writeLine(UD.dataBegin);
//        List<EquipmentInfo> equip = new ArrayList<>();
//        List<InventoryTask> inventory = new ArrayList<>();
//        for (Map.Entry<String, List<List>> entry : map.entrySet()) {
//            String name = entry.getKey();
//            List<List> data = entry.getValue();
//            for (List list : data) {
//                writeLine(name + ":" + JSON.toJSONString(list));
//                if (!type.equals(UD.init)) {
//                    //  将装备和盘点任务改变为未改变
//                    if (name.equals("ei")) {
//                        for (Object o : list) {
//                            EquipmentInfo ei = (EquipmentInfo) o;
//                            if (ei.getChanged() != null && ei.getChanged() == 1) {
//                                equip.add(new EquipmentInfo(ei.getId(), 0));
//                            }
//                        }
//                    } else if (name.equals("ti")) {
//                        for (Object o : list) {
//                            InventoryTask it = (InventoryTask) o;
//                            if (it.getChanged() == 1) {
//                                inventory.add(new InventoryTask(it.getId(), 0));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (!type.equals(UD.init)) {
//            if (equip.size() > 0) equipmentInfoService.updateBatchById(equip);
//            if (inventory.size() > 0) inventoryTaskService.updateBatchById(inventory);
//        }
//        writeLine(UD.dataEnd);
//    }
//
//    public static void getAndSendImages() {
//        List<EquipmentInfo> equipmentInfos = equipmentInfoService.selectList(
//            new EntityWrapper<EquipmentInfo>()
//                .setSqlSelect("id, name, code, type, model, RFID, storage_area_id, state, remark, user_id, batch_code, equipment_photo, date")
//                .eq("flag", 0)
//        );
//        sendImages(Common.splitArray(equipmentInfos, 25));
//    }
//
//
//    private static void sendImages(List<List> equipmentInfoList) {
//        for (List<EquipmentInfo> equipmentInfos : equipmentInfoList) {
//            for (EquipmentInfo equipmentInfo : equipmentInfos) {
//                try {
//                    String name = equipmentInfo.getEquipmentPhoto();
//                    if (name == null || name.trim().equals("")) continue;
//                    File file = new File(getPath(name));
//                    if (!file.exists()) continue;
//                    writeLine(UD.imageBegin + name + ":" + file.length());
//                    InputStream inputStream = new FileInputStream(file);
//                    byte[] bytes;
//                    int len;
//                    while (true) {
//                        bytes = new byte[limitBytes];
//                        if ((len = inputStream.read(bytes)) == -1) break;
////                        operator.getDataOutputStream().write(bytes, 0, len);
////                        operator.getDataOutputStream().flush();
//                        operator.getDataOutputStream().write(bytes, 0, len);
//                        operator.getDataOutputStream().flush();
//                    }
//                    writeLine(UD.imageEnd);
//                    inputStream.close();
//
//                } catch (IOException ioe) {
//                    log.error(ioe);
//                }
//
//            }
//        }
////        operator.writeLine(UD.allImageEnd);
//    }
//
//}
