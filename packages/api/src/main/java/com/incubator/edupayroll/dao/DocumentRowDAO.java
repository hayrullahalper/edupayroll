package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.DocumentRowEntity;
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
    public void save(DocumentRowEntity documentRow) {
        entityManager.persist(documentRow);
    }

    public DocumentRowEntity findById(String id) {
        UUID uuid;

        uuid = UUID.fromString(id);

        return entityManager.find(DocumentRowEntity.class, uuid);
    }

    public List<DocumentRowEntity> findAll() {
        TypedQuery<DocumentRowEntity> query = entityManager.createQuery("FROM DocumentRowEntity ", DocumentRowEntity.class);
        return query.getResultList();
    }

    @Transactional
    public void update(DocumentRowEntity documentRow) {
        entityManager.merge(documentRow);
    }

    @Transactional
    public void delete(String id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("DELETE FROM DocumentRowEntity").executeUpdate();
    }

}
