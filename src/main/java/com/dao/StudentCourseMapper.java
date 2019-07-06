package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.StudentCourse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentCourseMapper extends BaseMapper<StudentCourse>{
  List<Map<String,Object>> selectAllStudentCourse(Map params);
}
