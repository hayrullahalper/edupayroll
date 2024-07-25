package com.incubator.edupayroll.dto.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import lombok.Value;

@Value
public class DocumentCreateInput {

  @NotNull public String name;

  @NotNull
  @JsonSerialize(using = YearMonthSerializer.class)
  @JsonDeserialize(using = YearMonthDeserializer.class)
  public YearMonth time;

  @NotNull public String description;
}
