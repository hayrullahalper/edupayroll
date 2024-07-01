package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.record.RecordEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {}
