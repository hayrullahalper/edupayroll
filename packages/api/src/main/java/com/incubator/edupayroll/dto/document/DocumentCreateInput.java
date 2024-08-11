package com.incubator.edupayroll.dto.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.YearMonth;
import lombok.Value;

@Value
public class DocumentCreateInput {

  @Size(min = 3, max = 50, message = "Document's name must be between 3 and 50 characters")
  @NotEmpty(message = "Document's name is required")
  public String name;

  @NotNull(message = "Document's time is required")
  @JsonSerialize(using = YearMonthSerializer.class)
  @JsonDeserialize(using = YearMonthDeserializer.class)
  public YearMonth time;
}
