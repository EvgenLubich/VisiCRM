//package com.lun.util;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * Created by lubich on 09.03.17.
// */
//public class BiznesDays {
//
//    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//    String dateInString = "01-07-2016";
//    Date startDate = sdf.parse(dateInString);
//    String dateInString2 = "31-07-2016";
//    Date endDate = sdf.parse(dateInString2);
//
//
//    public int calculateDuration(Date startDate, Date endDate)
//    {
//        Calendar startCal = Calendar.getInstance();
//        startCal.setTime(startDate);
//
//        Calendar endCal = Calendar.getInstance();
//        endCal.setTime(endDate);
//
//        int workDays = 0;
//
//        if (startCal.getTimeInMillis() > endCal.getTimeInMillis())
//        {
//            startCal.setTime(endDate);
//            endCal.setTime(startDate);
//        }
//
//        do
//        {
//            startCal.add(Calendar.DAY_OF_MONTH, 1);
//            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
//            {
//                workDays++;
//            }
//        }
//        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
//
//        return workDays;
//    }
//}
