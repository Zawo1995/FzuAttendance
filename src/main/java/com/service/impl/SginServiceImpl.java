package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.dao.SignDetailMapper;
import com.dao.SignInMapper;
import com.model.SignIn;
import com.service.SignDetailService;
import com.service.SignService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyq-pc on 2019/6/28.SS
 */
@Service("signService")
@Transactional(rollbackFor = Exception.class)
public class SginServiceImpl extends ServiceImpl<SignInMapper, SignIn> implements SignService {
  @Resource
  SignInMapper signInMapper;

  @Override
  public ResponseResult getSignList(SignIn signIn) {
     Map<String ,Object> map = new HashMap<>();
     map.put("arrange_id",3);
     return RestResultGenerator.genResult(signInMapper.selectByMap(map), ResultCode.SUCCESS);
  }

  @Override
  public Integer addOne(SignIn signIn) {
    return signInMapper.insert(signIn);
  }
  @Override
  public Integer updateByEnd(SignIn signIn) {
    signIn.setEndTime( new Date().getTime());
    return  signInMapper.updateById(signIn);
  }
  @Override
  public ResponseResult getAllSignTask(Map params){
    return RestResultGenerator.genResult(signInMapper.getAllSignTask(params),ResultCode.SUCCESS);
  }

  @Override
  public List<Map<String, Object>> getSignId(Map params) {
    return signInMapper.getSignId(params);
  }


}

