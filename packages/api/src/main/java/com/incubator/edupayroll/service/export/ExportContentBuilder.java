package com.incubator.edupayroll.service.export;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import java.io.File;

public interface ExportContentBuilder {

  void init(DocumentEntity document);

  File build();
}
