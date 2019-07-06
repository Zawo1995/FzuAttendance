package com.model.courdsdto;

/**
 * Created by Administrator on 2019/6/14.
 */
public class CourseQuery {

  private Integer id;

  private String courseName;

  private Integer startWeek;

  private Integer endWeek;

  private Integer teacherId;

  private String teacherName;

  private Long firstOpenTime;

  private Long secondOpenTime;

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
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

  public Long getFirstOpenTime() {
    return firstOpenTime;
  }

  public void setFirstOpenTime(Long firstOpenTime) {
    this.firstOpenTime = firstOpenTime;
  }

  public Long getSecondOpenTime() {
    return secondOpenTime;
  }

  public void setSecondOpenTime(Long secondOpenTime) {
    this.secondOpenTime = secondOpenTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
