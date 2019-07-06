package com.controller;

import com.utils.ResponseResult2;
import com.model.RestTime;
import com.service.RestTimeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZW on 2018/4/30.
 */
@Controller
@RequestMapping(value = "/restTime", produces = {"application/json;charset=UTF-8"})
public class RestTimeController {
  @Resource
  RestTimeService restTimeService;
  private Logger log = Logger.getLogger(RestTimeController.class);

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public String getRestTimeBySchoolId(Integer id){
    List<Map<String, Object>> result = null;
    try{
      result = restTimeService.getRestTimeBySchoolId(id);
      return ResponseResult2.ok(result);
    } catch (Exception e){
      log.error(e);
      return ResponseResult2.fail();
    }
  }


  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public String updateSchoolRestTime(RestTime params){
    Integer result = 0;
    try{
      result = restTimeService.updateSchoolRestTime(params);
    }catch (Exception e){
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildEditResult(result);
  }
}

