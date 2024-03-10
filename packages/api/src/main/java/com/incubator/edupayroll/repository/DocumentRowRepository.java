package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.DocumentRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRowRepository extends JpaRepository<DocumentRowEntity, UUID> {

}
