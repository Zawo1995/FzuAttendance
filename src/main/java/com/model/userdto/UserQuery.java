package com.model.userdto;

/**
 * Created by Administrator on 2019/6/11.
 */
public class UserQuery {
  private Integer id;

  private String name;

  private Integer roleId;

  private Long registDateBegin;

  private Long registDateEnd;

  private String phone;

  private String collegeName;

  private String schoolName;

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

  public Long getRegistDateBegin() {
    return registDateBegin;
  }

  public void setRegistDateBegin(Long registDateBegin) {
    this.registDateBegin = registDateBegin;
  }

  public Long getRegistDateEnd() {
    return registDateEnd;
  }

  public void setRegistDateEnd(Long registDateEnd) {
    this.registDateEnd = registDateEnd;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public String getCollegeName() {
    return collegeName;
  }

  public void setCollegeName(String collegeName) {
    this.collegeName = collegeName;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }
}
