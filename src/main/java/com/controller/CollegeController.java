package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.College;
import com.model.collegedto.CollegeQuery;
import com.utils.ResponseResult2;
import com.service.CollegeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/27.
 */
@Controller
@RequestMapping(value = "/college", produces = {"application/json;charset=UTF-8"})
public class CollegeController {
  private Logger log = Logger.getLogger(CollegeController.class);
  @Resource
  CollegeService collegeService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(CollegeQuery collegeQuery) {
    return collegeService.getCollegeList(collegeQuery);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public String addOneCollege(College c) {
    Integer result = 0;
    try {
      result = collegeService.addOneCollege(c);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildAddResult(result);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public String deleteColleges(@RequestParam List<Integer> ids) {
    Integer result = 0;
    try {
      result = collegeService.deleteColleges(ids);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildDelResult(result);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public String editCollege(College college) {
    Integer result = 0;
    try {
      result = collegeService.editCollege(college);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildEditResult(result);
  }

}
