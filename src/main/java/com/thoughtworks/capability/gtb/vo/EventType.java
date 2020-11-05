package com.thoughtworks.capability.gtb.vo;

import lombok.Getter;

@Getter
public enum EventType {
  UPLOAD("U"), DOWNLOAD("D");

  private String code;

  EventType(String code) {
    this.code = code;
  }
}
