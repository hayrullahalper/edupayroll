package com.incubator.edupayroll.dto.document;

import com.incubator.edupayroll.entity.export.ExportEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

@Value
public class Document {

  @NotNull public String name;

  @NotNull public LocalDateTime time;

  @NotNull public String description;

  @NotNull public UserEntity user;

  @NotNull public List<ExportEntity> exports;

  @NotNull public List<RecordEntity> records;
}
