package com.lun.security;

/**
 * Created by lubich on 09.02.17.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.lun.dao.AppUserDAO;
import com.lun.model.AppUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AppUser appUser = appUserDAO.findByLogin(login);
        if (appUser != null) {
            Collection<GrantedAuthority> authorities = appUser.getUserRoles().stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRoleName()))
                    .collect(Collectors.toCollection(ArrayList::new));
            return new org.springframework.security.core.userdetails
                    .User(appUser.getLogin(), appUser.getPassword(), true, true, true, true, authorities);
        } else {
            throw new UsernameNotFoundException(login + " not found");
        }
    }
}