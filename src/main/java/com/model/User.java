package com.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.model.userdto.UserAdd;
import com.model.userdto.UserEdit;

public class User {
  @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer roleId;

    private String imageUrl;

    private Long registDate;

    private Long lastLoginDate;

    private String passwd;

    private String phone;

    private Integer collegeId;

    public User(){

    }

    public User(UserAdd userAdd) {
      this.name = userAdd.getName();
      this.roleId = userAdd.getRoleId();
      this.registDate = userAdd.getRegistDate();
      this.passwd = userAdd.getPassword();
      this.phone = userAdd.getPhone();
      this.collegeId = userAdd.getCollegeId();
    }

  public User(UserEdit userEdit) {
    this.id = userEdit.getId();
    this.name = userEdit.getName() != null ? userEdit.getName() : this.name;
    this.roleId = userEdit.getRoleId() != null ? userEdit.getRoleId() : this.roleId;
    this.imageUrl = userEdit.getImageUrl() != null ? userEdit.getImageUrl() : this.imageUrl;
    this.phone = userEdit.getPhone() != null ? userEdit.getPhone() : this.phone;
    this.collegeId = userEdit.getCollegeId() != null ? userEdit.getCollegeId() : this.collegeId;
    this.passwd = userEdit.getPassword() != null ? userEdit.getPassword() : this.passwd;
  }

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
        this.name = name == null ? null : name.trim();
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
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Long getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Long registDate) {
        this.registDate = registDate;
    }

    public Long getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }
}
