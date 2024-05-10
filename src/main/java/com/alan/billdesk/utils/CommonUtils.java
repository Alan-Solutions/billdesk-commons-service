package com.alan.billdesk.utils;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

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
    return s1.equals(s2);
  }

  public JSONObject createErrorJson(String... keyValue) {
    JSONObject jsonObject = new JSONObject();
    String []kv;
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

}
