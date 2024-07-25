package com.incubator.edupayroll.mapper.document;

import com.incubator.edupayroll.dto.document.Document;
import com.incubator.edupayroll.dto.export.Export;
import com.incubator.edupayroll.dto.record.Record;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.mapper.export.ExportMapper;
import com.incubator.edupayroll.mapper.record.RecordMapper;
import java.util.List;

public class DocumentMapper {
  public static Document toDTO(DocumentEntity document) {
    List<Export> exports = null;
    List<Record> records = null;

    if (!document.getExports().isEmpty()) {
      exports = document.getExports().stream().map(ExportMapper::toDTO).toList();
    }
    if (!document.getRecords().isEmpty()) {
      records = document.getRecords().stream().map(RecordMapper::toDTO).toList();
    }

    return new Document(
        document.getName(),
        document.getTime(),
        document.getDescription(),
        document.getUser().getId(),
        exports,
        records);
  }
}
