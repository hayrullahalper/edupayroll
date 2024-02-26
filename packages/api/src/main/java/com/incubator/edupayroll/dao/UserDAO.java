package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class UserDAO extends BaseDAO<UserEntity> implements Serializable {

    @Autowired
    public UserDAO(EntityManager em) {
        super(em, UserEntity.class);
    }

}
