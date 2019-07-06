package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.dao.ArrangeMapper;
import com.dao.CollegeMapper;
import com.model.College;
import com.model.collegedto.CollegeQuery;
import com.service.ArrangeService;
import com.service.CollegeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("collegeService")
@Transactional(rollbackFor = Exception.class)
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {
  @Resource
  CollegeMapper collegeMapper;

  @Resource
  ArrangeService arrangeService;

  public ResponseResult getCollegeList(CollegeQuery collegeQuery) {
    return RestResultGenerator.genResult(collegeMapper.selectAllCollege(collegeQuery), ResultCode.SUCCESS);
  }

  @Override
  public Integer addOneCollege(College c) {
    return collegeMapper.insert(c);
  }

  @Override
  public Integer deleteOneCollege(Integer id) {
    return collegeMapper.deleteById(id);
  }

  @Override
  public Integer deleteColleges(List<Integer> ids) {
    return collegeMapper.deleteBatchIds(ids);
  }

  @Override
  public Integer editCollege(College c) {
    return collegeMapper.updateById(c);
  }
}
