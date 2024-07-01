package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.school.SchoolEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {
  List<SchoolEntity> findByUser(UserEntity user);
}
