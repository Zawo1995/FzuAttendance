package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.College;
import com.model.Course;
import com.model.courdsdto.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface CourseService extends IService<Course>{
  ResponseResult getCourseList(CourseQuery t);
  Integer addOneCourse(Course c);
  Integer deleteOneCourse(Integer id);
  Integer deleteCourses(List<Integer> ids);
  Integer editCourse(Course c);
}
