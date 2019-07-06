package com.controller;

import com.utils.ResponseResult2;
import com.model.SystemParam;
import com.service.SystemParamService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZW on 2018/4/30.
 */
@Controller
@RequestMapping(value = "/systemParam", produces = {"application/json;charset=UTF-8"})
public class SystemParamController {
  private Logger log = Logger.getLogger(SystemParamController.class);

  @Resource
  SystemParamService systemParamService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public String getList(@RequestParam Map params) {
    List<Map<String, Object>> result = null;
    try {
      result = systemParamService.getList(params);
      return ResponseResult2.ok(result);
    } catch (Exception e) {
      log.error(e);
      return ResponseResult2.fail();
    }
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public String edit(SystemParam systemParam) {
    Integer result = 0;
    try {
      result = systemParamService.editSchool(systemParam);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildEditResult(result);
  }

}

