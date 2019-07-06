package com.model.menudto;

/**
 * Created by Administrator on 2019/6/30.
 */
public class MenuEdit {
  private Integer id;

  private String name;

  private String icon;

  private Integer order;

  private Integer targetId;//目标id 更改次序用

  private Integer targetOrder;//目标次序 更改次序用

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getTargetId() {
    return targetId;
  }

  public Integer getTargetOrder() {
    return targetOrder;
  }

  public void setTargetOrder(Integer targetOrder) {
    this.targetOrder = targetOrder;
  }

  public void setTargetId(Integer targetId) {
    this.targetId = targetId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }
}
