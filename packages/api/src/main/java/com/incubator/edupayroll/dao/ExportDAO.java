package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.ExportEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExportDAO extends BaseDAO<ExportEntity> {

    @Autowired
    public ExportDAO(EntityManager em) {
        super(em, ExportEntity.class);
    }

}
