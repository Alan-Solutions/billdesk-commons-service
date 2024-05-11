package com.alan.billdesk.constants;

public enum DataStatusEnum {

  DATA_NOT_AVAILABLE(1000, "Requested Data Not Available");

  private int code;
  private String message;

  private DataStatusEnum(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
