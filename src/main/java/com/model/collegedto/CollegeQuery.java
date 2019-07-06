package com.model.collegedto;

import java.util.List;

/**
 * Created by Administrator on 2019/6/11.
 */
public class CollegeQuery {
  private Integer schoolId;

  private String collegeName;

  private String collegeAddress;

  private String schoolAddress;

  private List<Integer> schoolIds;

  public List<Integer> getSchoolIds() {
    return schoolIds;
  }

  public void setSchoolIds(List<Integer> schoolIds) {
    this.schoolIds = schoolIds;
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
    this.collegeName = collegeName;
  }

  public String getCollegeAddress() {
    return collegeAddress;
  }

  public void setCollegeAddress(String collegeAddress) {
    this.collegeAddress = collegeAddress;
  }

  public String getSchoolAddress() {
    return schoolAddress;
  }

  public void setSchoolAddress(String schoolAddress) {
    this.schoolAddress = schoolAddress;
  }
}
