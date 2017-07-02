package com.lun.service;

import com.lun.dao.ActionTypeDAO;
import com.lun.dao.AppUserDAO;
import com.lun.dao.CalendarDAO;
import com.lun.dao.TrackingDAO;
import com.lun.model.*;
import com.lun.util.DayFinalizer;
import com.lun.util.Month;
import com.lun.util.WorkingDay;
import com.lun.util.WorkingOff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private ActionTypeDAO actionTypeDAO;

    @Override
    public List<Tracking> getTracksComein(String userName) {
        AppUser user = appUserDAO.findByLogin(userName);
        List<Tracking> tracksComein = trackingDAO.getTracksComein(user.getId());

        return tracksComein;
    }

    @Override
    @Transactional
    public void updateTime(String userLogin, String date, String timeOld, String time){
        AppUser user = appUserDAO.findByLogin(userLogin);
        String oldDateStr = date + " " + timeOld;
        String newDateStr = date + " " + time + ":00";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateOld = formatter.parse(oldDateStr);
            Date dateNew = formatter.parse(newDateStr);
            Tracking tracking = trackingDAO.updateTime(user.getId(), dateOld, dateNew);
            tracking.setDate(dateNew);
            trackingDAO.persist(tracking);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

//Подсветка выходных
            Cal isWeekend = calendarDAO.isAWeekEnd(start);
            int hours;
            try {
                hours = isWeekend.getHours();
            } catch (Exception e){
                hours = -1;
            }


            List<Tracking> trackingListPerDay = trackingDAO.getTracksForOneDay(user.getId(), start, finish);
            trackingListPerDayMap.put(start, trackingListPerDay);

            workingDay.add(new WorkingDay(trackingListPerDayMap, hours));

        }

        return workingDay;
    }

    @Override
    public List<WorkingDay> getWorkingDayForSomeMonth(String userName, int year, int month) {
        AppUser user = appUserDAO.findByLogin(userName);
        //AppUser offerDate = appUserDAO.findUserOfferDate(userName);
        List<WorkingDay> workingDayList = new ArrayList<>();

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
            day = 1;
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

        int i, count;
        for (i = day, count = 1; i<= maxDays; i++, count++) {
            if(count>1){
                c1.add(Calendar.DAY_OF_YEAR, 1);
            }
            Date start = new Date(c1.getTimeInMillis());
            c2.add(Calendar.DAY_OF_YEAR, 1);
            Date finish = new Date(c2.getTimeInMillis());


            Map<Date, List<Tracking>> trackingListPerDayMap = new HashMap<>();

            //TODO
            //calendarDAO.isAWeekEnd
            Cal isWeekend = calendarDAO.isAWeekEnd(start);
            int hours;
            try {
                hours = isWeekend.getHours();
            } catch (Exception e){
                hours = -1;
            }

            List<Tracking> trackingListPerDay = trackingDAO.getTracksForOneDay(user.getId(), start, finish);
            trackingListPerDayMap.put(start, trackingListPerDay);

            workingDayList.add(new WorkingDay(trackingListPerDayMap, hours));

        }

        return workingDayList;
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

    @Override
    public WorkingOff getWorkingOffHistory(List<WorkingDay> workingDay, String userName, int year, int month) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        int maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar c1 = new GregorianCalendar(year, month, 1);
        Calendar c2 = new GregorianCalendar(year, month, maxDays);

        Date start = new Date(c1.getTimeInMillis());
        Date finish = new Date(c2.getTimeInMillis());

        List<com.lun.model.Cal> calendar = calendarDAO.getOurs(start, finish);
        Map<Date, Integer> exceptionMap = new HashMap<>();
        for (com.lun.model.Cal day: calendar) {
            exceptionMap.put(day.getDay(), day.getHours());
        }

        AppUser user = appUserDAO.findByLogin(userName);
        WorkingOff workingOff = new WorkingOff(workingDay, user, exceptionMap, year, month);

        return workingOff;
    }

    @Override
    public Map<String, Integer> getCallendar(){
        Map<String, Integer> calendarMap = new TreeMap<>();

        List<Cal> calendar = calendarDAO.getCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for (Cal day: calendar){
            Date date = day.getDay();
            Integer hours = day.getHours();
            calendarMap.put(format.format(date), hours);
        }
        return calendarMap;
    }

    @Override
    public List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    @Override
    @Transactional
    public void addHospital(Date start, Date end, String userLogin){

        AppUser user = appUserDAO.findByLogin(userLogin);
        ActionType actionType = actionTypeDAO.findById(7);
        List<Date> dates = this.getDaysBetweenDates(start, end);

        for (Date date: dates) {
            Tracking tracking = new Tracking();
            tracking.setUser(user);
            tracking.setDate(date);
            tracking.setAction(actionType);
            tracking.setWorkingStatus(1);

            trackingDAO.persist(tracking);
        }
    }

    @Override
    @Transactional
    public void addVacation(Date start, Date end, String userLogin){

        AppUser user = appUserDAO.findByLogin(userLogin);
        ActionType actionType = actionTypeDAO.findById(6);
        List<Date> dates = this.getDaysBetweenDates(start, end);

        for (Date date: dates) {
            Tracking tracking = new Tracking();
            tracking.setUser(user);
            tracking.setDate(date);
            tracking.setAction(actionType);
            tracking.setWorkingStatus(1);

            trackingDAO.persist(tracking);
        }
    }

    @Override
    @Transactional
    public void addCommanding(Date start, Date end, String userLogin){

        AppUser user = appUserDAO.findByLogin(userLogin);
        ActionType actionType = actionTypeDAO.findById(8);
        List<Date> dates = this.getDaysBetweenDates(start, end);

        for (Date date: dates) {
            Tracking tracking = new Tracking();
            tracking.setUser(user);
            tracking.setDate(date);
            tracking.setAction(actionType);
            tracking.setWorkingStatus(1);

            trackingDAO.persist(tracking);
        }
    }

    @Override
    @Transactional
    public void deleteTrack(Date date, String userLogin){
        AppUser user = appUserDAO.findByLogin(userLogin);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date finish = c.getTime();

        trackingDAO.deleteTrack(user.getId(), date, finish);
    }

    @Scheduled(cron = "0 21 23 * * *")
    //@Scheduled(fixedDelay=20000)
    @Override
    @Transactional
    public void countStudent(){
        System.out.println("START");
        List<AppUser> users = appUserDAO.findAllUsers();

        Calendar c1 = new GregorianCalendar();
        Calendar c2 = new GregorianCalendar();
        c2.add(Calendar.DAY_OF_YEAR, 1);

        Cal isWeekend = calendarDAO.isAWeekEnd(new Date(c1.getTimeInMillis()));
        int hours;
        try {
            hours = isWeekend.getHours();
        } catch (Exception e){
            hours = -1;
        }

        for (AppUser user: users){

            String name = user.getLogin();
            AppUser test = appUserDAO.findByLogin(name);

            Date start = new Date(c1.getTimeInMillis());
            Date finish = new Date(c2.getTimeInMillis());


            ActionType actionType = actionTypeDAO.findById(4);

            List<Tracking> trackingListPerDay = trackingDAO.getTracksForOneDay(user.getId(), start, finish);
            int count = trackingListPerDay.size()-1;

            if (count == -1) {
                continue;
            }



            DayFinalizer dayFinalizer = new DayFinalizer(trackingListPerDay, hours);
            String action = trackingListPerDay.get(trackingListPerDay.size()-1).getAction().getType();


            if ( trackingListPerDay.isEmpty() || trackingListPerDay == null) {
                continue;
            }
            if (action.equals("gone")){
                if (dayFinalizer.isDinner()){
                    Tracking trackingStart = new Tracking();
                    Tracking trackingEnd = new Tracking();

                    Date lastTrack = trackingListPerDay.get(count).getDate();
                    Calendar calendarStart = Calendar.getInstance();
                    Calendar calendarEnd = Calendar.getInstance();

                    calendarStart.setTime(lastTrack);
                    calendarStart.add(Calendar.MINUTE, 1);

                    calendarEnd.setTime(lastTrack);
                    calendarEnd.add(Calendar.MINUTE, 26);

                    Date startDate = new Date(calendarStart.getTimeInMillis());
                    Date finishDate = new Date(calendarEnd.getTimeInMillis());

                    trackingStart.setUser(test);
                    trackingStart.setDate(startDate);
                    trackingStart.setAction(actionTypeDAO.findById(2));
                    trackingStart.setWorkingStatus(1);
                    trackingStart.setDay(startDate);
                    trackingDAO.persist(trackingStart);

                    trackingEnd.setUser(test);
                    trackingEnd.setDate(finishDate);
                    trackingEnd.setAction(actionTypeDAO.findById(3));
                    trackingEnd.setWorkingStatus(1);
                    trackingEnd.setDay(finishDate);
                    trackingDAO.persist(trackingEnd);
                }
                continue;
            }
            if (action.equals("comein")){
                Tracking tracking = new Tracking();
                tracking.setUser(test);
                tracking.setDate(trackingListPerDay.get(count).getDate());
                tracking.setAction(actionType);
                tracking.setWorkingStatus(1);
                tracking.setDay(trackingListPerDay.get(count).getDate());

                trackingDAO.persist(tracking);
            }
            if (action.equals("away")){
                Tracking trackingReturned = new Tracking();
                Tracking trackingGone = new Tracking();
                Date lastTrack = trackingListPerDay.get(count).getDate();
                Calendar calendarReturned = Calendar.getInstance();
                Calendar calendarGone = Calendar.getInstance();

                if (dayFinalizer.isDinner()){
                    calendarReturned.setTime(lastTrack);
                    calendarReturned.add(Calendar.MINUTE, 25);
                    calendarGone.setTime(lastTrack);
                    calendarGone.add(Calendar.SECOND, 10);
                } else {
                    calendarReturned.setTime(lastTrack);
                    calendarReturned.add(Calendar.SECOND, 10);
                    calendarGone.setTime(lastTrack);
                    calendarGone.add(Calendar.SECOND, 20);
                }

                Date startDate = new Date(calendarReturned.getTimeInMillis());
                Date finishDate = new Date(calendarGone.getTimeInMillis());

                trackingReturned.setUser(test);
                trackingReturned.setDate(startDate);
                trackingReturned.setAction(actionTypeDAO.findById(3));
                trackingReturned.setWorkingStatus(1);
                trackingReturned.setDay(startDate);
                trackingDAO.persist(trackingReturned);

                trackingGone.setUser(test);
                trackingGone.setDate(finishDate);
                trackingGone.setAction(actionType);
                trackingGone.setWorkingStatus(1);
                trackingGone.setDay(finishDate);
                trackingDAO.persist(trackingGone);
            }
            if (action.equals("returned")){
                Tracking tracking = new Tracking();
                Date lastTrack = trackingListPerDay.get(count).getDate();
                Calendar calendarGone = Calendar.getInstance();
                calendarGone.setTime(lastTrack);
                calendarGone.add(Calendar.SECOND, 10);
                Date finishDate = new Date(calendarGone.getTimeInMillis());

                tracking.setUser(test);
                tracking.setDate(finishDate);
                tracking.setAction(actionType);
                tracking.setWorkingStatus(1);

                trackingDAO.persist(tracking);
            }
        }
    }
}
