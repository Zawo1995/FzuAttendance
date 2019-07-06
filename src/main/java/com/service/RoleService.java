package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.Role;
import com.model.roledto.RoleAdd;
import com.model.roledto.RoleDel;
import com.model.roledto.RoleEdit;
import com.model.roledto.RoleQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by ZW on 2019/4/25.
 */
public interface RoleService extends IService<Role>{
  ResponseResult getList(RoleQuery roleQuery);
  ResponseResult addOne(RoleAdd roleAdd);
  ResponseResult delete(RoleDel roleDel);
  ResponseResult edit(RoleEdit roleEdit);
}
