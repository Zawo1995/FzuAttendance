package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.SignDetail;
import com.model.SignIn;
import com.service.SignDetailService;
import com.service.SignService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyq-pc on 2019/6/28.
 */

@Controller
@RequestMapping(value = "/sign", produces = {"application/json;charset=UTF-8"})
public class SignController {

  @Resource
  SignService signService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(SignIn signIn,  HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
      "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return signService.getSignList(signIn);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public Integer addOneSign(SignIn signIn, HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return signService.addOne(signIn);
  }


  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ResponseBody
  public Integer update(SignIn signIn, HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return signService.updateByEnd(signIn);
  }

  @RequestMapping(value = "signTask", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getAllSignTask(@RequestParam Map params) {
   return signService.getAllSignTask(params);
  }


  @RequestMapping(value = "getSignId", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, Object>> getList(String courseId, HttpServletResponse response
  ) {
     Map<String,Object> param= new HashMap<>();
     param.put("courseId",courseId);
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return signService.getSignId(param);
  }
//  @RequestMapping(value = "", method = RequestMethod.DELETE)
//  @ResponseBody
//  public ResponseResult deleteSchools(SchoolDel schoolDel) {
//    return signService.deleteSchools(schoolDel);
//  }
//
//  @RequestMapping(value = "", method = RequestMethod.PUT)
//  @ResponseBody
//  public ResponseResult editSchool(SchoolEdit schoolEdit) {
//    return signService.editSchool(schoolEdit);
//  }
}
