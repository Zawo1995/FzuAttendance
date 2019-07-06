package com.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZW on 2019/4/27.
 */
public class ResponseResult2 {
  private static Map resultMap;

  public static <T> String ok(T data) {
    resultMap = new HashMap();
    resultMap.put("success", true);
    resultMap.put("result", data);
    return JSON.toJSONString(resultMap, SerializerFeature.DisableCircularReferenceDetect);
  }

  public static String BuildAddResult(int result){
    resultMap = new HashMap();
    if(result == 0){
      resultMap.put("success",false);
      resultMap.put("result", "新增失败");
    }else{
      resultMap.put("success", true);
      resultMap.put("result", "新增成功");
      resultMap.put("idIndex", result);
    }
    return JSON.toJSONString(resultMap, SerializerFeature.DisableCircularReferenceDetect);
  }

  public static String BuildDelResult(int result){
    resultMap = new HashMap();
    if(result == 0){
      resultMap.put("success",false);
      resultMap.put("result", "删除失败");
    }else{
      resultMap.put("success", true);
      resultMap.put("result", "删除成功");
    }
    return JSON.toJSONString(resultMap, SerializerFeature.DisableCircularReferenceDetect);
  }

  public static String BuildEditResult(int result){
    resultMap = new HashMap();
    if(result == 0){
      resultMap.put("success",false);
      resultMap.put("result", "修改失败");
    }else{
      resultMap.put("success", true);
      resultMap.put("result", "修改成功");
    }
    return JSON.toJSONString(resultMap, SerializerFeature.DisableCircularReferenceDetect);
  }

  public static String fail() {
    resultMap = new HashMap();
    resultMap.put("success", false);
    resultMap.put("result", "获取失败");
    return JSON.toJSONString(resultMap, SerializerFeature.DisableCircularReferenceDetect);
  }

}
