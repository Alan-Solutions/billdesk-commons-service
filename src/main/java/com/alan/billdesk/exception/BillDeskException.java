package com.alan.billdesk.exception;

import lombok.Data;
import org.json.simple.JSONObject;

@Data
public class BillDeskException extends RuntimeException {

  private boolean exceptionOccured;

  private final int statusCode;

  private final String errorMessage;

  private final JSONObject errorJson;

  public BillDeskException(Throwable t, int statusCode, String errorMessage) {
    super(t);
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
    this.errorJson = null;
  }

  public BillDeskException(Throwable t, int statusCode, String errorMessage, JSONObject errorJson) {
    super(t);
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
    this.errorJson = errorJson;
  }

}
