package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.School;
import com.model.User;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.model.userdto.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface UserService extends IService<User> {
  ResponseResult getList(UserQuery userQuery);

  ResponseResult addOne(UserAdd userAdd);

  ResponseResult deleteUsers(UserDel userDel);

  ResponseResult editUser(UserEdit userEdit);

  ResponseResult uploadImg(MultipartFile file, Integer id, HttpSession session);

  ResponseResult login(UserLogin userLogin, HttpSession httpSession);

  ResponseResult logout(HttpSession httpSession);

  ResponseResult register(UserRegister userRegister, HttpSession session);

  ResponseResult check(UserCheck userCheck);

  ResponseResult checkCaptcha(ForgetPwd forgetPwd, HttpSession session);

  ResponseResult updatePwd(ForgetPwd forgetPwd);
}
