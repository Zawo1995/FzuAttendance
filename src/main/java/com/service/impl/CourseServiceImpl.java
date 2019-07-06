package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.dao.CourseMapper;
import com.model.Course;
import com.model.courdsdto.CourseQuery;
import com.service.ArrangeService;
import com.service.CourseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("courseService")
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
  @Resource
  CourseMapper courseMapper;

  @Resource
  ArrangeService arrangeService;
  private Logger log = Logger.getLogger(CourseServiceImpl.class);

  @Override
  public ResponseResult getCourseList(CourseQuery t) {

    return RestResultGenerator.genResult(courseMapper.selectAllCourse(t), ResultCode.SUCCESS);
  }

  @Override
  public Integer addOneCourse(Course c) {
    c.setCourseOpenTime(new Date().getTime());
    return courseMapper.insert(c);
  }

  @Override
  public Integer deleteOneCourse(Integer id) {
    Integer result = 0;
    result = arrangeService.deleteByCourseId(id);
    if(result == 0){
      return result;
    }
    result = courseMapper.deleteById(id);
    return result;
  }

  @Override
  public Integer deleteCourses(List<Integer> ids) {
    Integer result = 0;
    for(Integer id : ids){
      result = arrangeService.deleteByCourseId(id);
    }
    result = courseMapper.deleteBatchIds(ids);
    return result;
  }

  @Override
  public Integer editCourse(Course c) {
    return courseMapper.updateById(c);
  }
}
