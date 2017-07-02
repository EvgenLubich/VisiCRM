package com.lun.util;

import com.lun.model.AppUser;

import java.time.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class WorkingMonthes {
    private AppUser offerIn;
    private int year;
    private List<String> monthes = new LinkedList();

    public WorkingMonthes(AppUser offerIn, int year){
        this.offerIn = offerIn;
        this.year = year;
    }

    public List<String> getMonthes() {

        Calendar c = new GregorianCalendar();
        int yearEnd = c.get(c.YEAR);

        Calendar c2 = new GregorianCalendar();
        c2.setTime(offerIn.getOfferin());
        int yearStart = c2.get(c2.YEAR);

        if (yearEnd == year) {
            int count = (c.get(c.MONTH))-1;
            for (int i = 1; i <= count+1; i++){
                monthes.add(java.time.Month.of(i).toString());
            }
        } else if (yearStart == year) {
            int count = c2.get(c.MONTH);
            for (int i = count+1; i <= 12; i++){
                monthes.add(java.time.Month.of(i).toString());
            }
        } else {
            for (int i = 1; i <= 12; i++){
                monthes.add(java.time.Month.of(i).toString());
            }
        }

        return monthes;
    }
}
