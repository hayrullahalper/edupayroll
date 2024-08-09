package com.incubator.edupayroll.mapper.document;

import com.incubator.edupayroll.dto.document.Document;
import com.incubator.edupayroll.entity.document.DocumentEntity;

public class DocumentMapper {
  public static Document toDTO(DocumentEntity document) {
    return new Document(document.getName(), document.getTime());
  }
}
