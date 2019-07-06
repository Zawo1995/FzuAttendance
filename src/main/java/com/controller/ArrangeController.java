package com.controller;

import com.model.Arrange;
import com.utils.ResponseResult2;
import com.service.ArrangeService;
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
 * Created by Administrator on 2019/4/27.
 */
@Controller
@RequestMapping(value = "/arrange", produces = {"application/json;charset=UTF-8"})
public class ArrangeController {
  private Logger log = Logger.getLogger(SchoolController.class);
  @Resource
  ArrangeService arrangeService;


  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public String getList(@RequestParam Map params,HttpServletResponse response) {
    List<Map<String, Object>> result = null;
      response.addHeader("Access-Control-Allow-Origin", "*");
      response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      response.addHeader("Access-Control-Allow-Headers",
        "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    try {
      result = arrangeService.getArrangeList(params);
      return ResponseResult2.ok(result);
    } catch (Exception e) {
      log.error(e);
      return ResponseResult2.fail();
    }
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public String addOneArrange(Arrange arrange, HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    Integer result = 0;
    try {
      result = arrangeService.addOne(arrange);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildAddResult(result);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public String deleteArranges(@RequestParam List<Integer> ids) {
    Integer result = 0;
    try {
      result = arrangeService.deleteList(ids);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildDelResult(result);
  }


  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public String editArrange(Arrange arrange) {
    Integer result = 0;
    try {
      result = arrangeService.edit(arrange);
    } catch (Exception e) {
      log.error(e);
      result = 0;
    }
    return ResponseResult2.BuildEditResult(result);
  }

}
