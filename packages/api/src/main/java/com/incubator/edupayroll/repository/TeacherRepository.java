package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {

}
