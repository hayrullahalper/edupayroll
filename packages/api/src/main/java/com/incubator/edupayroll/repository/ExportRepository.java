package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.export.ExportEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportRepository extends JpaRepository<ExportEntity, UUID> {
  void deleteAllByDocument(DocumentEntity document);

  List<ExportEntity> findAllByDocument(DocumentEntity document, Sort sort);

  @Query(
      "SELECT COUNT(t) FROM ExportEntity t WHERE t.document.user = :user"
          + " AND (:name IS NULL OR t.name LIKE %:name%)")
  int count(@Param("user") UserEntity user, @Param("name") Optional<String> name);

  @Query(
      "SELECT t FROM ExportEntity t WHERE t.document.user = :user"
          + " AND (:name IS NULL OR t.name LIKE %:name%)")
  Page<ExportEntity> findAllByUser(
      @Param("user") UserEntity user, @Param("name") Optional<String> name, Pageable pageable);

  @Modifying
  @Query("DELETE FROM ExportEntity t WHERE t.document.user = :user AND t.id IN :ids")
  void deleteAll(@Param("user") UserEntity user, @Param("ids") List<UUID> ids);

  @Modifying
  @Query("DELETE FROM ExportEntity t WHERE t.document.user = :user AND t.id NOT IN :ids")
  void deleteAllExcludingIds(@Param("user") UserEntity user, @Param("ids") List<UUID> ids);
}
