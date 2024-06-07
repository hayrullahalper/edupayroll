package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.School;
import com.incubator.edupayroll.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID> {
    List<School> findByUser(User user);
}
