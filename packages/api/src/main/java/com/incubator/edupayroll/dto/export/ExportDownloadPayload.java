package com.incubator.edupayroll.dto.export;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ExportDownloadPayload {

  @NotNull public String url;
}
