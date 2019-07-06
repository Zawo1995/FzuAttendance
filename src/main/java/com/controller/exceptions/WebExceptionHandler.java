package com.controller.exceptions;

import com.aliyuncs.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class WebExceptionHandler {

  private static Logger logger = Logger.getLogger(WebExceptionHandler.class);

  /**
   * 处理所有不可知异常
   *
   * @param e 异常
   * @return json结果
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseResult<Object> handleException(Exception e) {
    // 打印异常堆栈信息
    logger.error(e.getMessage(), e);
    return RestResultGenerator.genResult(ResultCode.UNKNOWN_ERROR);
  }

  @ExceptionHandler(ClientException.class)
  @ResponseBody
  public ResponseResult<Object> handleClientException(ClientException e) {
    logger.error(e.getMessage(), e);
    return RestResultGenerator.genResult(ResultCode.CAPTCHA_SEND_FAIL);
  }

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  @ResponseBody
  public ResponseResult<Object> request405(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
    logger.error(httpRequestMethodNotSupportedException.getMessage(), httpRequestMethodNotSupportedException);
    return RestResultGenerator.genResult(ResultCode.ERROR_405);
  }

  @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
  @ResponseBody
  public ResponseResult<Object> request406(HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException) {
    logger.error(httpMediaTypeNotAcceptableException.getMessage(), httpMediaTypeNotAcceptableException);
    return RestResultGenerator.genResult(ResultCode.ERROR_406);
  }

  @ExceptionHandler({ConversionNotSupportedException.class})
  @ResponseBody
  public ResponseResult<Object> server500(ConversionNotSupportedException conversionNotSupportedException) {
    logger.error(conversionNotSupportedException.getMessage(), conversionNotSupportedException);
    return RestResultGenerator.genResult(ResultCode.ERROR_500);
  }


  @ExceptionHandler({HttpMessageNotWritableException.class})
  @ResponseBody
  public ResponseResult<Object> server500(HttpMessageNotWritableException httpMessageNotWritableException) {
    logger.error(httpMessageNotWritableException.getMessage(), httpMessageNotWritableException);
    return RestResultGenerator.genResult(ResultCode.ERROR_500);
  }

  /**
   * 处理用户友好异常
   * @param ufe
   * @return
   */
  @ExceptionHandler(UserFriendlyException.class)
  @ResponseBody
  public ResponseResult<Object> handleUserFriendlyException(UserFriendlyException ufe){
    logger.error(ufe.getMessage());
    return RestResultGenerator.genResult(ufe.getResultCode());
  }

}
