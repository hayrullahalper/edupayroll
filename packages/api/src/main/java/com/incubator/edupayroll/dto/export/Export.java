package com.incubator.edupayroll.dto.export;

import java.util.UUID;
import lombok.Value;

@Value
public class Export {
  String url;
  UUID documentId;
}
