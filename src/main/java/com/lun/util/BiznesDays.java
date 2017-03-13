package com.lun.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lubich on 09.03.17.
 */
public class BiznesDays {

    Calendar startCal;
    Calendar endCal = new GregorianCalendar();
    //int maxDays = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);

    public BiznesDays(int startDay, int year, int month, int day){
        this.startCal = new GregorianCalendar(year, month, startDay);
        this.endCal = new GregorianCalendar(year, month, endCal.get(endCal.DAY_OF_MONTH));
    }

    public int calculateDuration() {
        int workDays = 0;

        do {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                workDays++;
            }
        }
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        return workDays*8;
    }
}
