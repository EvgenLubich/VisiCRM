package com.lun.service;

import com.lun.model.Tracking;
import com.lun.util.Month;
import com.lun.util.WorkingDay;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by lubich on 16.02.17.
 */
public interface TrackingService {
    List<Tracking> getTracksComein(String userName);
    List<WorkingDay> getWorkingDay(String userName);
    List<WorkingDay> getWorkingDayForSomeMonth(String userName, int year, int month);
//    List<Month> getMonths(String userName);
}
