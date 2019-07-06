package com.model.userdto;

import com.model.accountdto.AccountAdd;

import java.util.List;

/**
 * Created by Administrator on 2019/6/11.
 */
public class UserAdd {
  private String name;

  private Integer roleId;

  private Long registDate;

  private String password;

  private String checkPassword;

  private String phone;

  private Integer collegeId;

  private String AccountAddListStr;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public Long getRegistDate() {
    return registDate;
  }

  public void setRegistDate(Long registDate) {
    this.registDate = registDate;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCheckPassword() {
    return checkPassword;
  }

  public void setCheckPassword(String checkPassword) {
    this.checkPassword = checkPassword;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getCollegeId() {
    return collegeId;
  }

  public void setCollegeId(Integer collegeId) {
    this.collegeId = collegeId;
  }

  public String getAccountAddListStr() {
    return AccountAddListStr;
  }

  public void setAccountAddListStr(String accountAddListStr) {
    AccountAddListStr = accountAddListStr;
  }
}
