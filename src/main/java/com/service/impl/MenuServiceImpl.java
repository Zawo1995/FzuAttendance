package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.controller.exceptions.ResponseResult;
import com.controller.exceptions.RestResultGenerator;
import com.controller.exceptions.ResultCode;
import com.controller.exceptions.UserFriendlyException;
import com.dao.CollegeMapper;
import com.dao.MenuMapper;
import com.dao.RestTimeMapper;
import com.dao.SchoolMapper;
import com.model.College;
import com.model.Menu;
import com.model.RestTime;
import com.model.School;
import com.model.menudto.MenuEdit;
import com.model.menudto.MenuQuery;
import com.model.schooldto.SchoolAdd;
import com.model.schooldto.SchoolDel;
import com.model.schooldto.SchoolEdit;
import com.model.schooldto.SchoolQuery;
import com.service.MenuService;
import com.service.SchoolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/25.
 */
@Service("menuService")
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
  @Resource
  MenuMapper menuMapper;

  public ResponseResult getAllMenu(MenuQuery menuQuery) {
    return RestResultGenerator.genResult(menuMapper.selectAllMenu(menuQuery), ResultCode.SUCCESS);
  }

  public ResponseResult eidtMenu(MenuEdit menuEdit) {
    if (menuEdit.getTargetId() != null) {//要修改次序
      Menu menu1 = new Menu();
      menu1.setOrder(menuEdit.getTargetOrder());
      menu1.setId(menuEdit.getId());
      Menu menu2 = new Menu();
      menu2.setOrder(menuEdit.getOrder());
      menu2.setId(menuEdit.getTargetId());
      menuMapper.updateById(menu1);
      menuMapper.updateById(menu2);
    } else {
      if (menuEdit.getName() == null) {
        throw new UserFriendlyException(ResultCode.MENU_NAME_NULL);
      }
      Menu temp = menuMapper.selectOne(new Menu(menuEdit.getName()));
      if (temp != null && temp.getId() != menuEdit.getId()) {
        throw new UserFriendlyException(ResultCode.MENU_NAME_EXIST);
      }
      Menu menu = new Menu();
      menu.setIcon(menuEdit.getIcon());
      menu.setId(menuEdit.getId());
      menu.setName(menuEdit.getName());
      menuMapper.updateById(menu);
    }
    return RestResultGenerator.genResult(null, ResultCode.EDI_SUCCESS);
  }
}
