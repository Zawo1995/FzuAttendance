package com.controller.common;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.UserMapper;
import com.model.User;
import com.model.userdto.ForgetPwd;
import com.model.userdto.UserRegister;
import com.service.UserService;
import com.utils.MessageUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2019/7/3.
 */

@Controller
@RequestMapping(value = "/forgetPwd", produces = {"application/json;charset=UTF-8"})
public class ForgetPwdController {
  @Resource
  UserService userService;

  @RequestMapping(value = "check", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult check(ForgetPwd forgetPwd, HttpSession session) {
    return userService.checkCaptcha(forgetPwd, session);
  }

  @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult updatePwd(ForgetPwd forgetPwd) {
    return userService.updatePwd(forgetPwd);
  }

  //发送验证码
  @RequestMapping(value = "sendCaptchaMessage", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult sendCaptchaMessage(ForgetPwd forgetPwd, HttpSession session) {
    User user = userService.selectOne(new EntityWrapper<User>().eq("phone", forgetPwd.getMobile()));
    if (user == null) {
      throw new UserFriendlyException(ResultCode.USER_NULL);
    }
    String captcha = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
    System.out.println("captcha:" + captcha);
    //....发送验证码短信
    MessageUtil.getInstance().sendForgetMsg(forgetPwd.getMobile(), captcha);
    JSONObject json = new JSONObject();
    json.put("captcha", captcha);
    json.put("createTime", System.currentTimeMillis());//存入当前时间
    session.setAttribute(forgetPwd.getMobile(), json);//存入session,电话作key
    return RestResultGenerator.genResult(user.getId(), ResultCode.CAPTCHA_SEND_SUSCCSS);//返回给前端用户的id
  }
}
