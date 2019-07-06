package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.SignDetail;
import com.service.SignDetailService;
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
 * Created by lyq-pc on 2019/6/28.
 */

@Controller
@RequestMapping(value = "/signDetail", produces = {"application/json;charset=UTF-8"})
public class SignDetailController {

  @Resource
  SignDetailService signDetailService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, Object>> getList(Integer id, HttpServletResponse response
  ) {

    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return signDetailService.getSignList(id);

  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult addOneSchool(SignDetail signDetail, HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return signDetailService.addOne(signDetail);
  }

  @RequestMapping(value = "detail", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getSignDetailListBySignId(@RequestParam Map params) {
    return signDetailService.selectSignDetailById(params);
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

