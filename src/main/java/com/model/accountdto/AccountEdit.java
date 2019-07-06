package com.model.accountdto;

/**
 * Created by Administrator on 2019/6/12.
 */
public class AccountEdit {
  private Integer id;

  private String method;

  private String loginAccount;

  private Integer userId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }
}
