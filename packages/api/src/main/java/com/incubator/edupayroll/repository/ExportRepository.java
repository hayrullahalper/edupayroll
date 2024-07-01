package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.export.ExportEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportRepository extends JpaRepository<ExportEntity, UUID> {}
