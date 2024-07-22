package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {
  long countByUser(UserEntity user);

  Page<TeacherEntity> findAllByUser(UserEntity user, Pageable pageable);
  List<TeacherEntity> findAllByUser(UserEntity user);
}
