package com.incubator.edupayroll.mapper.export;

import com.incubator.edupayroll.dto.export.Export;
import com.incubator.edupayroll.entity.export.ExportEntity;

public class ExportMapper {
  public static Export toDTO(ExportEntity export) {
    return new Export(
        export.getId(),
        export.getUrl(),
        export.getName(),
        export.getStatus(),
        export.getCreatedAt(),
        export.getUpdatedAt());
  }
}
