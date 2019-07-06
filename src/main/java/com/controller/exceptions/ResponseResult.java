package com.controller.exceptions;

public class ResponseResult<T> {
  private boolean success = true;
  private T data;
  private String message;
  private String code;
  private long total;
  private int pageSize;
  private int pageIndex;

  public ResponseResult() {
  }

  public ResponseResult(ResultCode resultCode) {
    this.code = resultCode.getCode();
    this.message = resultCode.getMsg();
  }

  public long getTotal() {
    return this.total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageIndex() {
    return this.pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }


  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
