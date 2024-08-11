package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {
  void deleteAllByDocument(DocumentEntity document);

  Optional<RecordEntity> findByHeadIsTrue();

  Optional<RecordEntity> findByNext(RecordEntity next);

  Optional<RecordEntity> findByHeadIsTrueAndDocument(DocumentEntity document);
}
