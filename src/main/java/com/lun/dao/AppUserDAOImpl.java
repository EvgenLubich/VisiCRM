package com.lun.dao;

import com.lun.model.AppUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by lubich on 09.02.17.
 */

@Repository
public class AppUserDAOImpl implements AppUserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public AppUser findByLogin(String login) {
        Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.login = :login");
        query.setParameter("login", login);
        try {
            return (AppUser) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void persist(AppUser appUser) {
        em.persist(appUser);
    }
}
