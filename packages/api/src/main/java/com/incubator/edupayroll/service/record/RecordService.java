package com.incubator.edupayroll.service.record;

import com.incubator.edupayroll.dto.record.Record;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.entity.record.RecordType;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.mapper.record.RecordMapper;
import com.incubator.edupayroll.repository.RecordRepository;
import com.incubator.edupayroll.service.document.DocumentService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecordService {
    final RecordRepository recordRepository;
    final DocumentService documentService;

    public RecordService(RecordRepository recordRepository, DocumentService documentService) {
        this.recordRepository = recordRepository;
        this.documentService = documentService;
    }

    @Transactional
    public RecordEntity create(
            RecordType type, String information, TeacherEntity teacher, DocumentEntity document) {
        Hibernate.initialize(document.getRecords());
        int line = document.getRecords().size() + 1;
        return recordRepository.saveAndFlush(
                new RecordEntity(line, type, information, teacher, document));
    }

    @Transactional
    public List<Record> getRecordsByDocumentId(UUID documentId) {
        DocumentEntity document = documentService.getById(documentId);

        Hibernate.initialize(document.getRecords());
        List<RecordEntity> recordEntities = document.getRecords();

        recordEntities.sort(Comparator.comparing(RecordEntity::getLine));

        return recordEntities.stream().map(RecordMapper::toDTO).collect(Collectors.toList());
    }

    public RecordEntity getById(UUID recordId) {
        return recordRepository.findById(recordId).orElseThrow();
    }

    public RecordEntity updateType(RecordEntity record, RecordType type) {
        record.setType(type);
        return recordRepository.saveAndFlush(record);
    }

    public RecordEntity updateTeacher(RecordEntity record, TeacherEntity teacher) {
        record.setTeacher(teacher);
        return recordRepository.saveAndFlush(record);
    }

    public RecordEntity updateInformation(RecordEntity record, String information) {
        record.setInformation(information);
        return recordRepository.saveAndFlush(record);
    }

    public boolean delete(RecordEntity record) {
        var recordId = record.getId();

        recordRepository.delete(record);

        return !recordRepository.existsById(recordId);
    }

}
