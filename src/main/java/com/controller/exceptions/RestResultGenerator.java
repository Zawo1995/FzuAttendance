package com.controller.exceptions;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestResultGenerator {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestResultGenerator.class);

  public RestResultGenerator() {
  }


  public static <T> ResponseResult<T> genResult(T data, ResultCode resultCode) {
    ResponseResult result = new ResponseResult();
    result.setSuccess(true);
    result.setData(data);
    result.setMessage(resultCode.getMsg());
    result.setCode(resultCode.getCode());
    return result;
  }


  public static <T> ResponseResult<T> genResult(ResultCode resultCode) {
    ResponseResult result = new ResponseResult();
    if(StringUtils.isEmpty(resultCode.getMsg())) {
      result.setSuccess(true);
    } else {
      result.setSuccess(false);
    }
    result.setMessage(resultCode.getMsg());
    return result;
  }
}
