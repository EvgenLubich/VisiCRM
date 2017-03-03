package com.lun.service;

import com.lun.util.WorkingYear;

/**
 * Created by lubich on 10.02.17.
 */
public interface AppUserService {

    void registerUser(String login, String password, String role);
    void addTime(String userName, int action);
    int getUserStatus(String userName);
    WorkingYear getYears(String userName);
}