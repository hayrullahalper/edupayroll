package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.DocumentRowEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentRowDAO extends BaseDAO<DocumentRowEntity> {

    @Autowired
    public DocumentRowDAO(EntityManager em) {
        super(em, DocumentRowEntity.class);
    }

}
