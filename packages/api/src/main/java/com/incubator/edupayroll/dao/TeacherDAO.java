package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.TeacherEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class TeacherDAO {

    private final EntityManager entityManager;

    @Autowired
    public TeacherDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(TeacherEntity teacher) {
        entityManager.persist(teacher);
    }

    public TeacherEntity findById(String id) {
        UUID uuid;

        uuid = UUID.fromString(id);

        return entityManager.find(TeacherEntity.class, uuid);
    }

    public List<TeacherEntity> findAll() {
        TypedQuery<TeacherEntity> query = entityManager.createQuery("FROM TeacherEntity", TeacherEntity.class);
        return query.getResultList();
    }

    @Transactional
    public void update(TeacherEntity teacher) {
        entityManager.merge(teacher);
    }

    @Transactional
    public void delete(String id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("DELETE FROM teacher").executeUpdate();
    }

}
