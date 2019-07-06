package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.School;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface SchoolService extends IService<School>{
  ResponseResult getList(SchoolQuery schoolQuery);
  ResponseResult addOne(SchoolAdd schoolAdd);
  ResponseResult deleteSchools(SchoolDel schoolDel);
  ResponseResult editSchool(SchoolEdit schoolEdit);
}
