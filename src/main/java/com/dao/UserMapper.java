package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.User;
import com.model.userdto.UserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User>{
  List<Map<String,Object>> selectAllUser(UserQuery userQuery);
}
