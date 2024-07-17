package com.incubator.edupayroll.dto.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import com.incubator.edupayroll.entity.export.ExportEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import java.util.List;
import lombok.Value;

@Value
public class Document {

  @NotNull public String name;

  @NotNull
  @JsonSerialize(using = YearMonthSerializer.class)
  @JsonDeserialize(using = YearMonthDeserializer.class)
  public YearMonth time;

  @NotNull public String description;

  @NotNull public UserEntity user;

  @NotNull public List<ExportEntity> exports;

  @NotNull public List<RecordEntity> records;
}
