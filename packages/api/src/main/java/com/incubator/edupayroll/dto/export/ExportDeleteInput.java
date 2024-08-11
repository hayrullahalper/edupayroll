package com.incubator.edupayroll.dto.export;

import com.incubator.edupayroll.util.selection.SelectionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Value;

@Value
public class ExportDeleteInput {

  @NotEmpty(message = "Export IDs are required")
  public List<@NotBlank String> ids;

  @Nullable public SelectionType type;
}
