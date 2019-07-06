package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.model.Arrange;
import com.model.Course;
import com.model.School;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface ArrangeService extends IService<Arrange> {
  List<Map<String, Object>> getArrangeList(Map params);
  Integer addOne(Arrange arrange);
  Integer deleteOne(Integer id);
  Integer deleteList(List<Integer> ids);
  Integer edit(Arrange arrange);
  Integer deleteByCourseId(Integer id);
}
