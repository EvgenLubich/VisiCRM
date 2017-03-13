package com.lun.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lubich on 09.03.17.
 */
public class BiznesDays {

    private Date startDate;
    private Date endDate;
    Calendar startCal = Calendar.getInstance();
    Calendar endCal = Calendar.getInstance();


    public BiznesDays(int year, int month, int day){
        this.startCal.set(year, month, day);
        this.startCal.set(year, month, 1);
    }


    public int calculateDuration() {

        int workDays = 0;

        do {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
            {
                workDays++;
            }
        }
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        return workDays;
    }
}
