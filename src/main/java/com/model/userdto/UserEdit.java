package com.model.userdto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2019/6/11.
 */
public class UserEdit {

  private Integer id;

  private String name;

  private Integer roleId;

  private String imageUrl;

  private String phone;

  private Integer collegeId;

  private String password;

  private String checkPassword;



  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
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
}
