package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.SignDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by lyq-pc on 2019/6/28.
 */
public interface SignDetailService  extends IService<SignDetail> {
  ResponseResult addOne(SignDetail signDetail);
  List<Map<String,Object>> getSignList(Integer signId);
  ResponseResult selectSignDetailById(Map params);
}
