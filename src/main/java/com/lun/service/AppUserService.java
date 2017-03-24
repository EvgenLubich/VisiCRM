package com.lun.service;

import com.lun.model.AppUser;
import com.lun.util.WorkingDay;
import com.lun.util.WorkingDays;
import com.lun.util.WorkingMonthes;
import com.lun.util.WorkingYear;

import java.util.List;
import java.util.Map;

/**
 * Created by lubich on 10.02.17.
 */
public interface AppUserService {

    void registerUser(String login, String password, String role);
    void addTime(String userName, int action);
    int getUserStatus(String userName);
    WorkingYear getYears(String userName);
    WorkingMonthes getMonthes(String userName, int year);
    WorkingDays getDays(String userName, int year, String month);
    Map<String, Integer> getAllUsers();
}