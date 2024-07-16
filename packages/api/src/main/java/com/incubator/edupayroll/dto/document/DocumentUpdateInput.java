package com.incubator.edupayroll.dto.document;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class DocumentUpdateInput {

  @Size(min = 3, max = 50, message = "Document's name must be between 3 and 50 characters")
  public String name;

  // ? @DateTimeFormat
  public LocalDateTime time;

  @Size(min = 3, max = 50, message = "Document's description must be between 3 and 50 characters")
  public String description;
}
