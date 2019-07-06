package com.controller.exceptions;

/**
 * Created by Administrator on 2019/6/10.
 */
public class UserFriendlyException extends RuntimeException {
  private String code;
  private String msg;
  private ResultCode resultCode;

  public UserFriendlyException(ResultCode resultCode) {
    super(resultCode.getMsg());
    this.code = resultCode.getCode();
    this.msg = resultCode.getMsg();
    this.resultCode = resultCode;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public ResultCode getResultCode() {
    return resultCode;
  }

  public void setResultCode(ResultCode resultCode) {
    this.resultCode = resultCode;
  }
}
