package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.User;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.model.userdto.UserAdd;
import com.model.userdto.UserDel;
import com.model.userdto.UserEdit;
import com.model.userdto.UserQuery;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2019/6/11.
 */
@Controller
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
public class UserController {
  @Resource
  UserService userService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(UserQuery userQuery) {
    return userService.getList(userQuery);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult addOne(UserAdd userAdd) {
    return userService.addOne(userAdd);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseResult deleteUsers(UserDel userDel) {
    return userService.deleteUsers(userDel);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseResult editUser(UserEdit userEdit) {
    return userService.editUser(userEdit);
  }

  @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult uploadImg(MultipartFile file, Integer id, HttpSession session) {
    return userService.uploadImg(file, id, session);
  }
}
