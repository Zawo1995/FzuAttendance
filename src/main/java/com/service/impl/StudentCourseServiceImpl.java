package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.CollegeMapper;
import com.dao.RestTimeMapper;
import com.dao.SchoolMapper;
import com.dao.StudentCourseMapper;
import com.model.College;
import com.model.RestTime;
import com.model.School;
import com.model.StudentCourse;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.service.SchoolService;
import com.service.StudentCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("studentCourseService")
@Transactional(rollbackFor = Exception.class)
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

  @Resource
  StudentCourseMapper studentCourseMapper;
  //查询选课信息
  @Override
  public ResponseResult getList(Map params) {
    return RestResultGenerator.genResult(studentCourseMapper.selectAllStudentCourse(params),ResultCode.SUCCESS);
  }

  @Override
  public Integer addOne(StudentCourse studentCourse) {

    return studentCourseMapper.insert(studentCourse);
  }

  @Override
  public ResponseResult deleteOness(Map params) {
    return null;
  }

  @Override
  public ResponseResult edit(Map params) {
    return null;
  }
}
