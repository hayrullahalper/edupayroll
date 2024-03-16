package com.incubator.edupayroll.repository.export;

import com.incubator.edupayroll.entity.export.ExportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExportRepository extends JpaRepository<ExportEntity, UUID> {

}
