package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.DocumentRow;
import com.incubator.edupayroll.entity.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class DocumentRowDAO {

    private final EntityManager entityManager;

    @Autowired
    public DocumentRowDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(DocumentRow documentRow) {
        entityManager.persist(documentRow);
    }

    public DocumentRow findById(String id) {
        UUID uuid;

        uuid = UUID.fromString(id);

        return entityManager.find(DocumentRow.class, uuid);
    }

    public List<DocumentRow> findAll() {
        TypedQuery<DocumentRow> query = entityManager.createQuery("FROM DocumentRow ", DocumentRow.class);
        return query.getResultList();
    }

    @Transactional
    public void update(DocumentRow documentRow) {
        entityManager.merge(documentRow);
    }

    @Transactional
    public void delete(String id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("DELETE FROM DocumentRow").executeUpdate();
    }

}
