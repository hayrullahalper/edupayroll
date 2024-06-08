package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.export.ExportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExportRepository extends JpaRepository<ExportEntity, UUID> {

}
