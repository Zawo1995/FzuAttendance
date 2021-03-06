package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.School;
import com.model.StudentCourse;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;

import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface StudentCourseService extends IService<StudentCourse>{
  ResponseResult getList(Map params);
  Integer addOne(StudentCourse studentCourse);
  ResponseResult deleteOness(Map params);
  ResponseResult edit(Map params);
}
