package com.incubator.edupayroll.repository.record;

import com.incubator.edupayroll.entity.record.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {

}
