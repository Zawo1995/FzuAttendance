package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.Account;
import com.model.User;
import com.model.accountdto.AccountAdd;
import com.model.accountdto.AccountDel;
import com.model.accountdto.AccountEdit;
import com.model.accountdto.AccountQuery;
import com.model.userdto.UserAdd;
import com.model.userdto.UserDel;
import com.model.userdto.UserEdit;
import com.model.userdto.UserQuery;

import java.util.List;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface AccountService extends IService<Account>{
  ResponseResult getList(AccountQuery t);
  ResponseResult addOne(AccountAdd t);
  ResponseResult addOnes(List<AccountAdd> list);
  ResponseResult deletes(AccountDel t);
  ResponseResult edit(AccountEdit t);
}
