package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.StudentCourse;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.service.SchoolService;
import com.service.StudentCourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by ZW on 2018/4/30.
 */
@Controller
@RequestMapping(value = "/studentCourse", produces = {"application/json;charset=UTF-8"})
public class StudentCourseController {
  @Resource
  StudentCourseService studentCourseService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(@RequestParam Map params,HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");

    return studentCourseService.getList(params);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public Integer addOne(StudentCourse studentCourse, HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");

    return studentCourseService.addOne(studentCourse);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseResult deleteOnes(@RequestParam Map params) {
    return studentCourseService.deleteOness(params);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseResult edit(@RequestParam Map params) {
    return studentCourseService.edit(params);
  }
}

