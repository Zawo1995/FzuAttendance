package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CollegeMapper;
import com.dao.RestTimeMapper;
import com.dao.SchoolMapper;
import com.dao.SystemParamMapper;
import com.model.College;
import com.model.RestTime;
import com.model.School;
import com.model.SystemParam;
import com.service.SchoolService;
import com.service.SystemParamService;
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
@Service("systemParamService")
@Transactional(rollbackFor = Exception.class)
public class SystemParamServiceImpl extends ServiceImpl<SystemParamMapper, SystemParam> implements SystemParamService {
  @Resource
  SystemParamMapper systemParamMapper;

  private Logger log = Logger.getLogger(SystemParamServiceImpl.class);

  public List<Map<String, Object>> getList(Map params) {
    EntityWrapper<SystemParam> ew = new EntityWrapper<>();
    if(params.get("id") != null){
      ew.eq("id", Integer.valueOf(params.get("id").toString()));
    }
    return systemParamMapper.selectMaps(ew);
  }

  public Integer editSchool(SystemParam systemParam) {
    return systemParamMapper.updateById(systemParam);
  }

}
