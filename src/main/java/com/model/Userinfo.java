package com.model;

import com.utils.MD5Util;

public class Userinfo {
  private Integer id;

  private String userCode;

  private String name;

  private Integer sex;

  private Integer role;

  private String phone;

  private String password;

  private Integer flag;

  private String belongTo;

  private String post;

  public String getBelongTo() {
    return belongTo;
  }

  public void setBelongTo(String belongTo) {
    this.belongTo = belongTo;
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode == null ? null : userCode.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = role;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone == null ? null : phone.trim();
  }

  public String getPassword( ) {
//    try {
//      return (id != null || password != null) ? password : MD5Util.md5Encrypt("123456");
//    }catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    }
    return  password;
  }

  public void setPassword(String password){
    this.password = password == null ? null : password.trim();
  }

  public Integer getFlag(boolean b) {
    return flag;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }
}
