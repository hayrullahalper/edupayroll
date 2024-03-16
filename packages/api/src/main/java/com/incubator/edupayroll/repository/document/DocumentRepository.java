package com.incubator.edupayroll.repository.document;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {

}
