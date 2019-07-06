package com.controller.exceptions;

/**
 * Created by Administrator on 2019/6/10.
 */
public enum ResultCode {
  SUCCESS("0", "success"),
  UNKNOWN_ERROR("0x10001", "未知错误"),
  USERNAME_ERROR("0x10002", "用户名错误或不存在"),
  PASSWORD_ERROR("0x10003", "密码错误"),
  USERNAME_EMPTY("0x10004", "用户名不能为空"),
  LOGIN_ERROR("0x10005","用户名或者密码错误"),
  LOGIN_SUCCESS("0x10006","登录成功"),
  LOGOUT_SUCCESS("0x10007","登出成功"),
  REGIST_SUCCESS("0x10008","注册成功"),
  CHECK_SUCCESS("0x10009","检测登录成功"),
  CHECK_FAILTURE("0x10010","用户已过期，请重新登录"),
  CAPTCHA_SEND_SUSCCSS("0x10011","验证码已发送"),
  CAPTCHA_SEND_FAIL("0x10012","验证码发送失败"),
  CAPTCHA_ERROR("0x10013","验证码错误"),
  CAPTCHA_OUTDATE("0x10014","验证码过期"),
  USER_NULL("0x10015","该用户不存在"),
  UPLOAD_PIC_FAIL("0x10016","上传图片失败"),
  UPLOAD_PIC_SUCCESS("0x10017","上传图片成功"),



  ROLE_NAME_NULL("0x20001","角色名不能为空"),
  ROLE_DELETE_NULL("0x20002","删除角色的数量至少得一个"),
  ROLE_RENAME_ID_NULL("0x20003","被修改角色的ID不能为空"),
  ROLE_NAME_EXIST("0x20004","该角色已经存在"),
  ROLE_NOT_EXIST("0x20005","该角色不存在或者已经删除"),

  SCHOOL_NAME_NULL("0x30001","学校名称不能为空"),
  SCHOOL_DELETE_NULL("0x30002","删除学校的数量至少得一个"),
  SCHOOL_RENAME_ID_NULL("0x20003","被修改学校的ID不能为空"),
  SCHOOL_NAME_EXIST("0x30004","该学校已经存在"),
  SCHOOL_NOT_EXIST("0x20005","学校不存在或者已经删除"),

  USER_NAME_NULL("0x40001","用户名称不能为空"),
  USER_DELETE_NULL("0x40002","删除用户的数量至少得一个"),
  USER_ID_NULL("0x40003","被修改用户的ID不能为空"),
  USER_NOT_EXIST("0x40004","用户不存在或者已经删除"),
  USER_PWD_NOT_SAME("0x40005","两次输入的密码不一致"),
  USER_CODE_EXIST("0x40007","该账号已经被注册"),
  USER_PHONE_EXIST("0x40008","该手机已经被注册"),
  USER_MAIL_EXIST("0x40009","该邮箱已经被注册"),


  ACC_USER_NOT_EXIST("0x50001","用户不存在"),
  ACC_METHOD_EXIST("0x50002","该用户已经存在该登录方式"),
  ACC_EXIST("0x50003","该账户已经被注册"),
  ACC_METHOD_NULL("0x50004","登录方式不明"),
  ACC_ACCOUNT_NULL("0x50004","登录账户不明"),


  MENU_NAME_NULL("0x50001","菜单名不能为空"),
  MENU_NAME_EXIST("0x50003","该菜单名已经存在"),


  PHONE_VALID("0x80000","手机号码非法"),
  EMAIL_VALID("0x80001","邮箱格式不正确"),
  QQ_VALID("0x80002","QQ格式不正确"),
  WECHAT_VALID("0x80003","微信格式不正确"),
  ADD_SUCCESS("0x90001","添加成功"),
  DEL_SUCCESS("0x90002","删除成功"),
  EDI_SUCCESS("0x90003","修改成功"),
  ERROR_405("0x405","405错误"),
  ERROR_406("0x406","406错误"),
  ERROR_500("0x500","500错误"),

  ELSE_ERROR("0x99999","其他");

  private String code;
  private String msg;

  ResultCode(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

}
