package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.record.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {

}
