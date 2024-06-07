package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.Teacher;
import com.incubator.edupayroll.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    long countByUser(User user);

    Page<Teacher> findAllByUser(User user, Pageable pageable);
}
