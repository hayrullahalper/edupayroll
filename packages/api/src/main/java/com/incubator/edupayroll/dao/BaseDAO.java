package com.incubator.edupayroll.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public abstract class BaseDAO<T extends Serializable> {

    protected EntityManager em;
    private final Class<T> clazz;

    public BaseDAO(EntityManager em, Class<T> clazz) {
        this.em = em;
        this.clazz = clazz;
    }

    @Transactional
    public void save(T object) {
        em.persist(object);
    }

    public T findById(String id) {
        return em.find(clazz, UUID.fromString(id));
    }

    public List<T> findAll() {
        TypedQuery<T> query = em.createQuery("FROM " + clazz.getSimpleName(), clazz);
        return query.getResultList();
    }

    @Transactional
    public T update(T object) {
        return em.merge(object);
    }

    @Transactional
    public void delete(String id) {
        em.remove(findById(id));
    }

    @Transactional
    public int deleteAll() {
        return em.createQuery("DELETE FROM " + clazz.getSimpleName()).executeUpdate();
    }

}
