package com.lun.dao;

import com.lun.model.Cal;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lubich on 14.03.17.
 */
public interface CalendarDAO {

    List<Cal> getOurs(Date start, Date finish);
}
