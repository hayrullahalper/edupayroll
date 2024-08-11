package com.incubator.edupayroll.service.record;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.entity.record.RecordType;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.RecordRepository;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
  private final Integer MAX_DAYS_IN_MONTH = 31;
  private final RecordRepository recordRepository;

  @Autowired
  public RecordService(RecordRepository recordRepository) {
    this.recordRepository = recordRepository;
  }

  @Transactional
  public RecordEntity create(
      DocumentEntity document,
      TeacherEntity teacher,
      RecordType type,
      List<Integer> hours,
      Optional<RecordEntity> previous) {
    if (hours.size() > MAX_DAYS_IN_MONTH) {
      throw new RecordHoursTooLongException();
    }

    var record =
        recordRepository.save(new RecordEntity(type, hours, false, null, teacher, document));

    previous.ifPresentOrElse(
        entity -> {
          record.setNext(entity.getNext());
          entity.setNext(record);

          recordRepository.save(entity);
        },
        () -> {
          var head = recordRepository.findByHeadIsTrue();

          head.ifPresent(
              entity -> {
                record.setNext(entity);
                entity.setHead(false);

                recordRepository.save(entity);
              });

          record.setHead(true);
        });

    return recordRepository.save(record);
  }

  public List<RecordEntity> getAllByDocument(DocumentEntity document) {
    var records = new ArrayList<RecordEntity>();
    var head = recordRepository.findByHeadIsTrueAndDocument(document);

    head.ifPresent(
        entity -> {
          var current = entity;

          while (current != null) {
            if (!current.getDocument().getId().equals(document.getId())) {
              throw RecordStructureCorrupted.byId(current.getId());
            }

            records.add(current);
            current = current.getNext();
          }
        });

    return records;
  }

  public RecordEntity getById(UserEntity user, UUID id) {
    var maybeRecord = recordRepository.findById(id);
    var record = maybeRecord.orElseThrow(() -> RecordNotFoundException.byId(id));

    if (!record.getDocument().getUser().getId().equals(user.getId())) {
      throw AccessDeniedException.byUser(user);
    }

    return record;
  }

  public RecordEntity updateInformation(
      RecordEntity record, TeacherEntity teacher, RecordType type) {
    if (teacher != null) {
      record.setTeacher(teacher);
    }

    if (type != null) {
      record.setType(type);
    }

    return recordRepository.saveAndFlush(record);
  }

  public RecordEntity updateHours(RecordEntity record, List<Integer> hours) {
    if (hours.size() > MAX_DAYS_IN_MONTH) {
      throw new RecordHoursTooLongException();
    }

    record.setHours(hours);
    return recordRepository.saveAndFlush(record);
  }

  @Transactional
  public RecordEntity updatePrevious(RecordEntity record, Optional<RecordEntity> maybePrevious) {
    var tail = record.getNext();
    var middle = maybePrevious.map(RecordEntity::getNext);

    if (maybePrevious.isEmpty()) {
      var head = recordRepository.findByHeadIsTrueAndDocument(record.getDocument());
      var previous = recordRepository.findByNext(record);

      head.ifPresent(
          entity -> {
            record.setNext(entity);
            entity.setHead(false);

            recordRepository.save(entity);
          });

      previous.ifPresent(
          entity -> {
            entity.setNext(tail);
            recordRepository.save(entity);
          });

      record.setHead(true);
      return recordRepository.save(record);
    }

    var previous = maybePrevious.get();

    record.setNext(middle.orElse(null));
    previous.setNext(record);

    middle.ifPresent(
        (entity) -> {
          entity.setNext(tail);
          recordRepository.save(entity);
        });

    recordRepository.save(previous);
    return recordRepository.save(record);
  }

  @Transactional
  public void remove(RecordEntity record) {
    if (record.isHead()) {
      var next = record.getNext();
      next.setHead(true);

      recordRepository.save(next);
    }

    var previous = recordRepository.findByNext(record);

    previous.ifPresent(
        entity -> {
          entity.setNext(record.getNext());
          recordRepository.save(entity);
        });

    recordRepository.delete(record);
  }

  public void removeAllByDocument(DocumentEntity document) {
    recordRepository.deleteAllByDocument(document);
  }
}
