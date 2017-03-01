package com.lun.dao;

import com.lun.model.UserRole;

/**
 * Created by lubich on 13.02.17.
 */
public interface UserRoleDAO {
    UserRole find(String roleName);
}
