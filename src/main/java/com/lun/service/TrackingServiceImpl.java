package com.lun.service;

import com.lun.dao.AppUserDAO;
import com.lun.dao.CalendarDAO;
import com.lun.dao.TrackingDAO;
import com.lun.model.*;
import com.lun.util.Month;
import com.lun.util.WorkingDay;
import com.lun.util.WorkingOff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Calendar;

/**
 * Created by lubich on 16.02.17.
 */
@Service
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private TrackingDAO trackingDAO;
    @Autowired
    private CalendarDAO calendarDAO;

    @Override
    public List<Tracking> getTracksComein(String userName) {
        AppUser user = appUserDAO.findByLogin(userName);
        List<Tracking> tracksComein = trackingDAO.getTracksComein(user.getId());

        return tracksComein;
    }

    @Override
    public List<WorkingDay> getWorkingDay(String userName) {
        AppUser user = appUserDAO.findByLogin(userName);
        List<WorkingDay> workingDay = new ArrayList<>();

        Calendar c = new GregorianCalendar();
        int year = c.get(c.YEAR);
        int month = c.get(c.MONTH);
        int today = c.get(c.DAY_OF_MONTH);
        int day = 1;

        Calendar c1 = new GregorianCalendar(year, month, day);
        Calendar c2 = new GregorianCalendar(year, month, day);


        for (int i = 1; i<= today; i++) {
            if(i>1){
                c1.add(Calendar.DAY_OF_YEAR, 1);
            }
            Date start = new Date(c1.getTimeInMillis());
            c2.add(Calendar.DAY_OF_YEAR, 1);
            Date finish = new Date(c2.getTimeInMillis());


            Map<Date, List<Tracking>> trackingListPerDayMap = new HashMap<>();


            List<Tracking> trackingListPerDay = trackingDAO.getTracksForOneDay(user.getId(), start, finish);
            trackingListPerDayMap.put(start, trackingListPerDay);

            workingDay.add(new WorkingDay(trackingListPerDayMap));

        }

        return workingDay;
    }

    @Override
    public List<WorkingDay> getWorkingDayForSomeMonth(String userName, int year, int month) {
        AppUser user = appUserDAO.findByLogin(userName);
        //AppUser offerDate = appUserDAO.findUserOfferDate(userName);
        List<WorkingDay> workingDay = new ArrayList<>();

        int y = year;
        int m = month;



        Calendar offerIn = new GregorianCalendar();
        offerIn.setTime(user.getOfferin());
        int monthStart = offerIn.get(offerIn.MONTH);
        int yearStart = offerIn.get(offerIn.YEAR);
        int dayStart = offerIn.get(offerIn.DAY_OF_MONTH);


        Calendar c = new GregorianCalendar();

        Calendar c1;
        Calendar c2;
        int maxDays;
        int day;

        if(y == yearStart && m == monthStart) {
            day = dayStart;
            c.set(Calendar.YEAR, y);
            c.set(Calendar.MONTH, m);
            maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            c1 = new GregorianCalendar(year, month, day);
            c2 = new GregorianCalendar(year, month, day);
        } else {
            day = 1;
            c.set(Calendar.YEAR, y);
            c.set(Calendar.MONTH, m);
            maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            c1 = new GregorianCalendar(year, month, day);
            c2 = new GregorianCalendar(year, month, day);
        }

        for (int i = day; i<= maxDays; i++) {
            if(i>1){
                c1.add(Calendar.DAY_OF_YEAR, 1);
            }
            Date start = new Date(c1.getTimeInMillis());
            c2.add(Calendar.DAY_OF_YEAR, 1);
            Date finish = new Date(c2.getTimeInMillis());


            Map<Date, List<Tracking>> trackingListPerDayMap = new HashMap<>();


            List<Tracking> trackingListPerDay = trackingDAO.getTracksForOneDay(user.getId(), start, finish);
            trackingListPerDayMap.put(start, trackingListPerDay);

            workingDay.add(new WorkingDay(trackingListPerDayMap));

        }

        return workingDay;
    }

    @Override
    public WorkingOff getWorkingOff(List<WorkingDay> workingDay, String userName) {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int dayStart = 1;
        int dayEnd = now.getActualMaximum(now.DAY_OF_MONTH);

        Calendar c1 = new GregorianCalendar(year, month, dayStart);
        Calendar c2 = new GregorianCalendar(year, month, dayEnd);

        Date start = new Date(c1.getTimeInMillis());
        Date finish = new Date(c2.getTimeInMillis());

        List<com.lun.model.Cal> calendar = calendarDAO.getOurs(start, finish);
        Map<Date, Integer> exceptionMap = new HashMap<>();
        for (com.lun.model.Cal day: calendar) {
            exceptionMap.put(day.getDay(), day.getHours());
        }

        AppUser user = appUserDAO.findByLogin(userName);

        WorkingOff workingOff = new WorkingOff(workingDay, user, exceptionMap);

        return workingOff;
    }

    @Scheduled(fixedRate=2000)
    @Override
    public void countStudent(){
        System.out.println("Count Student... tracking service");
    }

}
