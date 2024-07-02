package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {}
