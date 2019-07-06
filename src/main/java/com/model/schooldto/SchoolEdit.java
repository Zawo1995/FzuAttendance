package com.model.schooldto;

/**
 * Created by Administrator on 2019/6/11.
 */
public class SchoolEdit {
  private Integer id;

  private String oldSchoolName;

  private String schoolName;

  private String schoolAddress;

  private String schoolDetail;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOldSchoolName() {
    return oldSchoolName;
  }

  public void setOldSchoolName(String oldSchoolName) {
    this.oldSchoolName = oldSchoolName;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  public String getSchoolAddress() {
    return schoolAddress;
  }

  public void setSchoolAddress(String schoolAddress) {
    this.schoolAddress = schoolAddress;
  }

  public String getSchoolDetail() {
    return schoolDetail;
  }

  public void setSchoolDetail(String schoolDetail) {
    this.schoolDetail = schoolDetail;
  }
}
