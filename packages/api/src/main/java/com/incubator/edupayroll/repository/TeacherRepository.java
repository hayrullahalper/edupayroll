package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {

  @Query(
      "SELECT COUNT(t) FROM TeacherEntity t WHERE t.user = :user"
          + " AND (:firstName IS NULL OR t.firstName LIKE %:firstName%)"
          + " AND (:lastName IS NULL OR t.lastName LIKE %:lastName%)"
          + " AND (:branch IS NULL OR t.branch LIKE %:branch%)"
          + " AND (:idNumber IS NULL OR t.idNumber = :idNumber)")
  long countFilteredTeachers(
      @Param("user") UserEntity user,
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("branch") String branch,
      @Param("idNumber") String idNumber);

  @Query(
      "SELECT t FROM TeacherEntity t WHERE t.user = :user"
          + " AND (:firstName IS NULL OR t.firstName LIKE %:firstName%)"
          + " AND (:lastName IS NULL OR t.lastName LIKE %:lastName%)"
          + " AND (:branch IS NULL OR t.branch LIKE %:branch%)"
          + " AND (:idNumber IS NULL OR t.idNumber = :idNumber)")
  Page<TeacherEntity> findFilteredTeachers(
      @Param("user") UserEntity user,
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("branch") String branch,
      @Param("idNumber") String idNumber,
      Pageable pageable);

  List<TeacherEntity> findAllByUser(UserEntity user);
}
