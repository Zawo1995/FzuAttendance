package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.controller.exceptions.ResponseResult;
import com.model.Course;
import com.model.courdsdto.CourseQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseMapper extends BaseMapper<Course> {
  List<Map<String,Object>> selectAllCourse(CourseQuery courseQuery);
}
