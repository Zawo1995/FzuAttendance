package com.service.impl;//package com.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.service.impl.ServiceImpl;
//import com.dao.UserinfoMapper;
//import com.model.Userinfo;
//import com.service.UserinfoService;
//import com.utils.MD5Util;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Lin on 2018/10/23.
// */
//@Service("userinfoService")
//@Transactional(rollbackFor = Exception.class)
//public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper,Userinfo> implements UserinfoService {
//    @Resource
//    UserinfoMapper userinfoMapper;
//
//    private Logger log = Logger.getLogger(UserinfoServiceImpl.class);
//
//    public List<Map<Object, Object>> getAllUser(Map params) {
//        List<Map<Object, Object>> userList = userinfoMapper.selectAllUser(params);
//        return userList;
//    }
//
//    public Integer addUser(Map<String, Object> params) {
//        Userinfo userinfo = JSON.parseObject(JSON.toJSONString(params), Userinfo.class);
//        try {
//            userinfo.setPassword(MD5Util.md5Encrypt(userinfo.getPassword(true)));
//        }catch (Exception e){
//            log.error(e);
//        }
//        if (userinfo.getId() == null) return userinfoMapper.insert(userinfo);
//        else return userinfoMapper.updateById(userinfo);
//    }
//
//    public boolean delUser(List<Integer> ids) {
//        List<Userinfo> userinfos = new ArrayList<>();
//        for (Integer id : ids){
//            Userinfo userinfo = new Userinfo();
//            userinfo.setId(id);
//            userinfo.setFlag(1);
//            userinfos.add(userinfo);
//        }
//        return updateBatchById(userinfos);
//    }
//
//    @Override
//    public int checkExist(String userCode) {
//        return userinfoMapper.checkExistCode(userCode);
//    }
//
//}
