package com.incubator.edupayroll.dto.document;

import com.incubator.edupayroll.dto.export.Export;
import com.incubator.edupayroll.dto.record.Record;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Value;

@Value
public class Document {
  @NotNull public UUID id;

  @NotNull public String name;
  @NotNull public String time;

  @NotNull public List<Record> records;
  @NotNull public List<Export> exports;

  @NotNull public LocalDateTime createdAt;
  @NotNull public LocalDateTime updatedAt;
}
