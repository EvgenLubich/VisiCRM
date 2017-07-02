package com.lun.service;

import com.lun.model.Tracking;
import com.lun.util.Month;
import com.lun.util.WorkingDay;
import com.lun.util.WorkingOff;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lubich on 16.02.17.
 */
public interface TrackingService {
    List<Tracking> getTracksComein(String userName);
    List<WorkingDay> getWorkingDay(String userName);
    List<WorkingDay> getWorkingDayForSomeMonth(String userName, int year, int month);
    WorkingOff getWorkingOff(List<WorkingDay> workingDay, String userName);
    WorkingOff getWorkingOffHistory(List<WorkingDay> workingDay, String userName, int year, int month);
    void countStudent();
    Map<String, Integer> getCallendar();
    void updateTime(String user, String date, String timeOld, String time);
    List<Date> getDaysBetweenDates(Date startdate, Date enddate);
    void addHospital(Date start, Date end, String userLogin);
    void deleteTrack(Date date, String userLogin);
    void addVacation(Date start, Date end, String userLogin);
    void addCommanding(Date start, Date end, String userLogin);
//    List<Month> getMonths(String userName);
}
