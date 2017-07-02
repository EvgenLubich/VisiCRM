package com.lun.dao;

import com.lun.model.AppUser;

import java.util.List;

/**
 * Created by lubich on 09.02.17.
 */
public interface AppUserDAO {

    AppUser findByLogin(String login);
    void persist(AppUser appUser);
    AppUser findUserOfferDate(String login);
    List<AppUser> findAllUsers();
    void deleteUser(AppUser appUser);
    void updateUser(String login, String firstName, String lastName, String password, String role);
}
