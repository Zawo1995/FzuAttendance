package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.SignIn;

import java.util.List;
import java.util.Map;


/**
 * Created by lyq-pc on 2019/6/25.
 */
public interface SignService  extends IService<SignIn> {
  ResponseResult getSignList(SignIn signIn);
  Integer addOne( SignIn signIn);
  Integer updateByEnd(SignIn signIn);
  ResponseResult getAllSignTask(Map params);
  List<Map<String, Object>> getSignId(Map params);
}
