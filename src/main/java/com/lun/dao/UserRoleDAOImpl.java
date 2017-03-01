package com.lun.dao;

import com.lun.model.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by lubich on 13.02.17.
 */

@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserRole find(String roleName) {
        Query query = em.createQuery("SELECT r FROM UserRole r WHERE r.roleName = :roleName");
        query.setParameter("roleName", roleName);
        return (UserRole) query.getSingleResult();    }
}
