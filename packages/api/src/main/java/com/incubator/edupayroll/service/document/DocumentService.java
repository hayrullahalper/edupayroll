package com.incubator.edupayroll.service.document;

import com.incubator.edupayroll.dto.document.DocumentCreateInput;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.DocumentRepository;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
  final DocumentRepository documentRepository;

  @Autowired
  public DocumentService(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  public DocumentEntity getById(UUID uuid) {
    return documentRepository
        .findById(uuid)
        .orElseThrow(() -> DocumentNotFoundException.byId(uuid));
  }

  public DocumentEntity update(
      DocumentEntity document, String name, YearMonth time, String description) {
    if (name != null) document.setName(name);

    if (time != null) document.setTime(time);

    if (description != null) document.setDescription(description);

    return documentRepository.saveAndFlush(document);
  }

  public DocumentEntity create(DocumentCreateInput input, UserEntity user) {
    DocumentEntity document = new DocumentEntity();

    document.setName(input.getName());
    document.setTime(input.getTime());
    document.setDescription(input.getDescription());
    document.setUser(user);
    document.setExports(new ArrayList<>());
    document.setRecords(new ArrayList<>());

    return documentRepository.save(document);
  }
}
