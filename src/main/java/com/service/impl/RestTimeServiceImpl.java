package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CollegeMapper;
import com.dao.RestTimeMapper;
import com.dao.SchoolMapper;
import com.model.College;
import com.model.RestTime;
import com.model.School;
import com.service.RestTimeService;
import com.service.SchoolService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("resttimeService")
@Transactional(rollbackFor = Exception.class)
public class RestTimeServiceImpl extends ServiceImpl<RestTimeMapper, RestTime> implements RestTimeService {

  @Resource
  RestTimeMapper restTimeMapper;
  private Logger log = Logger.getLogger(RestTimeServiceImpl.class);

  @Override
  public List<Map<String, Object>> getRestTimeBySchoolId(Integer id) {
    EntityWrapper<RestTime> ew = new EntityWrapper<>();
    ew.eq("school_id",id);
    return restTimeMapper.selectMaps(ew);
  }

  @Override
  public Integer updateSchoolRestTime(RestTime restTime) {
    return restTimeMapper.updateById(restTime);
  }
}
