package com.controller;

import com.controller.exceptions.ResponseResult;
import com.model.accountdto.AccountAdd;
import com.model.accountdto.AccountDel;
import com.model.accountdto.AccountEdit;
import com.model.accountdto.AccountQuery;
import com.model.userdto.UserAdd;
import com.model.userdto.UserDel;
import com.model.userdto.UserEdit;
import com.model.userdto.UserQuery;
import com.service.AccountService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/6/11.
 */
@Controller
@RequestMapping(value = "/account", produces = {"application/json;charset=UTF-8"})
public class AccountController {
  @Resource
  AccountService accountService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseResult getList(AccountQuery accountQuery) {
    return accountService.getList(accountQuery);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult addOne(AccountAdd accountAdd) {
    return accountService.addOne(accountAdd);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseResult deletes(AccountDel accountDel) {
    return accountService.deletes(accountDel);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseResult edit(AccountEdit accountEdit) {
    return accountService.edit(accountEdit);
  }
}
