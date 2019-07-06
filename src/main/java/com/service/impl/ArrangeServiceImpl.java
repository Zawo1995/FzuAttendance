package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ArrangeMapper;
import com.dao.CollegeMapper;
import com.model.Arrange;
import com.model.College;
import com.service.ArrangeService;
import com.service.CollegeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("arrangeService")
@Transactional(rollbackFor = Exception.class)
public class ArrangeServiceImpl extends ServiceImpl<ArrangeMapper, Arrange> implements ArrangeService {
  @Resource
  ArrangeMapper arrangeMapper;
  private Logger log = Logger.getLogger(ArrangeServiceImpl.class);

  public List<Map<String, Object>> getArrangeList(Map params) {
    Integer courseId = Integer.valueOf(params.get("courseId").toString());
    EntityWrapper<Arrange> ew = new EntityWrapper<>();
    ew.eq("course_id", courseId);
    if(params.get("courseAddress") != null){
      ew.like("course_address",params.get("courseAddress").toString());
    }
    if(params.get("dayNum") != null){
      ew.eq("day_num",Integer.valueOf(params.get("dayNum").toString()));
    }
    if (params.get("lessonBegin") != null) {
      String msg = params.get("lessonBegin").toString();
      Integer small = Integer.valueOf(msg.substring(0, msg.indexOf(":")));
      Integer large = Integer.valueOf(msg.substring(msg.indexOf(":") + 1));
      ew.between("lesson_begin", small, large);
    }
    if (params.get("lessonLength") != null) {
      String msg = params.get("lessonLength").toString();
      Integer small = Integer.valueOf(msg.substring(0, msg.indexOf(":")));
      Integer large = Integer.valueOf(msg.substring(msg.indexOf(":") + 1));
      ew.between("lesson_length", small, large);
    }
    return arrangeMapper.selectMaps(ew);
  }

  @Override
  public Integer addOne(Arrange arrange) {
    return arrangeMapper.insert(arrange);
  }

  @Override
  public Integer deleteOne(Integer id) {
    return arrangeMapper.deleteById(id);
  }

  @Override
  public Integer deleteList(List<Integer> ids) {
    return arrangeMapper.deleteBatchIds(ids);
  }

  @Override
  public Integer edit(Arrange arrange) {
    return arrangeMapper.updateById(arrange);
  }

  @Override
  public Integer deleteByCourseId(Integer id){
    EntityWrapper<Arrange> ew = new EntityWrapper<>();
    ew.eq("course_id",id);
    return arrangeMapper.delete(ew);
  }
}
