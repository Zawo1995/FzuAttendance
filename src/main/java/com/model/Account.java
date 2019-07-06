package com.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.model.accountdto.AccountAdd;
import com.model.accountdto.AccountEdit;

public class Account {
  @TableId(type = IdType.AUTO)
    private Integer id;

    private String method;

    private String loginAccount;

    private String passwd;

    private Integer userId;

    public Account(){

    }

    public Account(AccountAdd accountAdd){
      this.method = accountAdd.getMethod();
      this.loginAccount = accountAdd.getLoginAccount();
      this.passwd = accountAdd.getPassword();
      this.userId = accountAdd.getUserId();
    }

    public Account(AccountEdit accountEdit){
      this.id = accountEdit.getId();
      this.method = accountEdit.getMethod();
      this.loginAccount = accountEdit.getLoginAccount();
    }

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
        this.method = method == null ? null : method.trim();
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
