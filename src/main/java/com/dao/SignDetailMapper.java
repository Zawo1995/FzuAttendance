package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.SignDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SignDetailMapper extends BaseMapper<SignDetail> {
  List<Map<String,Object>> selectById(Integer id);
  List<Map<String,Object>> selectSignDetailById(Map params);
}
