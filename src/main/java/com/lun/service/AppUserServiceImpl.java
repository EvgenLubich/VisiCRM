package com.lun.service;

import com.lun.dao.*;
import com.lun.model.*;
import com.lun.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lubich on 13.02.17.
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private TrackingDAO trackingDAO;
    @Autowired
    private ActionTypeDAO actionTypeDAO;
    @Autowired
    private CalendarDAO calendarDAO;


    @Override
    @Transactional
    public void registerUser(String login, String firstName, String lastName, String password, String role) {

        Date d = new Date();
        UserRole userRole = userRoleDAO.find(role);
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(userRole);
        AppUser appUser = new AppUser(login, firstName, lastName, APPUtil.encodePassword(password), roleSet, d);
        appUserDAO.persist(appUser);
    }

    @Override
    @Transactional
    public void updateUser(String login, String firstName, String lastName, String password, String role){
        AppUser user = getUser(login);
        user.setName(firstName);
        user.setSurname(lastName);
        user.setLogin(login);
        UserRole userRole = userRoleDAO.find(role);
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(userRole);
        user.setUserRoles(roleSet);
        if (!password.equals("")) {
            user.setPassword( APPUtil.encodePassword(password));
        }
        appUserDAO.persist(user);
       // appUserDAO.updateUser(login, firstName, lastName, password, role);
    }

    @Override
    @Transactional
    public AppUser getUser(String login){
        AppUser user = appUserDAO.findByLogin(login);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(String login){
        appUserDAO.deleteUser(appUserDAO.findByLogin(login));
    }

    @Override
    @Transactional
    public void deleteDate(Date date){
        calendarDAO.deleteDate(calendarDAO.findByDate(date));
    }

    @Override
    @Transactional
    public void addDate(Date date, int time){
        Cal cal = new Cal();
        cal.setDay(date);
        cal.setHours(time);

        calendarDAO.persist(cal);
    }

    @Override
    public Boolean isDayExist(Date date){
        Cal cal = calendarDAO.findByDate(date);
        if (cal == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional
    public void addTime(String userName, int action) {
        Date d = new Date();

        AppUser user = appUserDAO.findByLogin(userName);
        Tracking tracking = new Tracking();
        ActionType actionType = actionTypeDAO.findById(action);
        tracking.setUser(user);
        tracking.setDate(d);
        tracking.setAction(actionType);
        tracking.setWorkingStatus(1);
        tracking.setDay(d);

        trackingDAO.persist(tracking);
    }

    @Override
    public Map<String, UserForAdmin> getAllUsers(String date){
        Map<String, UserForAdmin> usersStatusMap = new TreeMap<>();

        List<AppUser> users = appUserDAO.findAllUsers();
        for (AppUser user: users){
            UserForAdmin userForAdmin = getUserStatus(user.getLogin(), date);
            usersStatusMap.put(userForAdmin.getLastName() + " " + userForAdmin.getFirstName(), userForAdmin);
        }
        return usersStatusMap;
    }

    @Override
    public String getStringDate(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }

    @Override
    @Transactional
    public UserForAdmin getUserStatus(String userName, String date){


        UserForAdmin userForAdmin = new UserForAdmin();
        int statusId;
        ActionType actionId;
        Date d = new Date();

        int year;
        int month;
        int day;

        if (date.equals("")) {
            Calendar c = new GregorianCalendar();
            year = c.get(c.YEAR);
            month = c.get(c.MONTH);
            day = c.get(c.DAY_OF_MONTH);
        } else {
            String [] stringDate = date.split("-");
            year = Integer.parseInt(stringDate[0]);
            month = Integer.parseInt(stringDate[1])-1;
            day = Integer.parseInt(stringDate[2]);
        }

        Calendar c2 = new GregorianCalendar(year, month, day);
        Date start = new Date(c2.getTimeInMillis());
        c2.add(Calendar.DAY_OF_YEAR, 1);
        Date finish = new Date(c2.getTimeInMillis());

        AppUser user = appUserDAO.findByLogin(userName);
        try {
            Tracking tracking = trackingDAO.getStatusByUserId(user.getId(), d, start, finish);
            actionId = tracking.getAction();
            statusId = actionId.getId();
            userForAdmin.setStatus(statusId);
        } catch (Exception e){
            userForAdmin.setStatus(5);
        }
        List<Tracking> tracking = trackingDAO.getTracksForOneDay(user.getId(), start, finish);
        userForAdmin.setTrackings(tracking);
        userForAdmin.setFirstName(user.getName());
        userForAdmin.setLastName(user.getSurname());
        userForAdmin.setLogin(user.getLogin());

        return userForAdmin;
    }

    @Override
    public WorkingYear getYears(String userName) {

        AppUser year = appUserDAO.findUserOfferDate(userName);
        WorkingYear workingYear = new WorkingYear(year);

        return workingYear;
    }

    @Override
    public WorkingMonthes getMonthes(String userName, int year){

        AppUser offerDate = appUserDAO.findUserOfferDate(userName);
        WorkingMonthes monthes = new WorkingMonthes(offerDate, year);

        return monthes;
    }

    @Override
    public WorkingDays getDays(String userName, int year, String month) {
        AppUser offerDate = appUserDAO.findUserOfferDate(userName);
        WorkingDays workingDays = new WorkingDays(offerDate, year, month);
        return null;
    }
}
