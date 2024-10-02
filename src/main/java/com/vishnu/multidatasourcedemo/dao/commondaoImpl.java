package com.vishnu.multidatasourcedemo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vishnu.multidatasourcedemo.entity.InstructorDetail;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class commondaoImpl implements commondao{

    private EntityManager entityManager;

    
    @Autowired
    public commondaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(InstructorDetail instructorDetail) {
        try {
            entityManager.persist(instructorDetail);
        } catch (Exception e) {
            throw e;
        }
    }

}
