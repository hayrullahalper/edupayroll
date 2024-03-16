package com.incubator.edupayroll.repository.school;

import com.incubator.edupayroll.entity.school.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {

}
