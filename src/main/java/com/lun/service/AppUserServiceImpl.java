package com.lun.service;

import com.lun.dao.ActionTypeDAO;
import com.lun.dao.AppUserDAO;
import com.lun.dao.TrackingDAO;
import com.lun.model.ActionType;
import com.lun.model.AppUser;
import com.lun.model.Tracking;
import com.lun.model.UserRole;
import com.lun.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lun.dao.UserRoleDAO;

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


    @Override
    @Transactional
    public void registerUser(String login, String password, String role) {

        Date d = new Date();
        UserRole userRole = userRoleDAO.find(role);
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(userRole);
        AppUser appUser = new AppUser(login, APPUtil.encodePassword(password), roleSet, d);
        appUserDAO.persist(appUser);
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
        trackingDAO.persist(tracking);
    }

    @Override
    @Transactional
    public int getUserStatus(String userName){
        //notUsed
        Date d = new Date();
        //
        Calendar c = new GregorianCalendar();
        int year = c.get(c.YEAR);
        int month = c.get(c.MONTH);
        int day = c.get(c.DAY_OF_MONTH);

        Calendar c2 = new GregorianCalendar(year, month, day);
        Date start = new Date(c2.getTimeInMillis());
        c2.add(Calendar.DAY_OF_YEAR, 1);
        Date finish = new Date(c2.getTimeInMillis());

        AppUser user = appUserDAO.findByLogin(userName);
        try {
            Tracking status = trackingDAO.getStatusByUserId(user.getId(), d, start, finish);
            ActionType actionId = status.getAction();
            int statusId = actionId.getId();
            return statusId;
        } catch (Exception e){
            return 5;
        }
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
