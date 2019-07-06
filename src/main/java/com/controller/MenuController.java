package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.menudto.MenuEdit;
import com.model.menudto.MenuQuery;
import com.model.userdto.UserAdd;
import com.model.userdto.UserDel;
import com.model.userdto.UserEdit;
import com.model.userdto.UserQuery;
import com.service.MenuService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/6/11.
 */
@Controller
@RequestMapping(value = "/menu", produces = {"application/json;charset=UTF-8"})
public class MenuController {
  @Resource
  MenuService menuService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(MenuQuery menuQuery) {
    return menuService.getAllMenu(menuQuery);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult addOne(Object o) {
    return null;
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseResult deleteMenus(Object o) {
    return null;
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseResult editMenu(MenuEdit menuEdit) {
    return menuService.eidtMenu(menuEdit);
  }
}

