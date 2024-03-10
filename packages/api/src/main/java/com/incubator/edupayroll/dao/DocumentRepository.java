package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
    
}
