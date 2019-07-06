package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.*;
import com.model.*;
import com.model.accountdto.AccountAdd;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.model.userdto.*;
import com.service.AccountService;
import com.service.SchoolService;
import com.service.UserService;
import com.utils.MemoryData;
import com.utils.PatternUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
  @Resource
  UserMapper userMapper;

  @Resource
  AccountService accountService;

  @Resource
  AccountMapper accountMapper;

  public ResponseResult getList(UserQuery userQuery) {
    return RestResultGenerator.genResult(userMapper.selectAllUser(userQuery), ResultCode.SUCCESS);
  }

  public ResponseResult addOne(UserAdd userAdd) {
    if (!userAdd.getCheckPassword().equals(userAdd.getPassword())) {
      throw new UserFriendlyException(ResultCode.USER_PWD_NOT_SAME);
    }
    if (userAdd.getName() == null) {
      throw new UserFriendlyException(ResultCode.USER_NAME_NULL);
    }
    if (!PatternUtil.phonePattern(userAdd.getPhone())) {
      throw new UserFriendlyException(ResultCode.PHONE_VALID);
    }
    if (userMapper.selectCount(new EntityWrapper<User>().eq("phone", userAdd.getPhone())) > 0) {
      throw new UserFriendlyException(ResultCode.USER_PHONE_EXIST);
    }
    User u = new User(userAdd);
    userMapper.insert(u);//插入用户数据成功
    List<AccountAdd> l = JSONArray.parseArray(userAdd.getAccountAddListStr(), AccountAdd.class);
    for (AccountAdd accountAdd : l) {
      accountAdd.setPassword(userAdd.getPassword());
      accountAdd.setUserId(u.getId());
    }
    return accountService.addOnes(l);
  }

  public ResponseResult deleteUsers(UserDel userDel) {
    if (userDel.getIds() == null || userDel.getIds().size() <= 0) {
      throw new UserFriendlyException(ResultCode.USER_DELETE_NULL);
    }
    for (Integer id : userDel.getIds()) {
      if (userMapper.selectCount(new EntityWrapper().eq("id", id)) == 0) {
        throw new UserFriendlyException(ResultCode.USER_NOT_EXIST);
      }
    }
    userMapper.deleteBatchIds(userDel.getIds());
    return RestResultGenerator.genResult(null, ResultCode.DEL_SUCCESS);
  }

  public ResponseResult editUser(UserEdit userEdit) {
    if (userEdit.getId() == null) {
      throw new UserFriendlyException(ResultCode.USER_ID_NULL);
    }
    if (userMapper.selectCount(new EntityWrapper().eq("id", userEdit.getId())) == 0) {
      throw new UserFriendlyException(ResultCode.USER_NOT_EXIST);
    }
    if (userEdit.getPhone() != null && !PatternUtil.phonePattern(userEdit.getPhone())) {
      throw new UserFriendlyException(ResultCode.PHONE_VALID);
    }
    if (userEdit.getPassword() != null && !userEdit.getCheckPassword().equals(userEdit.getPassword())) {
      throw new UserFriendlyException(ResultCode.USER_PWD_NOT_SAME);
    }
    if (userEdit.getName() == null) {
      throw new UserFriendlyException(ResultCode.USER_NAME_NULL);
    }
    List<User> ul2 = userMapper.selectList((new EntityWrapper<User>()).eq("phone", userEdit.getPhone()));
    if (ul2.size() != 0 && ul2.get(0).getId() != userEdit.getId()) {//同理Phone不重复
      throw new UserFriendlyException(ResultCode.USER_PHONE_EXIST);
    }
    User u = new User(userEdit);
    userMapper.updateById(u);
    return RestResultGenerator.genResult(null, ResultCode.EDI_SUCCESS);
  }

  public ResponseResult uploadImg(MultipartFile file, Integer id, HttpSession session) {
    if (userMapper.selectById(id) == null) {
      throw new UserFriendlyException(ResultCode.ACC_USER_NOT_EXIST);
    }
    String path = session.getServletContext().getRealPath("assets/images/avatar");
    if (!new File(path).exists()) {
      new File(path).mkdir();
    }
    String fileName = file.getOriginalFilename();
    String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
    String name = fileName.substring(0, fileName.lastIndexOf("."));
    String ID = String.valueOf(new Date().getTime());
    String saveFileName = ID + "-" + name + "." + fileExtensionName;
    File targetFile = new File(path, saveFileName);
    try {
      file.transferTo(targetFile);
    } catch (Exception e) {
      throw new UserFriendlyException(ResultCode.UPLOAD_PIC_FAIL);
    }
    User user = new User();
    user.setId(id);
    user.setImageUrl("assets/images/avatar/" + saveFileName);
    userMapper.updateById(user);
    return RestResultGenerator.genResult(user.getImageUrl(), ResultCode.UPLOAD_PIC_SUCCESS);
  }

  //登录
  public ResponseResult login(UserLogin userLogin, HttpSession session) {
    Account account = accountService.selectOne(
      new EntityWrapper<Account>()
        .eq("login_account", userLogin.getUserName())
        .eq("passwd", userLogin.getPassword()));
    if (account == null) {
      throw new UserFriendlyException(ResultCode.LOGIN_ERROR);
    }
    User user = userMapper.selectById(account.getUserId());
    user.setLastLoginDate(new Date().getTime());//获取当前时间
    userMapper.updateById(user);
    user.setPasswd(null);//除去密码字段
    session.setAttribute("user", user);
    MemoryData.setSessionMap(user.getId().toString(), session);
    return RestResultGenerator.genResult(user, ResultCode.LOGIN_SUCCESS);
  }

  //检测session
  public ResponseResult check(UserCheck userCheck) {
    HttpSession oldSession = MemoryData.getSessionMap(userCheck.getCode());
    if (oldSession == null) {
      throw new UserFriendlyException(ResultCode.CHECK_FAILTURE);
    }
    return RestResultGenerator.genResult(null, ResultCode.CHECK_SUCCESS);
  }

  //登出
  public ResponseResult logout(HttpSession session) {
    User user = null;
    user = (User) session.getAttribute("user");
    if (user != null) {
      MemoryData.remove(user.getId().toString());
      session.removeAttribute("user");
      session.invalidate();
    }
    return RestResultGenerator.genResult(null, ResultCode.LOGOUT_SUCCESS);
  }

  //注册
  public ResponseResult register(UserRegister userRegister, HttpSession session) {
    if (!userRegister.getConfirm().equals(userRegister.getPassword())) {
      throw new UserFriendlyException(ResultCode.USER_PWD_NOT_SAME);
    }
    JSONObject json = JSON.parseObject(String.valueOf(session.getAttribute(userRegister.getMobile())));
//    if (json == null) {
//      throw new UserFriendlyException(ResultCode.CAPTCHA_ERROR);
//    }
//    if ((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5) {
//      throw new UserFriendlyException(ResultCode.CAPTCHA_OUTDATE);
//    }
//    if (!json.getString("captcha").equals(userRegister.getCaptcha())) {
//      throw new UserFriendlyException(ResultCode.CAPTCHA_ERROR);
//    }
    if (!PatternUtil.phonePattern(userRegister.getMobile())) {
      throw new UserFriendlyException(ResultCode.PHONE_VALID);
    }
    if (userMapper.selectCount(new EntityWrapper<User>().eq("phone", userRegister.getMobile())) > 0) {
      throw new UserFriendlyException(ResultCode.USER_PHONE_EXIST);
    }

    User u = new User();
    u.setRegistDate(System.currentTimeMillis());
    u.setPasswd(userRegister.getPassword());
    u.setPhone(userRegister.getMobile());
    u.setRoleId(userRegister.getRoleId());
    u.setCollegeId(userRegister.getCollegeId());
    userMapper.insert(u);//插入用户数据成功
    Account mobileAccount = new Account();
    mobileAccount.setLoginAccount(userRegister.getMobile());
    mobileAccount.setPasswd(userRegister.getPassword());
    mobileAccount.setUserId(u.getId());
    mobileAccount.setMethod("mobile");
    accountMapper.insert(mobileAccount);

    Account emailAccount = new Account();
    emailAccount.setLoginAccount(userRegister.getMail());
    emailAccount.setPasswd(userRegister.getPassword());
    emailAccount.setUserId(u.getId());
    emailAccount.setMethod("mail");
    accountMapper.insert(emailAccount);

    return RestResultGenerator.genResult(null, ResultCode.REGIST_SUCCESS);
  }

  //验证 验证码
  public ResponseResult checkCaptcha(ForgetPwd forgetPwd, HttpSession session) {
    JSONObject json = JSON.parseObject(String.valueOf(session.getAttribute(forgetPwd.getMobile())));
    if (json == null) {
      throw new UserFriendlyException(ResultCode.CAPTCHA_ERROR);
    }
    if ((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5) {
      throw new UserFriendlyException(ResultCode.CAPTCHA_OUTDATE);
    }
    if (!json.getString("captcha").equals(forgetPwd.getCaptcha())) {
      throw new UserFriendlyException(ResultCode.CAPTCHA_ERROR);
    }
    return RestResultGenerator.genResult(null, ResultCode.SUCCESS);
  }

  //修改密码
  public ResponseResult updatePwd(ForgetPwd forgetPwd) {
    if (!forgetPwd.getConfirm().equals(forgetPwd.getPassword())) {
      throw new UserFriendlyException(ResultCode.USER_PWD_NOT_SAME);
    }
    User user = new User();
    user.setId(forgetPwd.getId());
    List<Account> accounts = accountService.selectList(new EntityWrapper<Account>().eq("user_id", forgetPwd.getId()));
    for (Account a : accounts) {
      a.setPasswd(forgetPwd.getPassword());
      accountMapper.updateById(a);
    }//修改每一个账户的密码
    user.setPasswd(forgetPwd.getPassword());
    userMapper.updateById(user);
    return RestResultGenerator.genResult(null, ResultCode.SUCCESS);
  }


}
