package com.alan.billdesk.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonUtils {

  public boolean isNullOrEmpty(String v1) {
    return null == v1 || v1.isBlank();
  }

  public boolean isNullOrEmpty(List v1) {
    return null == v1 || v1.isEmpty();
  }

  public boolean equals(String s1, String s2) {
    return s1.equals(s2);
  }

}
