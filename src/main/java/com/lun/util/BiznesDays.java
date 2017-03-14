package com.lun.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by lubich on 09.03.17.
 */
public class BiznesDays {

    private Calendar startCal;
    private Calendar endCal;
    private Map<Date, Integer> exceptionMap;
    //int maxDays = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);

    public BiznesDays(int startDay, int year, int month, int day, Map<Date, Integer> exceptionMap){
        this.startCal = new GregorianCalendar(year, month, startDay);
        this.endCal = new GregorianCalendar(year, month, day);
        this.exceptionMap = exceptionMap;
    }

    public int calculateDuration() {
        int workDays = 0;

        do {
            Date d = startCal.getTime();
            boolean b = exceptionMap.containsKey(d);
            if (b){
                int val = exceptionMap.get(d);
                workDays += val;
            }
            else if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                workDays += 8;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        return workDays;
    }
}
