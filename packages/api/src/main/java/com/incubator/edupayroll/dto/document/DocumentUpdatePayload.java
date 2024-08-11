package com.incubator.edupayroll.dto.document;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class DocumentUpdatePayload {

  @NotNull public boolean success;
}
