package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
  @Query(
      "SELECT COUNT(d) FROM DocumentEntity d WHERE d.user = :user"
          + " AND (:name IS NULL OR d.name LIKE %:name%)")
  int count(@Param("user") UserEntity user, @Param("name") Optional<String> name);

  @Query(
      "SELECT d FROM DocumentEntity d WHERE d.user = :user"
          + " AND (:name IS NULL OR d.name LIKE %:name%)")
  Page<DocumentEntity> findAllByUser(
      @Param("user") UserEntity user, @Param("name") Optional<String> name, Pageable pageable);
}
