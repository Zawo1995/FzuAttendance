package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.model.Userinfo;

import java.util.List;
import java.util.Map;

/**
 * Created by CLY on 2018/5/1.
 */
public interface UserinfoService extends IService<Userinfo> {

  List<Map<Object,Object>> getAllUser(Map params);

  Integer addUser(Map<String, Object> params);

  boolean delUser(List<Integer> params);

  int checkExist(String userCode);
}
