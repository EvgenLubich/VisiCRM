package com.lun.dao;

import com.lun.model.Tracking;

import java.util.Date;
import java.util.List;

/**
 * Created by lubich on 14.02.17.
 */
public interface TrackingDAO {

    void persist(Tracking tracking);
    Tracking getStatusByUserId(int userId, Date day, Date start, Date finish);
    List<Tracking> getTracksComein(int userId);
    List<Tracking> getTracksForOneDay(int userId, Date start, Date finish);
    Tracking updateTime(int userid, Date dateOld, Date dateNew);
    void deleteTrack(int userid, Date start, Date finish);
    List<Tracking> getDaysOffCount(int userId, Date start, Date fin);
}
