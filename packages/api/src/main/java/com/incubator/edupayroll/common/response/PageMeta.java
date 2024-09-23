package com.incubator.edupayroll.common.response;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PageMeta {

  @NotNull public int limit;

  @NotNull public int offset;
  @NotNull public long total;
}
