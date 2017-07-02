package com.lun.dao;

import com.lun.model.Tracking;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lubich on 14.02.17.
 */
@Repository
public class TrackingDAOImpl implements TrackingDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Tracking tracking) {
        em.persist(tracking);
    }

    @Override
    public void deleteTrack(int userid, Date start, Date finish){
        Query query = em.createQuery("DELETE FROM Tracking t WHERE t.user.id = :user_id AND t.date BETWEEN :start AND :finish");
        query.setParameter("user_id", userid);
        query.setParameter("start", start, TemporalType.DATE);
        query.setParameter("finish", finish, TemporalType.DATE);
        query.executeUpdate();
    }

    @Override
    public Tracking updateTime(int userid, Date dateOld, Date dateNew){
        Query query = em.createQuery("SELECT t FROM Tracking t WHERE t.user.id = :user_id AND t.date = :old");
        query.setParameter("user_id", userid);
        query.setParameter("old", dateOld);
        try {
            query.setMaxResults(1);
            return (Tracking) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Tracking getStatusByUserId(int userId, Date day, Date start, Date finish) {
        Query query = em.createQuery("SELECT t FROM Tracking t WHERE t.user.id = :user_id AND t.date BETWEEN :start AND :finish ORDER BY t.id desc");
        query.setParameter("user_id", userId);
        query.setParameter("start", start, TemporalType.DATE);
        query.setParameter("finish", finish, TemporalType.DATE);
        try {
        query.setMaxResults(1);
            return (Tracking) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tracking> getTracksComein(int userId) {
        Query query = em.createQuery("SELECT t FROM Tracking t WHERE t.user.id = :user_id AND t.action = 1 ORDER BY t.id desc");
        query.setParameter("user_id", userId);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tracking> getDaysOffCount(int userId, Date start, Date fin) {
        Query query = em.createQuery("SELECT t FROM Tracking t WHERE t.user.id = :user_id AND t.action = 6 AND t.date BETWEEN :start AND :fin ORDER BY t.id desc");
        query.setParameter("user_id", userId);
        query.setParameter("start", start, TemporalType.DATE);
        query.setParameter("fin", fin, TemporalType.DATE);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tracking> getTracksForOneDay(int userId, Date start, Date finish){
        Query query = em.createQuery("SELECT t FROM Tracking t WHERE t.user.id = :user_id AND t.date BETWEEN :start AND :finish ORDER BY t.id asc");
        query.setParameter("user_id", userId);
        query.setParameter("start", start, TemporalType.DATE);
        query.setParameter("finish", finish, TemporalType.DATE);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
