package com.incubator.edupayroll.mapper.document;

import com.incubator.edupayroll.dto.document.Document;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.mapper.export.ExportMapper;
import com.incubator.edupayroll.mapper.record.RecordMapper;

public class DocumentMapper {
  public static Document toDTO(DocumentEntity document) {
    var exports = document.getExports().stream().map(ExportMapper::toDTO).toList();
    var records = document.getRecords().stream().map(RecordMapper::toDTO).toList();

    return new Document(
        document.getId(),
        document.getName(),
        document.getTime().toString(),
        records,
        exports,
        document.getCreatedAt(),
        document.getUpdatedAt());
  }
}
