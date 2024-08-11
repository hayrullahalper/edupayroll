package com.incubator.edupayroll.dto.document;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class DocumentDeletePayload {

  @NotNull public boolean success;
}
