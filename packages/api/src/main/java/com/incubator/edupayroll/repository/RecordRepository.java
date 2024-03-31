package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {

}
