package com.lun.service;

import com.lun.model.Tracking;
import com.lun.util.Month;
import com.lun.util.WorkingDay;
import com.lun.util.WorkingOff;

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
    WorkingOff getWorkingOff(List<WorkingDay> workingDay, String userName);
    WorkingOff getWorkingOffHistory(List<WorkingDay> workingDay, String userName, int year, int month);
   /* void countStudent();*/
//    List<Month> getMonths(String userName);
}
