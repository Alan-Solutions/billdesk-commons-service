package com.alan.billdesk.utils;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.response.BillDeskResponse;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CommonUtils {

  private static final JSONObject emptyJson = new JSONObject();

  public JSONObject emptyJson() {
    return emptyJson;
  }

  public boolean isNullOrEmpty(String v1) {
    return null == v1 || v1.isBlank();
  }

  public boolean isNullOrEmpty(List v1) {
    return null == v1 || v1.isEmpty();
  }

  public boolean equals(String s1, String s2) {
    if (null == s1 || null == s2)
      return true;
    return s1.equals(s2);
  }

  public boolean equals(Double d1, Double d2) {
    if (null == d1 || null == d2)
      return true;
    return d1.equals(d2);
  }

  public boolean equals(LocalDateTime d1, LocalDateTime d2) {
    if (null == d1 || null == d2)
      return true;
    return d1.isEqual(d2);
  }

  public JSONObject createErrorJson(String... keyValue) {
    JSONObject jsonObject = new JSONObject();
    String[] kv;
    for (String str : keyValue) {
      kv = str.split(":");
      jsonObject.put(kv[0], kv[1]);
    }
    return jsonObject;
  }

  public JSONObject createErrorJson(int errorCode, String errorMessage, boolean expOccurred) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("errorCode", errorCode);
    jsonObject.put("errorMessage", errorMessage);
    jsonObject.put("exceptionOccurred", expOccurred);
    return jsonObject;
  }

  public <T> BillDeskResponse<T> createResponse(T t, JSONObject errorJson) {
    BillDeskResponse<T> response = new BillDeskResponse<>(Constants.SUCCESS, t, emptyJson());
    if (null != t) {
      return response;
    }
    response.setStatus(Constants.FAILED);
    response.setErrorDetails(errorJson);
    return response;
  }

  public <T> BillDeskResponse<List<T>> createResponse(List<T> t, JSONObject errorJson) {
    BillDeskResponse<List<T>> response = new BillDeskResponse<>(Constants.SUCCESS, t, emptyJson());
    if (null != t && !t.isEmpty()) {
      return response;
    }
    response.setStatus(Constants.FAILED);
    response.setErrorDetails(errorJson);
    return response;
  }

  public <T> BillDeskResponse<T> createResponse(Optional<T> t, JSONObject errorJson) {
    BillDeskResponse<T> response = new BillDeskResponse<>(Constants.SUCCESS, null, emptyJson());
    if (t.isPresent()) {
      response.setBody(t.get());
      return response;
    }
    response.setStatus(Constants.FAILED);
    response.setErrorDetails(errorJson);
    return response;
  }

}
