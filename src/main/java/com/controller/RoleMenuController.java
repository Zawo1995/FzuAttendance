package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.menudto.MenuEdit;
import com.model.menudto.MenuQuery;
import com.model.rolemenudto.RoleMenuEdit;
import com.model.rolemenudto.RoleMenuQuery;
import com.service.MenuService;
import com.service.RoleMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/6/11.
 */
@Controller
@RequestMapping(value = "/rolemenu", produces = {"application/json;charset=UTF-8"})
public class RoleMenuController {
  @Resource
  RoleMenuService roleMenuService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(RoleMenuQuery t) {
    return roleMenuService.getList(t);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseResult updateBatch(RoleMenuEdit roleMenuEdit){
    return roleMenuService.updateBatch(roleMenuEdit);
  }


}

