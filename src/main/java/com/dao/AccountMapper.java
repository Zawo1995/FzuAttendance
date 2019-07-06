package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.model.Account;
import com.model.accountdto.AccountAdd;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper extends BaseMapper<Account>{
  void insertBatch(List<AccountAdd> accountAddList);
}
