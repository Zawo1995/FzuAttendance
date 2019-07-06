package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.Course;
import com.model.courdsdto.CourseQuery;
import com.utils.ResponseResult2;
import com.service.CourseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by ZW on 2018/4/30.
 */
@Controller
@RequestMapping(value = "/course", produces = {"application/json;charset=UTF-8"})
public class CourseController {
  private Logger log = Logger.getLogger(CourseController.class);

  @Resource
  CourseService courseService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(CourseQuery t,  HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
      "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return courseService.getCourseList(t);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public String addOneCourse(Course c,HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
      "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    Integer result = 0;
    try {
      result = courseService.addOneCourse(c);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildAddResult(result);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public String deleteCourses(@RequestParam List<Integer> ids) {
    Integer result = 0;
    try {
      result = courseService.deleteCourses(ids);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildDelResult(result);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public String editCourse(Course c) {
    Integer result = 0;
    try {
      result = courseService.editCourse(c);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildEditResult(result);
  }
}

