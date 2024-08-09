package com.incubator.edupayroll.service.document;

import com.incubator.edupayroll.dto.document.DocumentCreateInput;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.DocumentRepository;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
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

  public DocumentEntity getById(UserEntity user, UUID id) {
    var maybeDocument = documentRepository.findById(id);
    var document = maybeDocument.orElseThrow(() -> DocumentNotFoundException.byId(id));

    if (!document.getUser().getId().equals(user.getId())) {
      throw AccessDeniedException.byUser(user);
    }

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

  public DocumentEntity create(DocumentCreateInput input, UserEntity user) {
    DocumentEntity document = new DocumentEntity();

    document.setUser(user);

    document.setName(input.getName());
    document.setTime(input.getTime());
    document.setExports(new ArrayList<>());
    document.setRecords(new ArrayList<>());

    return documentRepository.save(document);
  }

  // TODO: implement remove and bulkRemove methods after implementation of exports and records
}
