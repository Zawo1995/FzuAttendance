package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.*;
import com.model.*;
import com.model.roledto.RoleAdd;
import com.model.roledto.RoleDel;
import com.model.roledto.RoleEdit;
import com.model.roledto.RoleQuery;
import com.service.RoleService;
import com.service.SchoolService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZW on 2019/4/25.
 */
@Service("roleService")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
  @Resource
  RoleMapper roleMapper;

  @Resource
  RoleMenuMapper roleMenuMapper;

  @Resource
  MenuMapper menuMapper;

  public ResponseResult getList(RoleQuery roleQuery) {
    List<Map<String, Object>> result = null;
    EntityWrapper<Role> ew = new EntityWrapper<>();
    if (roleQuery.getName() != null) {
      ew.like("name", roleQuery.getName());
    }
    if(roleQuery.getIsDefault() != null){
      ew.eq("is_default",roleQuery.getIsDefault());
    }
    result = roleMapper.selectMaps(ew);
    return RestResultGenerator.genResult(result, ResultCode.SUCCESS);
  }

  public ResponseResult addOne(RoleAdd roleAdd) {
    if (roleAdd.getName() == null) {
      throw new UserFriendlyException(ResultCode.ROLE_NAME_NULL);
    }
    EntityWrapper entityWrapper = new EntityWrapper();
    entityWrapper.eq("name", roleAdd.getName());
    if (roleMapper.selectCount(entityWrapper) > 0) {
      throw new UserFriendlyException(ResultCode.ROLE_NAME_EXIST);
    }
    Role role = new Role();
    role.setName(roleAdd.getName());
    role.setRemark(roleAdd.getRemark());
    role.setIsDefault(0);//0表示非系统默认角色
    Integer result = roleMapper.insert(role);

    //插入Role-Menu关联表
    List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();
    List<Menu> menus = menuMapper.selectList(new EntityWrapper<Menu>());
    for(Menu m: menus){
      RoleMenu rm = new RoleMenu();
      rm.setMenuId(m.getId());
      rm.setRoleId(role.getId());
      rm.setEnable(0);//初始都设置为0
      roleMenuList.add(rm);
    }
    roleMenuMapper.insertBatch(roleMenuList);
    return RestResultGenerator.genResult(role.getId(), ResultCode.ADD_SUCCESS);//返回刚刚插入的角色ID

  }

  public ResponseResult delete(RoleDel roleDel) {
    if (roleDel.getIds() == null || roleDel.getIds().size() <= 0) {
      throw new UserFriendlyException(ResultCode.ROLE_DELETE_NULL);
    }
    for (Integer id : roleDel.getIds()) {
      if(roleMapper.selectCount(new EntityWrapper().eq("id",id)) == 0){
        throw new UserFriendlyException(ResultCode.ROLE_NOT_EXIST);
      }
    }
    roleMapper.deleteBatchIds(roleDel.getIds());
    return RestResultGenerator.genResult(null,ResultCode.DEL_SUCCESS);
  }

  public ResponseResult edit(RoleEdit roleEdit) {
    if (roleEdit.getId() == null) {
      throw new UserFriendlyException(ResultCode.ROLE_RENAME_ID_NULL);
    }
    if (roleEdit.getName() == null) {
      throw new UserFriendlyException(ResultCode.ROLE_NAME_NULL);
    }
    if(roleMapper.selectCount(new EntityWrapper().eq("id", roleEdit.getId())) == 0){
      throw new UserFriendlyException(ResultCode.ROLE_NOT_EXIST);
    }
    EntityWrapper entityWrapper = new EntityWrapper();
    entityWrapper.eq("name", roleEdit.getName());
    if (roleMapper.selectCount(entityWrapper) > 0 && !roleEdit.getName().equals(roleEdit.getOldName())) {
      throw new UserFriendlyException(ResultCode.ROLE_NAME_EXIST);
    }
    Role role = new Role();
    role.setId(roleEdit.getId());
    role.setName(roleEdit.getName());
    role.setRemark(roleEdit.getRemark());
    roleMapper.updateById(role);
    return RestResultGenerator.genResult(null,ResultCode.EDI_SUCCESS);
  }
}
