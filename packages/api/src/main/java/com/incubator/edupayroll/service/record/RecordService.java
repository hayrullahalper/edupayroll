package com.incubator.edupayroll.service.record;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.entity.record.RecordType;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.repository.RecordRepository;
import com.incubator.edupayroll.service.document.DocumentService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
  private final DocumentService documentService;
  private final RecordRepository recordRepository;

  @Autowired
  public RecordService(RecordRepository recordRepository, DocumentService documentService) {
    this.documentService = documentService;
    this.recordRepository = recordRepository;
  }

  public RecordEntity create(
      DocumentEntity document,
      TeacherEntity teacher,
      RecordType type,
      String information,
      Optional<RecordEntity> previous) {

    var record = new RecordEntity(type, information, false, null, teacher, document);

    if (previous.isPresent()) {
      var previousRecord = previous.get();

      recordRepository.save(record);
      record.setNextId(previousRecord.getNextId());

      previousRecord.setNextId(record.getId());
      recordRepository.save(previousRecord);

      recordRepository.flush();

      return record;
    }

    var head = recordRepository.findByHeadIsTrue();

    head.ifPresent(
        entity -> {
          record.setNextId(entity.getId());
          entity.setHead(false);

          recordRepository.save(entity);
        });

    record.setHead(true);

    recordRepository.save(record);
    recordRepository.flush();

    return record;
  }

  //  public RecordEntity update(
  //      RecordEntity record, TeacherEntity teacher, RecordType type, String information) {
  //    if (teacher != null) {
  //      record.setTeacher(teacher);
  //    }
  //
  //    if (type != null) {
  //      record.setType(type);
  //    }
  //
  //    if (information != null) {
  //      record.setInformation(information);
  //    }
  //
  //    return record;
  //  }

  //
  //  @Transactional
  //  public List<Record> getRecordsByDocumentId(UUID documentId) {
  //    DocumentEntity document = documentService.getById(documentId);
  //
  //    Hibernate.initialize(document.getRecords());
  //    List<RecordEntity> recordEntities = document.getRecords();
  //
  //    recordEntities.sort(Comparator.comparing(RecordEntity::getLine));
  //
  //    return recordEntities.stream().map(RecordMapper::toDTO).collect(Collectors.toList());
  //  }
  //
  //  public RecordEntity getById(UUID recordId) {
  //    return recordRepository.findById(recordId).orElseThrow();
  //  }
  //
  //  public RecordEntity updateType(RecordEntity record, RecordType type) {
  //    record.setType(type);
  //    return recordRepository.saveAndFlush(record);
  //  }
  //
  //  public RecordEntity updateTeacher(RecordEntity record, TeacherEntity teacher) {
  //    record.setTeacher(teacher);
  //    return recordRepository.saveAndFlush(record);
  //  }
  //
  //  public RecordEntity updateInformation(RecordEntity record, String information) {
  //    record.setInformation(information);
  //    return recordRepository.saveAndFlush(record);
  //  }
  //
  //  public boolean delete(RecordEntity record) {
  //    var recordId = record.getId();
  //
  //    recordRepository.delete(record);
  //
  //    return !recordRepository.existsById(recordId);
  //  }
}
