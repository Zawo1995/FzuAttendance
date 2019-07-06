package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.Userinfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserinfoMapper extends BaseMapper<Userinfo> {
  List<Map<Object,Object>> selectAllUser(Map params);

  int checkExistCode(String userCode);
}
