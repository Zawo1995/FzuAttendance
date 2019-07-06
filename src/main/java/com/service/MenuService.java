package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.controller.exceptions.ResponseResult;
import com.model.Menu;
import com.model.School;
import com.model.menudto.MenuEdit;
import com.model.menudto.MenuQuery;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;

/**
 * Created by Administrator on 2019/4/25.
 */
public interface MenuService extends IService<Menu>{
  ResponseResult getAllMenu(MenuQuery menuQuery);
  ResponseResult eidtMenu(MenuEdit menuEdit);
}
