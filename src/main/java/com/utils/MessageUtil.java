package com.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/6.
 */
public class MessageUtil {
  private static MessageUtil instance = null;
  // 产品名称:云通信短信API产品,开发者无需替换
  private static final String product = "Dysmsapi";
  // 产品域名,开发者无需替换
  private static final String domain = "dysmsapi.aliyuncs.com";

  private static final String accessKeyId = "LTAI1wWqWEhnOrU8";
  private static final String accessKeySecret = "1DAsDLxTnKNNSXVuKtjOFY79vf8AUX";

  private static final String signName = "超级考勤系统";
  private static final String codeRegister = "SMS_169902393";
  private static final String codeForget = "SMS_169902394";
  private static IAcsClient client;

  // 构造
  private MessageUtil() {

  }

  public static MessageUtil getInstance() {
    if (instance == null) {
      instance = new MessageUtil();
    }
    DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
    try {
      DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    } catch (ClientException e) {
      throw new UserFriendlyException(ResultCode.CAPTCHA_SEND_FAIL);
    }
    client = new DefaultAcsClient(profile);
    return instance;
  }

  public boolean sendRegisterMsg(String phoneNumber, String code) {
    SendSmsRequest request = new SendSmsRequest();
    request.setSignName(signName);
    request.setPhoneNumbers(phoneNumber);
    request.setTemplateCode(codeRegister);
    Map maps = new HashMap();
    maps.put("code", code);
    request.setTemplateParam(JSON.toJSONString(maps));
    return sendMsg(request);
  }

  public boolean sendForgetMsg(String phoneNumber, String code) {
    SendSmsRequest request = new SendSmsRequest();
    request.setSignName(signName);
    request.setPhoneNumbers(phoneNumber);
    request.setTemplateCode(codeForget);
    Map maps = new HashMap();
    maps.put("code", code);
    request.setTemplateParam(JSON.toJSONString(maps));
    return sendMsg(request);
  }

  public boolean sendMsg(SendSmsRequest request) {
    try {
      SendSmsResponse response = client.getAcsResponse(request);
      if (response.getCode() != null && response.getCode().equals("OK")) {
        return true;
      } else {
        return false;
      }
    } catch (ClientException e) {
      return false;
    }
  }

}
