package com.lun.dao;

import com.lun.model.ActionType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Evgen on 15.02.2017.
 */

@Repository
public class ActionTypeDAOImpl implements ActionTypeDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ActionType findById(int id) {
        Query query = em.createQuery("SELECT u FROM ActionType u WHERE u.id = :id");
        query.setParameter("id", id);
        try {
            return (ActionType) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
