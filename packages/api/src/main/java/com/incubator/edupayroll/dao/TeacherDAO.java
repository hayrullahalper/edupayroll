package com.incubator.edupayroll.dao;

import com.incubator.edupayroll.entity.Teacher;
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
    public void save(Teacher teacher) {
        entityManager.persist(teacher);
    }

    public Teacher findById(String id) {
        UUID uuid;

        uuid = UUID.fromString(id);

        return entityManager.find(Teacher.class, uuid);
    }

    public List<Teacher> findAll() {
        TypedQuery<Teacher> query = entityManager.createQuery("FROM Teacher", Teacher.class);
        return query.getResultList();
    }

    @Transactional
    public void update(Teacher teacher) {
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
