package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {

  @Query(
      "SELECT COUNT(t) FROM TeacherEntity t WHERE t.user = :user"
          + " AND (:name IS NULL OR t.name LIKE %:name%)")
  long count(@Param("user") UserEntity user, @Param("name") Optional<String> name);

  @Query(
      "SELECT t FROM TeacherEntity t WHERE t.user = :user"
          + " AND (:name IS NULL OR t.name LIKE %:name%)")
  Page<TeacherEntity> findAllByUser(
      @Param("user") UserEntity user, @Param("name") Optional<String> name, Pageable pageable);

  @Modifying
  @Query("DELETE FROM TeacherEntity t WHERE t.user = :user AND t.id IN :ids")
  void deleteAll(@Param("user") UserEntity user, @Param("ids") List<UUID> ids);

  @Modifying
  @Query("DELETE FROM TeacherEntity t WHERE t.user = :user AND t.id NOT IN :ids")
  void deleteAllExcludingIds(@Param("user") UserEntity user, @Param("ids") List<UUID> ids);
}
