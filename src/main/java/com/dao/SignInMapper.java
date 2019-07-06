package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.controller.exceptions.ResponseResult;
import com.model.SignIn;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SignInMapper extends BaseMapper<SignIn> {
  List<Map<String,Object>> getAllSignTask(Map params);
  List<Map<String, Object>> getSignId(Map params);
}
