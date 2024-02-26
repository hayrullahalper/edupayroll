package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.DocumentEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class DocumentDAO extends BaseDAO<DocumentEntity> implements Serializable {

    @Autowired
    public DocumentDAO(EntityManager em) {
        super(em, DocumentEntity.class);
    }

}
