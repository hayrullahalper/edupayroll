package com.incubator.edupayroll.entity.export;

import lombok.Getter;

@Getter
public enum ExportStatus {
  FAILED("FAILED"),
  PENDING("PENDING"),
  COMPLETED("COMPLETED"),
  IN_PROGRESS("IN_PROGRESS");

  private final String value;

  ExportStatus(String value) {
    this.value = value;
  }
}
