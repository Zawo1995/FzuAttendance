package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.dao.SignDetailMapper;
import com.model.School;
import com.model.SignDetail;
import com.service.SignDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lyq-pc on 2019/6/28.SS
 */
@Service("signDetailService")
@Transactional(rollbackFor = Exception.class)
public class SignDetailServiceImpl extends ServiceImpl<SignDetailMapper, SignDetail> implements SignDetailService {
  @Resource
  SignDetailMapper signDetailMapper;
  @Override
  public ResponseResult addOne(SignDetail signDetail) {
//    SignDetail s = new SignDetail();
//    s.setLatitude(signDetail.getLatitude());
    signDetail.setSignTime(new Date().getTime());
      int i = signDetailMapper.insert(signDetail);
    return null;
  }

  @Override
  public List<Map<String,Object>> getSignList(Integer id) {
    return  signDetailMapper.selectById(id);
  }

  @Override
  public ResponseResult selectSignDetailById(Map params){
    return RestResultGenerator.genResult(signDetailMapper.selectSignDetailById(params), ResultCode.SUCCESS);
  }
}
