package com.incubator.edupayroll.service.document;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.DocumentRepository;
import com.incubator.edupayroll.service.export.ExportService;
import com.incubator.edupayroll.service.record.RecordService;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
  private final RecordService recordService;
  private final ExportService exportService;
  private final DocumentRepository documentRepository;

  @Autowired
  public DocumentService(
      RecordService recordService,
      ExportService exportService,
      DocumentRepository documentRepository) {
    this.recordService = recordService;
    this.exportService = exportService;
    this.documentRepository = documentRepository;
  }

  public long count(UserEntity user, Optional<String> name) {
    return documentRepository.count(user, name.map(String::trim));
  }

  public List<DocumentEntity> getAll(
      UserEntity user, int limit, int offset, Optional<String> name) {
    int number = Math.round((float) offset / limit);

    var sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id"));

    var pr = PageRequest.of(number, limit, sort);
    var page = documentRepository.findAllByUser(user, name.map(String::trim), pr);

    return page.get().toList();
  }

  public DocumentEntity getById(UUID id) {
    var maybeDocument = documentRepository.findById(id);
    var document = maybeDocument.orElseThrow(() -> DocumentNotFoundException.byId(id));

    var records = recordService.getAllByDocument(document);
    var exports = exportService.getAllByDocument(document);

    document.setRecords(records);
    document.setExports(exports);

    return document;
  }

  public DocumentEntity getById(UserEntity user, UUID id) {
    var maybeDocument = documentRepository.findById(id);
    var document = maybeDocument.orElseThrow(() -> DocumentNotFoundException.byId(id));

    if (!document.getUser().getId().equals(user.getId())) {
      throw AccessDeniedException.byUser(user);
    }

    var records = recordService.getAllByDocument(document);
    var exports = exportService.getAllByDocument(document);

    document.setRecords(records);
    document.setExports(exports);

    return document;
  }

  public DocumentEntity update(DocumentEntity document, String name, YearMonth time) {
    if (name != null) {
      document.setName(name);
    }

    if (time != null) {
      document.setTime(time);
    }

    return documentRepository.saveAndFlush(document);
  }

  public DocumentEntity create(UserEntity user, String name, YearMonth time) {
    DocumentEntity document = new DocumentEntity();

    document.setUser(user);

    document.setName(name);
    document.setTime(time);
    document.setExports(new ArrayList<>());
    document.setRecords(new ArrayList<>());

    return documentRepository.saveAndFlush(document);
  }

  public void remove(DocumentEntity document) {
    recordService.removeAllByDocument(document);
    exportService.removeAllByDocument(document);

    documentRepository.delete(document);
  }
}
