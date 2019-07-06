package com.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class Course {
  @TableId(type = IdType.AUTO)
  private Integer id;

  private String courseName;

  private Integer startWeek;

  private Integer endWeek;

  private Integer teacherId;

  private Long courseOpenTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName == null ? null : courseName.trim();
  }

  public Integer getStartWeek() {
    return startWeek;
  }

  public void setStartWeek(Integer startWeek) {
    this.startWeek = startWeek;
  }

  public Integer getEndWeek() {
    return endWeek;
  }

  public void setEndWeek(Integer endWeek) {
    this.endWeek = endWeek;
  }

  public Integer getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(Integer teacherId) {
    this.teacherId = teacherId;
  }

  public Long getCourseOpenTime() {
    return courseOpenTime;
  }

  public void setCourseOpenTime(Long courseOpenTime) {
    this.courseOpenTime = courseOpenTime;
  }
}
