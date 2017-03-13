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
    private int workingOffTime;
    private String name;
    private int year;
    private int month;
    private int day;

    public WorkingOff(List<WorkingDay> workingDay, String name){
        this.days = workingDay;
        this.currWorkingOffTime = setCurrWorkingOffTime();
        Calendar c = new GregorianCalendar();
        this.name = name;
        this.year = c.get(c.YEAR);
        this.month = c.get(c.MONTH);
        this.day = c.get(c.DAY_OF_MONTH);
        this.workingOffTime = setWorkingOffTime(this.year, this.month, this.day);
    }

    public WorkingOff(List<WorkingDay> workingDay, String name, int year, int month){
        this.days = workingDay;
        this.currWorkingOffTime = setCurrWorkingOffTime();
        this.name = name;
        this.year = year;
        this.month = month;
        //TODO
       /* дописать для разных годов и месяцев */
    }

    public long setCurrWorkingOffTime() {
        long sum = 0;
        for (WorkingDay tracking : days) {
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

    public int setWorkingOffTime(int year, int month, int day){
        int bDays;
        BiznesDays biznesDays = new BiznesDays(year, month, day);
        bDays = biznesDays.calculateDuration();
        return bDays;
    }

    public int getWorkingOffTime(){
        return workingOffTime;
    }



}
