package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.Role;
import com.model.RoleMenu;
import com.model.roledto.RoleAdd;
import com.model.roledto.RoleDel;
import com.model.roledto.RoleEdit;
import com.model.roledto.RoleQuery;
import com.model.rolemenudto.RoleMenuEdit;
import com.model.rolemenudto.RoleMenuQuery;

/**
 * Created by ZW on 2019/4/25.
 */
public interface RoleMenuService extends IService<RoleMenu>{
  ResponseResult getList(RoleMenuQuery t);
  ResponseResult updateBatch(RoleMenuEdit roleMenuEdit);
}
