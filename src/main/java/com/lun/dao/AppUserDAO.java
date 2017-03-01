package com.lun.dao;

import com.lun.model.AppUser;

/**
 * Created by lubich on 09.02.17.
 */
public interface AppUserDAO {

    AppUser findByLogin(String login);
    void persist(AppUser appUser);
}
