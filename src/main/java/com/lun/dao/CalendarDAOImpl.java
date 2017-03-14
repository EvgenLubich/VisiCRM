package com.lun.dao;

import com.lun.model.Cal;
import com.lun.model.Tracking;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lubich on 14.03.17.
 */
@Repository
public class CalendarDAOImpl implements CalendarDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cal> getOurs(Date start, Date finish){
        Query query = em.createQuery("SELECT c FROM Cal c WHERE c.date BETWEEN :start AND :finish");
        query.setParameter("start", start, TemporalType.DATE);
        query.setParameter("finish", finish, TemporalType.DATE);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
