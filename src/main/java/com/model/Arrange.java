package com.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class Arrange {
  @TableId(type = IdType.AUTO)
  private Integer id;

  private Integer courseId;

  private String courseAddress;

  private Double longitude;

  private Double latitude;

  private Integer setRow;

  private Integer setColumn;

  private Integer lessonBegin;

  private Integer lessonLength;

  private Integer dayNum;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public String getCourseAddress() {
    return courseAddress;
  }

  public void setCourseAddress(String courseAddress) {
    this.courseAddress = courseAddress == null ? null : courseAddress.trim();
  }



  public Integer getSetRow() {
    return setRow;
  }

  public void setSetRow(Integer setRow) {
    this.setRow = setRow;
  }

  public Integer getSetColumn() {
    return setColumn;
  }

  public void setSetColumn(Integer setColumn) {
    this.setColumn = setColumn;
  }

  public Integer getLessonBegin() {
    return lessonBegin;
  }

  public void setLessonBegin(Integer lessonBegin) {
    this.lessonBegin = lessonBegin;
  }

  public Integer getLessonLength() {
    return lessonLength;
  }

  public void setLessonLength(Integer lessonLength) {
    this.lessonLength = lessonLength;
  }

  public Integer getDayNum() {
    return dayNum;
  }

  public void setDayNum(Integer dayNum) {
    this.dayNum = dayNum;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
}
