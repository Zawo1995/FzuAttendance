package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.CollegeMapper;
import com.dao.RestTimeMapper;
import com.dao.SchoolMapper;
import com.model.College;
import com.model.RestTime;
import com.model.School;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.service.SchoolService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("schoolService")
@Transactional(rollbackFor = Exception.class)
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
  @Resource
  SchoolMapper schoolMapper;

  @Resource
  CollegeMapper collegeMapper;

  @Resource
  RestTimeMapper restTimeMapper;

  public ResponseResult getList(SchoolQuery schoolQuery) {
    EntityWrapper<School> ew = new EntityWrapper<>();
    if (schoolQuery.getSchoolName() != null) {
      ew.like("school_name", schoolQuery.getSchoolName());
    }
    if (schoolQuery.getSchoolAddress() != null) {
      ew.like("school_address", schoolQuery.getSchoolAddress());
    }
    return RestResultGenerator.genResult(schoolMapper.selectMaps(ew), ResultCode.SUCCESS);
  }


  public ResponseResult addOne(SchoolAdd schoolAdd) {
    //同时添加本学校的作息时间到作息时间表
    if (schoolAdd.getSchoolName() == null) {
      throw new UserFriendlyException(ResultCode.SCHOOL_NAME_NULL);
    }
    EntityWrapper<School> ew = new EntityWrapper<>();
    ew.eq("school_name", schoolAdd.getSchoolName());
    if (schoolMapper.selectCount(ew) > 0) {
      throw new UserFriendlyException(ResultCode.SCHOOL_NAME_EXIST);
    }

    School s = new School();
    s.setSchoolName(schoolAdd.getSchoolName());
    s.setSchoolAddress(schoolAdd.getSchoolAddress());
    s.setSchoolDetail(schoolAdd.getSchoolDetail());

    schoolMapper.insert(s);
    RestTime r = new RestTime();
    r.setSchoolId(s.getId());
    restTimeMapper.insert(r);
    return RestResultGenerator.genResult(null, ResultCode.ADD_SUCCESS);
  }

  public ResponseResult deleteSchools(SchoolDel schoolDel) {
    //删除该学校下的所有子College
    if (schoolDel.getIds() == null || schoolDel.getIds().size() == 0) {
      throw new UserFriendlyException(ResultCode.SCHOOL_DELETE_NULL);
    }
    for (Integer id : schoolDel.getIds()) {
      if (schoolMapper.selectCount(new EntityWrapper<School>().eq("id", id)) == 0) {
        throw new UserFriendlyException(ResultCode.SCHOOL_NOT_EXIST);
      }
    }
    List<Integer> ids = schoolDel.getIds();
    for (Integer id : ids) {
      EntityWrapper<College> ew = new EntityWrapper<>();
      ew.eq("school_id", id);
      List<College> l = collegeMapper.selectList(ew);
      List<Integer> collegeIds = new ArrayList<>();
      for (College c : l) {
        collegeIds.add(c.getId());
      }
      if (collegeIds.size() > 0) {
        collegeMapper.deleteBatchIds(collegeIds);
      }
      restTimeMapper.delete(new EntityWrapper<RestTime>().eq("school_id", id));//删除对应的作息时间信息
    }
    schoolMapper.deleteBatchIds(ids);
    return RestResultGenerator.genResult(null, ResultCode.DEL_SUCCESS);
  }

  public ResponseResult editSchool(SchoolEdit schoolEdit) {
    if (schoolEdit.getId() == null) {
      throw new UserFriendlyException(ResultCode.SCHOOL_RENAME_ID_NULL);
    }
    if (schoolEdit.getSchoolName() == null) {
      throw new UserFriendlyException(ResultCode.SCHOOL_NAME_NULL);
    }
    if (schoolMapper.selectCount(new EntityWrapper<School>().eq("id", schoolEdit.getId())) == 0) {
      throw new UserFriendlyException(ResultCode.SCHOOL_NOT_EXIST);
    }
    EntityWrapper entityWrapper = new EntityWrapper();
    entityWrapper.eq("school_name", schoolEdit.getSchoolName());
    if (schoolMapper.selectCount(entityWrapper) > 0 && !schoolEdit.getSchoolName().equals(schoolEdit.getOldSchoolName())) {
      throw new UserFriendlyException(ResultCode.ROLE_NAME_EXIST);
    }
    School s = new School();
    s.setId(schoolEdit.getId());
    s.setSchoolName(schoolEdit.getSchoolName());
    s.setSchoolAddress(schoolEdit.getSchoolAddress());
    s.setSchoolDetail(schoolEdit.getSchoolDetail());
    schoolMapper.updateById(s);
    return RestResultGenerator.genResult(null, ResultCode.EDI_SUCCESS);
  }
}
