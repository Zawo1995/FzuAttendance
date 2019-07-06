package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.AccountMapper;
import com.dao.UserMapper;
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
import com.service.AccountService;
import com.service.UserService;
import com.utils.PatternUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("accountService")
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

  public static String EMAIL_METHOD = "mail";
  public static String QQ_METHOD = "qq";
  public static String WECHAT_METHOD = "wechat";


  @Resource
  AccountMapper accountMapper;

  @Resource
  UserMapper userMapper;

  @Override
  public ResponseResult getList(AccountQuery t) {
    EntityWrapper<Account> ew = new EntityWrapper<>();
    if (t.getId() != null) {
      ew.eq("id", t.getId());
    }
    if (t.getUserId() != null) {
      ew.eq("user_id", t.getUserId());
    }
    return RestResultGenerator.genResult(accountMapper.selectMaps(ew), ResultCode.SUCCESS);
  }

  @Override
  public ResponseResult addOne(AccountAdd t) {
    if (t.getUserId() == null || userMapper.selectCount(new EntityWrapper<User>().eq("id", t.getUserId())) == 0) {
      throw new UserFriendlyException(ResultCode.ACC_USER_NOT_EXIST);
    }
    if(t.getMethod() == null){
      throw new UserFriendlyException(ResultCode.ACC_METHOD_NULL);
    }
    if(t.getLoginAccount() == null){
      throw new UserFriendlyException(ResultCode.ACC_ACCOUNT_NULL);
    }
    if (accountMapper.selectCount
      (new EntityWrapper<Account>().eq("user_id", t.getUserId()).eq("method", t.getMethod())) > 0) {
      throw new UserFriendlyException(ResultCode.ACC_METHOD_EXIST);
    }
    if(accountMapper.selectCount(new EntityWrapper<Account>().eq("login_account",t.getLoginAccount())) > 0){
      throw new UserFriendlyException(ResultCode.ACC_EXIST);
    }
    Account a = new Account(t);
    a.setPasswd(userMapper.selectById(t.getUserId()).getPasswd());
    accountMapper.insert(a);
    return RestResultGenerator.genResult(null, ResultCode.ADD_SUCCESS);
  }

  public ResponseResult addOnes(List<AccountAdd> list) {
    if (list.get(0).getUserId() == null || userMapper.selectCount(new EntityWrapper<User>().eq("id", list.get(0).getUserId())) == 0) {
      throw new UserFriendlyException(ResultCode.ACC_USER_NOT_EXIST);
    }
    for (AccountAdd aa : list) {
      if (accountMapper.selectCount
        (new EntityWrapper<Account>().eq("user_id", aa.getUserId()).eq("method", aa.getMethod())) > 0) {
        throw new UserFriendlyException(ResultCode.ACC_METHOD_EXIST);
      }
      if (aa.getMethod().equals(EMAIL_METHOD) && !PatternUtil.emailPattern(aa.getLoginAccount())) {//判断邮箱
        throw new UserFriendlyException(ResultCode.EMAIL_VALID);
      }
      if (aa.getMethod().equals(QQ_METHOD) && !PatternUtil.qqPattern(aa.getLoginAccount())) {//判断QQ
        throw new UserFriendlyException(ResultCode.QQ_VALID);
      }
      if (aa.getMethod().equals(WECHAT_METHOD) && !PatternUtil.weChatPattern(aa.getLoginAccount())) {//判断微信
        throw new UserFriendlyException(ResultCode.WECHAT_VALID);
      }
      if(accountMapper.selectCount(new EntityWrapper<Account>().eq("login_account",aa.getLoginAccount())) > 0){
        throw new UserFriendlyException(ResultCode.ACC_EXIST);
      }
    }
    accountMapper.insertBatch(list);
    return RestResultGenerator.genResult(null, ResultCode.ADD_SUCCESS);
  }

  @Override
  public ResponseResult deletes(AccountDel t) {
    if(t.getId() == null || accountMapper.selectCount(new EntityWrapper<Account>().eq("id",t.getId())) == 0){
      throw new UserFriendlyException(ResultCode.ACC_USER_NOT_EXIST);
    }
    Account a = new Account();
    accountMapper.deleteById(t.getId());
    return RestResultGenerator.genResult(null, ResultCode.DEL_SUCCESS);
  }

  @Override
  public ResponseResult edit(AccountEdit t) {
    if(t.getId() == null || accountMapper.selectCount(new EntityWrapper<Account>().eq("id",t.getId())) == 0){
      throw new UserFriendlyException(ResultCode.ACC_USER_NOT_EXIST);
    }
    if (t.getMethod().equals(EMAIL_METHOD) && !PatternUtil.emailPattern(t.getLoginAccount())) {//判断邮箱
      throw new UserFriendlyException(ResultCode.EMAIL_VALID);
    }
    if (t.getMethod().equals(QQ_METHOD) && !PatternUtil.qqPattern(t.getLoginAccount())) {//判断QQ
      throw new UserFriendlyException(ResultCode.QQ_VALID);
    }
    if (t.getMethod().equals(WECHAT_METHOD) && !PatternUtil.weChatPattern(t.getLoginAccount())) {//判断微信
      throw new UserFriendlyException(ResultCode.WECHAT_VALID);
    }
    List<Account> l = accountMapper.selectList(new EntityWrapper<Account>().eq("login_account",t.getLoginAccount()));
    if(l.size() != 0 && l.get(0).getId() != t.getId()){
      throw new UserFriendlyException(ResultCode.ACC_EXIST);
    }
    Account a = new Account(t);
    accountMapper.updateById(a);
    return RestResultGenerator.genResult(null, ResultCode.EDI_SUCCESS);
  }
}
