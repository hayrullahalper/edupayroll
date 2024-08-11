package com.incubator.edupayroll.service.record;

public class RecordHoursTooLongException extends RuntimeException {
  public RecordHoursTooLongException() {
    super("Record hours are too long! It should be less or equal to 31.");
  }
}
