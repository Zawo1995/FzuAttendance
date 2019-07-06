package com.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

public class College implements Serializable{
  @TableId(type = IdType.AUTO)
  private Integer id;

  private Integer schoolId;

  private String collegeName;

  private String collegeAddress;

  private String collegeDetail;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSchoolId() {
    return schoolId;
  }

  public void setSchoolId(Integer schoolId) {
    this.schoolId = schoolId;
  }

  public String getCollegeName() {
    return collegeName;
  }

  public void setCollegeName(String collegeName) {
    this.collegeName = collegeName == null ? null : collegeName.trim();
  }

  public String getCollegeAddress() {
    return collegeAddress;
  }

  public void setCollegeAddress(String collegeAddress) {
    this.collegeAddress = collegeAddress == null ? null : collegeAddress.trim();
  }

  public String getCollegeDetail() {
    return collegeDetail;
  }

  public void setCollegeDetail(String collegeDetail) {
    this.collegeDetail = collegeDetail == null ? null : collegeDetail.trim();
  }
}
