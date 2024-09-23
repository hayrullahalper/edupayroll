package com.incubator.edupayroll.service.export;

import com.incubator.edupayroll.common.exception.AccessDeniedException;
import com.incubator.edupayroll.common.selection.SelectionType;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.export.ExportEntity;
import com.incubator.edupayroll.entity.export.ExportStatus;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.ExportRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExportService {
  private final ExportRepository exportRepository;

  @Autowired
  public ExportService(ExportRepository exportRepository) {
    this.exportRepository = exportRepository;
  }

  public ExportEntity getById(UUID id) {
    var maybeExport = exportRepository.findById(id);
    return maybeExport.orElseThrow(() -> ExportNotFoundException.byId(id));
  }

  public ExportEntity getById(UserEntity user, UUID id) {
    var maybeExport = exportRepository.findById(id);
    var export = maybeExport.orElseThrow(() -> ExportNotFoundException.byId(id));

    if (!export.getDocument().getUser().getId().equals(user.getId())) {
      throw AccessDeniedException.byUser(user);
    }

    return export;
  }

  public long count(UserEntity user, Optional<String> name) {
    return exportRepository.count(user, name.map(String::trim));
  }

  public List<ExportEntity> getAll(UserEntity user, int limit, int offset, Optional<String> name) {
    int number = Math.round((float) offset / limit);

    var sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id"));

    var pr = PageRequest.of(number, limit, sort);
    var page = exportRepository.findAllByUser(user, name.map(String::trim), pr);

    return page.get().toList();
  }

  public List<ExportEntity> getAllByDocument(DocumentEntity document) {
    return exportRepository.findAllByDocument(document, Sort.by(Sort.Order.desc("createdAt")));
  }

  public ExportEntity create(DocumentEntity document, String name) {
    var export = new ExportEntity();

    export.setName(name);
    export.setStatus(ExportStatus.PENDING);
    export.setDocument(document);

    return exportRepository.saveAndFlush(export);
  }

  public ExportEntity updateUrl(ExportEntity export, String url) {
    export.setUrl(url);
    return exportRepository.saveAndFlush(export);
  }

  public ExportEntity updateName(ExportEntity export, String name) {
    export.setName(name);
    return exportRepository.saveAndFlush(export);
  }

  public ExportEntity updateStatus(ExportEntity export, ExportStatus status) {
    export.setStatus(status);
    return exportRepository.saveAndFlush(export);
  }

  public void remove(ExportEntity export) {
    exportRepository.delete(export);
  }

  public void removeAllByDocument(DocumentEntity document) {
    exportRepository.deleteAllByDocument(document);
  }

  @Transactional
  public void bulkRemove(UserEntity user, SelectionType type, List<UUID> ids) {
    if (type == SelectionType.INCLUDE) {
      exportRepository.deleteAll(user, ids);
      return;
    }

    exportRepository.deleteAllExcludingIds(user, ids);
  }
}
