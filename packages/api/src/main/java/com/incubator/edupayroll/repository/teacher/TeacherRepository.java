package com.incubator.edupayroll.repository.teacher;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {

}
