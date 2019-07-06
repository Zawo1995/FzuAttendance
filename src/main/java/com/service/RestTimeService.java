package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.model.RestTime;
import com.model.School;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface RestTimeService extends IService<RestTime>{
  List<Map<String, Object>> getRestTimeBySchoolId(Integer id);
  Integer updateSchoolRestTime(RestTime restTime) ;
}
