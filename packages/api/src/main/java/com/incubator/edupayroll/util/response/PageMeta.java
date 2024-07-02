package com.incubator.edupayroll.util.response;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PageMeta {
  @NotNull public int page;

  @NotNull public int size;

  @NotNull public int total;
}
