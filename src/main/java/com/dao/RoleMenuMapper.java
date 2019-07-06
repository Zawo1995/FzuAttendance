package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
  Integer insertBatch(List<RoleMenu> roleMenuList);
}
