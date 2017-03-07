package com.lun.util;

import com.lun.model.AppUser;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class WorkingDays {
    private AppUser offerIn;
    private int year;
    private String month;
    private List<String> days = new LinkedList();

    public WorkingDays(AppUser offerIn, int year, String month){
        this.offerIn = offerIn;
        this.year = year;
        this.month = month;
    }

    public List<String> getDays() {

        Calendar c = new GregorianCalendar();
        int monthEnd = c.get(c.MONTH);

        Calendar c2 = new GregorianCalendar();
        c2.setTime(offerIn.getOfferin());
        int monthStart = c2.get(c2.MONTH);

        int currMonth = java.time.Month.valueOf(month).ordinal();

        if (monthEnd == currMonth) {
            int count = c.get(c.DAY_OF_MONTH);
            for (int i = 1; i <= count; i++){
                days.add(i+"."+monthEnd);
            }
        } else if (monthStart == currMonth) {
            int count = c2.get(c.DAY_OF_MONTH);
            int limit = c2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            for (int i = count; i <= limit; i++){
                days.add(i+"."+monthEnd);
            }
        } else {
            int limit = c2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            for (int i = 1; i <= limit; i++){
                days.add(i+"."+monthEnd);
            }
        }

        return days;
    }
}
