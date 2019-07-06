package com.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class StudentCourse {
  @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer studentId;

    private Integer courseId;

    private Integer sickCount;

    private Integer thinkCount;

    private Integer lateCount;

    private Integer earlyCount;

    private Integer absenteeism;

    private Float ordinaryGrade;

    private Float examGrade;

    private Float finalGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSickCount() {
        return sickCount;
    }

    public void setSickCount(Integer sickCount) {
        this.sickCount = sickCount;
    }

    public Integer getThinkCount() {
        return thinkCount;
    }

    public void setThinkCount(Integer thinkCount) {
        this.thinkCount = thinkCount;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public Integer getEarlyCount() {
        return earlyCount;
    }

    public void setEarlyCount(Integer earlyCount) {
        this.earlyCount = earlyCount;
    }

    public Integer getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(Integer absenteeism) {
        this.absenteeism = absenteeism;
    }

    public Float getOrdinaryGrade() {
        return ordinaryGrade;
    }

    public void setOrdinaryGrade(Float ordinaryGrade) {
        this.ordinaryGrade = ordinaryGrade;
    }

    public Float getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(Float examGrade) {
        this.examGrade = examGrade;
    }

    public Float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(Float finalGrade) {
        this.finalGrade = finalGrade;
    }
}
