package com.controller.common;

import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.model.userdto.UserCheck;
import com.model.userdto.UserLogin;
import com.model.userdto.UserRegister;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/2.
 */

@Controller
@RequestMapping(value = "/loginout", produces = {"application/json;charset=UTF-8"})
public class LogInOut {
  @Resource
  UserService userService;

  @RequestMapping(value = "login", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult login(UserLogin userLogin, HttpSession session,HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
      "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return userService.login(userLogin, session);
  }

  @RequestMapping(value = "check", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult check(UserCheck userCheck) {
    return userService.check(userCheck);
  }


  @RequestMapping(value = "logout", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult logout(HttpSession session) {
    return userService.logout(session);
  }

}
