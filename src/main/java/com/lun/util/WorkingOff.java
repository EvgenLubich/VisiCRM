package com.lun.util;

import com.lun.dao.AppUserDAO;
import com.lun.model.AppUser;
import com.lun.model.Tracking;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by lubich on 09.03.17.
 */
public class WorkingOff {

    @Autowired
    private AppUserDAO appUserDAO;

    private List<WorkingDay> days;
    private long currWorkingOffTime;
    private int workingOffTime;
    private String name;
    private int year;
    private int month;
    private int day;
    private int startDay = 1;

    public WorkingOff(List<WorkingDay> workingDay, AppUser user){
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

        this.workingOffTime = setWorkingOffTime(this.startDay, this.year, this.month, this.day);
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

    public String getCurrWorkingOffTime(){

        int s = (int)(currWorkingOffTime / 1000) % 60;
        int m = (int)(currWorkingOffTime / (1000 * 60)) % 60;
        int h = (int)(currWorkingOffTime / (3600000));

        return String.format("%d:%02d:%02d", h,m,s);
    }

    public int setWorkingOffTime(int startDay, int year, int month, int day){
        int bDays;
        BiznesDays biznesDays = new BiznesDays(startDay, year, month, day);
        bDays = biznesDays.calculateDuration();
        return bDays;
    }

    public int getWorkingOffTime(){
        return workingOffTime;
    }



}
