package com.controller;

import com.controller.exceptions.ResponseResult;
import com.dao.SchoolMapper;
import com.model.Role;
import com.model.School;
import com.model.roledto.RoleAdd;
import com.model.roledto.RoleDel;
import com.model.roledto.RoleEdit;
import com.model.roledto.RoleQuery;
import com.service.RoleService;
import com.service.SchoolService;
import com.utils.ResponseResult2;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZW on 2018/4/30.
 */
@Controller
@RequestMapping(value = "/role", produces = {"application/json;charset=UTF-8"})
public class RoleController {
  private Logger log = Logger.getLogger(RoleController.class);

  @Resource
  RoleService roleService;
  @Resource
  SchoolMapper schoolMapper;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(RoleQuery roleQuery) {
    return roleService.getList(roleQuery);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult addOne(RoleAdd roleAdd) {
    return roleService.addOne(roleAdd);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseResult delete(RoleDel roleDel) {
    return roleService.delete(roleDel);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseResult edit(RoleEdit roleEdit) {
    return roleService.edit(roleEdit);
  }
}

