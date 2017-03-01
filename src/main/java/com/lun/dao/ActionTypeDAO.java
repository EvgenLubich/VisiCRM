package com.lun.dao;

import com.lun.model.ActionType;

import java.util.AbstractList;

/**
 * Created by Evgen on 15.02.2017.
 */
public interface ActionTypeDAO {
    ActionType findById(int id);
}
