package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/6/12.
 */
public class PatternUtil {
  private static String phoneRegex = "^1[3456789]\\d{9}$";
  private static String emailRegex = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$";
  private static String qqRegex = "^[1-9][0-9]{4,9}$";
  private static String weChatRegex = "^[a-zA-Z0-9_]+$";

  public static boolean phonePattern(String phoneNumber){
    Pattern p = Pattern.compile(phoneRegex);
    Matcher m = p.matcher(phoneNumber);
    return m.matches();
  }

  public static boolean emailPattern(String email){
    Pattern p = Pattern.compile(emailRegex);
    Matcher m = p.matcher(email);
    return m.matches();
  }
  public static boolean qqPattern(String qq){
    Pattern p = Pattern.compile(qqRegex);
    Matcher m = p.matcher(qq);
    return m.matches();
  }
  public static boolean weChatPattern(String weChat){
    Pattern p = Pattern.compile(weChatRegex);
    Matcher m = p.matcher(weChat);
    return m.matches();
  }
}
