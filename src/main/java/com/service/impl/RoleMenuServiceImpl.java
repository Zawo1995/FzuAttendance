package com.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.RoleMapper;
import com.dao.RoleMenuMapper;
import com.model.Role;
import com.model.RoleMenu;
import com.model.roledto.RoleAdd;
import com.model.roledto.RoleDel;
import com.model.roledto.RoleEdit;
import com.model.roledto.RoleQuery;
import com.model.rolemenudto.RoleMenuEdit;
import com.model.rolemenudto.RoleMenuQuery;
import com.service.RoleMenuService;
import com.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZW on 2019/4/25.
 */
@Service("roleMenuService")
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
  @Resource
  RoleMenuMapper roleMenuMapper;


  public ResponseResult getList(RoleMenuQuery roleMenuQuery) {
    EntityWrapper<RoleMenu> ew = new EntityWrapper<>();
    if (roleMenuQuery.getRoleId() != null) {
      ew.eq("role_id", roleMenuQuery.getRoleId());
    }
    if (roleMenuQuery.getMenuId() != null) {
      ew.eq("menu_id", roleMenuQuery.getMenuId());
    }
    return RestResultGenerator.genResult(roleMenuMapper.selectMaps(ew), ResultCode.SUCCESS);
  }

  public ResponseResult updateBatch(RoleMenuEdit roleMenuEdit) {
    List<RoleMenu> roleMenuList = JSONObject.parseArray(roleMenuEdit.getRoleMenuListStr(), RoleMenu.class);
    try {
      insertOrUpdateBatch(roleMenuList);//这个批量插入是Iservice的，不会被捕获
    } catch (Exception e) {
      throw new UserFriendlyException(ResultCode.UNKNOWN_ERROR);
    }

    return RestResultGenerator.genResult(null, ResultCode.EDI_SUCCESS);
  }


}
