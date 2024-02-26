package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.SchoolEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class SchoolDAO extends BaseDAO<SchoolEntity> implements Serializable {

    @Autowired
    public SchoolDAO(EntityManager em) {
        super(em, SchoolEntity.class);
    }

}
