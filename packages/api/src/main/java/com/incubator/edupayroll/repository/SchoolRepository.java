package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {

}
