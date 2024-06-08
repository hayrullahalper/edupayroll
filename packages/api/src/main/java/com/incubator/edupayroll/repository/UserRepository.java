package com.incubator.edupayroll.repository;

import com.incubator.edupayroll.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    public boolean existsByEmail(String email);

    public Optional<UserEntity> findByEmail(String email);

}
