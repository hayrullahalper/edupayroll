package com.incubator.edupayroll.dto.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import jakarta.validation.constraints.Size;
import java.time.YearMonth;
import lombok.Value;

@Value
public class DocumentUpdateInput {

  @Size(min = 3, max = 50, message = "Document's name must be between 3 and 50 characters")
  public String name;

  @JsonSerialize(using = YearMonthSerializer.class)
  @JsonDeserialize(using = YearMonthDeserializer.class)
  public YearMonth time;

  @Size(min = 3, max = 50, message = "Document's description must be between 3 and 50 characters")
  public String description;
}
