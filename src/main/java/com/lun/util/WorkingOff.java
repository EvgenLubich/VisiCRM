package com.lun.util;

import com.lun.model.AppUser;

import java.util.*;

/**
 * Created by lubich on 09.03.17.
 */
public class WorkingOff {

    private List<WorkingDay> days;
    private Map<Date, Integer> exceptionMap;
    private long currWorkingOffTime;
    private int workingOffTime;
    private int year;
    private int month;
    private int day;
    private int startDay = 1;

    public WorkingOff(List<WorkingDay> workingDay, AppUser user, Map<Date, Integer> exceptionMap){

        this.exceptionMap = exceptionMap;
        this.days = workingDay;
        this.currWorkingOffTime = setCurrWorkingOffTime();
        Calendar c = new GregorianCalendar();
        this.year = c.get(c.YEAR);
        this.month = c.get(c.MONTH);
        this.day = c.get(c.DAY_OF_MONTH);
        Calendar offerIn = new GregorianCalendar();
        offerIn.setTime(user.getOfferin());

        if (offerIn.get(offerIn.YEAR) == this.year && offerIn.get(offerIn.MONTH) == this.month){
            startDay = offerIn.get(offerIn.DAY_OF_MONTH);
        }

        this.workingOffTime = setWorkingOffTime(this.startDay, this.year, this.month, this.day, this.exceptionMap);
    }

    public WorkingOff(List<WorkingDay> workingDay, AppUser user, Map<Date, Integer> exceptionMap, int year, int month){

        this.exceptionMap = exceptionMap;
        this.days = workingDay;
        this.currWorkingOffTime = setCurrWorkingOffTime();
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        this.year = year;
        this.month = month;
        this.day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar offerIn = new GregorianCalendar();
        offerIn.setTime(user.getOfferin());

        if (offerIn.get(offerIn.YEAR) == this.year && offerIn.get(offerIn.MONTH) == this.month){
            startDay = offerIn.get(offerIn.DAY_OF_MONTH);
        }

        this.workingOffTime = setWorkingOffTime(this.startDay, this.year, this.month, this.day, this.exceptionMap);
    }

    public long setCurrWorkingOffTime() {
        long sum = 0;
        for (WorkingDay tracking : days) {
            if (tracking.isHospital() || tracking.isVacation() || tracking.isCommanding()){
                sum = sum + 28800000;
            } else if (tracking.getComein() == 0){
                continue;
            } else if (tracking.getAway() != 0) {
                sum = sum + tracking.workDay;
            } else if (tracking.getAway() == 0){
                if (tracking.getComein() == 0){

                } else {
                    Calendar c1 = new GregorianCalendar();
                    Date date = new Date(c1.getTimeInMillis());
                    sum = sum + date.getTime() - (tracking.getComein() + tracking.getEpsent());
                }
            }
        }
        return sum;
    }

    public String getCurrWorkingOffTime(){

        int s = (int)(currWorkingOffTime / 1000) % 60;
        int m = (int)(currWorkingOffTime / (1000 * 60)) % 60;
        int h = (int)(currWorkingOffTime / (3600000));

        return String.format("%d:%02d:%02d", h,m,s);
    }

    public String getDiffWorkingOffTime(){
        long diff = currWorkingOffTime - (workingOffTime * 3600000);

        if (diff>0) {
            int s = (int) (diff / 1000) % 60;
            int m = (int) (diff / (1000 * 60)) % 60;
            int h = (int) (diff / (3600000));

            return String.format("%d:%02d:%02d", h, m, s);
        } else {
            return "0";
        }
    }

    public int setWorkingOffTime(int startDay, int year, int month, int day, Map<Date, Integer> exceptionMap){
        int bDays;
        BiznesDays biznesDays = new BiznesDays(startDay, year, month, day, exceptionMap);
        bDays = biznesDays.calculateDuration();
        return bDays;
    }

    public int getWorkingOffTime(){
        return workingOffTime;
    }

}
