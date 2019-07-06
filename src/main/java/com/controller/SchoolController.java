package com.controller;

import com.controller.exceptions.ResponseResult;
import com.dao.SchoolMapper;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.utils.ResponseResult2;
import com.model.School;
import com.service.SchoolService;
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
@RequestMapping(value = "/school", produces = {"application/json;charset=UTF-8"})
public class SchoolController {

  @Resource
  SchoolService schoolService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(SchoolQuery schoolQuery, HttpServletResponse response
           ) {

      response.addHeader("Access-Control-Allow-Origin", "*");
      response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      response.addHeader("Access-Control-Allow-Headers",
        "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
     return schoolService.getList(schoolQuery);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult addOneSchool(SchoolAdd schoolAdd) {
    return schoolService.addOne(schoolAdd);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseResult deleteSchools(SchoolDel schoolDel) {
    return schoolService.deleteSchools(schoolDel);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseResult editSchool(SchoolEdit schoolEdit) {
    return schoolService.editSchool(schoolEdit);
  }
}

