package com.model;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class School {
  @TableId(type = IdType.AUTO)
    private Integer id;

    private String schoolName;

    private String schoolAddress;

    private String schoolDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress == null ? null : schoolAddress.trim();
    }

    public String getSchoolDetail() {
        return schoolDetail;
    }

    public void setSchoolDetail(String schoolDetail) {
        this.schoolDetail = schoolDetail == null ? null : schoolDetail.trim();
    }
}
