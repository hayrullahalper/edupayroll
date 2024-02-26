package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.TeacherEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAO extends BaseDAO<TeacherEntity> {

    @Autowired
    public TeacherDAO(EntityManager em) {
        super(em, TeacherEntity.class);
    }

}
