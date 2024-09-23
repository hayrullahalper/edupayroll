package com.incubator.edupayroll.common.response;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ResponseError<E> {
  @NotNull public E code;
  public String message;
}
