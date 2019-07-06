package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.College;
import com.model.collegedto.CollegeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CollegeMapper extends BaseMapper<College> {
  List<Map<Object, Object>> selectAllCollege(CollegeQuery collegeQuery);
}
