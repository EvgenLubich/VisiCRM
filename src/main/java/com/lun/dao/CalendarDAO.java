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
    List<Cal> getCalendar();
    Cal isAWeekEnd(Date start);
    void persist(Cal cal);
    void deleteDate(Cal cal);
    Cal findByDate(Date date);
}
