package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.Document;
import com.incubator.edupayroll.entity.DocumentRow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class DocumentDAO {

    private final EntityManager entityManager;

    @Autowired
    public DocumentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(Document document) {
        entityManager.persist(document);
    }

    public Document findById(String id) {
        UUID uuid;

        uuid = UUID.fromString(id);

        return entityManager.find(Document.class, uuid);
    }

    public List<Document> findAll() {
        TypedQuery<Document> query = entityManager.createQuery("FROM Document ", Document.class);
        return query.getResultList();
    }

    @Transactional
    public void update(Document document) {
        entityManager.merge(document);
    }

    @Transactional
    public void delete(String id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("DELETE FROM Document").executeUpdate();
    }

}
