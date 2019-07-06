package com.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class RestTime {
  @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer schoolId;

    private Integer breakTime;

    private Integer lessonTime;

    private Long lessonOne;

    private Long lessonTwo;

    private Long lessonThree;

    private Long lessonFour;

    private Long lessonFive;

    private Long lessonSix;

    private Long lessonSeven;

    private Long lessonEight;

    private Long lessonNine;

    private Long lessonTen;

    private Long lessonEleven;

    private Integer lessonDelay;

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

    public Integer getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Integer breakTime) {
        this.breakTime = breakTime;
    }

    public Integer getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(Integer lessonTime) {
        this.lessonTime = lessonTime;
    }

    public Long getLessonOne() {
        return lessonOne;
    }

    public void setLessonOne(Long lessonOne) {
        this.lessonOne = lessonOne;
    }

    public Long getLessonTwo() {
        return lessonTwo;
    }

    public void setLessonTwo(Long lessonTwo) {
        this.lessonTwo = lessonTwo;
    }

    public Long getLessonThree() {
        return lessonThree;
    }

    public void setLessonThree(Long lessonThree) {
        this.lessonThree = lessonThree;
    }

    public Long getLessonFour() {
        return lessonFour;
    }

    public void setLessonFour(Long lessonFour) {
        this.lessonFour = lessonFour;
    }

    public Long getLessonFive() {
        return lessonFive;
    }

    public void setLessonFive(Long lessonFive) {
        this.lessonFive = lessonFive;
    }

    public Long getLessonSix() {
        return lessonSix;
    }

    public void setLessonSix(Long lessonSix) {
        this.lessonSix = lessonSix;
    }

    public Long getLessonSeven() {
        return lessonSeven;
    }

    public void setLessonSeven(Long lessonSeven) {
        this.lessonSeven = lessonSeven;
    }

    public Long getLessonEight() {
        return lessonEight;
    }

    public void setLessonEight(Long lessonEight) {
        this.lessonEight = lessonEight;
    }

    public Long getLessonNine() {
        return lessonNine;
    }

    public void setLessonNine(Long lessonNine) {
        this.lessonNine = lessonNine;
    }

    public Long getLessonTen() {
        return lessonTen;
    }

    public void setLessonTen(Long lessonTen) {
        this.lessonTen = lessonTen;
    }

    public Long getLessonEleven() {
        return lessonEleven;
    }

    public void setLessonEleven(Long lessonEleven) {
        this.lessonEleven = lessonEleven;
    }

    public Integer getLessonDelay() {
        return lessonDelay;
    }

    public void setLessonDelay(Integer lessonDelay) {
        this.lessonDelay = lessonDelay;
    }
}
