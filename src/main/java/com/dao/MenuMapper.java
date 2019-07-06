package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.Menu;
import com.model.menudto.MenuQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuMapper extends BaseMapper<Menu>{
  List<Map<String,Object>> selectAllMenu(MenuQuery menuQuery);
}
