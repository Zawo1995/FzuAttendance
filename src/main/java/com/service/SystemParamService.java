package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.model.School;
import com.model.SystemParam;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface SystemParamService extends IService<SystemParam>{
  List<Map<String, Object>> getList(Map params);
  Integer editSchool(SystemParam systemParam);
}
