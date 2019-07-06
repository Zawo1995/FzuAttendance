package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.College;
import com.model.School;
import com.model.collegedto.CollegeQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface CollegeService extends IService<College>{
  ResponseResult getCollegeList(CollegeQuery collegeQuery);
  Integer addOneCollege(College c);
  Integer deleteOneCollege(Integer id);
  Integer deleteColleges(List<Integer> ids);
  Integer editCollege(College c);
}
