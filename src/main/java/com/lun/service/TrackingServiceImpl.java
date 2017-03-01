package com.lun.service;

import com.lun.dao.AppUserDAO;
import com.lun.dao.TrackingDAO;
import com.lun.model.ActionType;
import com.lun.model.AppUser;
import com.lun.model.Tracking;
import com.lun.util.Month;
import com.lun.util.WorkingDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lubich on 16.02.17.
 */
@Service
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private TrackingDAO trackingDAO;

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
        int day = 1;

        Calendar c1 = new GregorianCalendar(year, month, day);
        Calendar c2 = new GregorianCalendar(year, month, day);


        for (int i = 1; i<= 28; i++) {
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
        List<WorkingDay> workingDay = new ArrayList<>();

        int y = year;
        int m = month;
        int day = 1;

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m);
        int maxDays = c.getActualMaximum( Calendar.DAY_OF_MONTH );

        Calendar c1 = new GregorianCalendar(year, month, day);
        Calendar c2 = new GregorianCalendar(year, month, day);


        for (int i = 1; i<= maxDays; i++) {
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

//    @Override
//    public List<Tracking> getMonths(String userName) {
//
//        AppUser user = appUserDAO.findByLogin(userName);
//        List<Tracking> months = new ArrayList<>();
//
//        return months;
//    }
}
