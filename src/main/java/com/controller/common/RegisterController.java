package com.controller.common;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.UserMapper;
import com.model.User;
import com.model.userdto.UserRegister;
import com.service.UserService;
import com.utils.MessageUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin2.message.Message;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2019/7/3.
 */

@Controller
@RequestMapping(value = "/register", produces = {"application/json;charset=UTF-8"})
public class RegisterController {
  @Resource
  UserService userService;

  @Resource
  UserMapper userMapper;

  @RequestMapping(value = "register", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult register(UserRegister userRegister, HttpSession session,HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
      "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    return userService.register(userRegister, session);
  }

  //发送验证码
  @RequestMapping(value = "sendCaptchaMessage", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult sendCaptchaMessage(String telphone, HttpSession session,HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers",
      "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,token");
    if (userMapper.selectCount(new EntityWrapper<User>().eq("phone", telphone)) > 0) {
      throw new UserFriendlyException(ResultCode.USER_PHONE_EXIST);
    }
    String captcha = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
    System.out.println("captcha:" + captcha);
    //....发送验证码短信
    MessageUtil.getInstance().sendRegisterMsg(telphone,captcha);
    JSONObject json = new JSONObject();
    json.put("captcha", captcha);
    json.put("createTime", System.currentTimeMillis());//存入当前时间
    session.setAttribute(telphone, json);//存入session,电话作key
    return RestResultGenerator.genResult(captcha, ResultCode.CAPTCHA_SEND_SUSCCSS);
  }
}
