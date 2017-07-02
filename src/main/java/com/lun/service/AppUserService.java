package com.lun.service;

import com.lun.model.AppUser;
import com.lun.model.Tracking;
import com.lun.util.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lubich on 10.02.17.
 */
public interface AppUserService {

    void registerUser(String login, String firstName, String lastName, String password, String role);
    void addTime(String userName, int action);
    UserForAdmin getUserStatus(String userName, String date);
    WorkingYear getYears(String userName);
    WorkingMonthes getMonthes(String userName, int year);
    WorkingDays getDays(String userName, int year, String month);
    Map<String, UserForAdmin> getAllUsers(String date);
    void deleteUser(String login);
    void addDate(Date date, int time);
    void deleteDate(Date date);
    String getStringDate();
    Boolean isDayExist(Date date);
    AppUser getUser(String login);
    void updateUser(String login, String firstName, String lastName, String password, String role);
}