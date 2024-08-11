package com.incubator.edupayroll.dto.export;

import com.incubator.edupayroll.entity.export.ExportStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Value;

@Value
public class Export {
  @NotNull UUID id;

  String url;
  @NotNull String name;
  @NotNull ExportStatus status;

  @NotNull LocalDateTime createdAt;
  @NotNull LocalDateTime updatedAt;
}
