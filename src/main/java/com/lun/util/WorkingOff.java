package com.lun.util;

import com.lun.model.Tracking;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by lubich on 09.03.17.
 */
public class WorkingOff {
    private List<WorkingDay> days;
    private long currWorkingOffTime;
    private String name;
    private int year;
    private int month;

    public WorkingOff(List<WorkingDay> workingDay, String name){
        this.days = workingDay;
        this.currWorkingOffTime = setCurrWorkingOffTime();
        Calendar c = new GregorianCalendar();
        this.name = name;
        this.year = c.get(c.YEAR);
        this.month = c.get(c.MONTH);
    }

    public WorkingOff(List<WorkingDay> workingDay, String name, int year, int month){
        this.days = workingDay;
        this.currWorkingOffTime = setCurrWorkingOffTime();
        this.name = name;
        this.year = year;
        this.month = month;
    }

    public long setCurrWorkingOffTime() {
        long sum = 0;
        for (WorkingDay tracking : days) {
            long e = tracking.getWorkDay();
            long i = tracking.workDay;
            if (tracking.getComein() == 0){
                continue;
            }
            sum = sum + tracking.workDay;
        }
        return sum;
    }
    public long getCurrWorkingOffTime(){
        return currWorkingOffTime;
    }
}
