package com.lun.util;

import com.lun.model.Tracking;

import java.text.SimpleDateFormat;
import java.util.*;

public class WorkingDay {

    private Map<Date, List<Tracking>> dayTracksMap;

    public String hours;
    private String date;
    public String day;
    private long epsent;
    public long workDay;
    private long comein;
    private long away;

    //public boolean isWEnd;

    public WorkingDay(Map<Date, List<Tracking>> dayTracks, int hours) {
        this.dayTracksMap = dayTracks;
        this.hours = getDay();
        this.day = getDay();
        this.date = getDate();
        this.comein = getComein();
        this.away = getAway();
        this.epsent = getEpsent();
        this.workDay = getWorkDay();

//        this.isWEnd = isWeekend();
    }

    public String getDay() {
        String currDay = "err";
        for (Date dayKey : dayTracksMap.keySet()) {
            currDay = new SimpleDateFormat("EEEE").format(dayKey);
        }
        return currDay;
    }

    public String getDate() {
        String currDate = "err";
        for (Date dayKey : dayTracksMap.keySet()) {
            currDate = new SimpleDateFormat("dd").format(dayKey);
        }
        return currDate;
    }

    public long getEpsent() {
        long sum = 0;

        for (List<Tracking> dayTracks : dayTracksMap.values()) {
            for (int i = 0; i < dayTracks.size(); i++) {
                String status = dayTracks.get(i).getAction().getType();

                if (status.equals("away")) {
                    Date away1 = dayTracks.get(i).getDate();
                    if (((i + 1) < dayTracks.size()) && dayTracks.get(i + 1).getAction().getType().equals("returned")) {
                        Date returned = dayTracks.get(i + 1).getDate();
                        long dif = returned.getTime() - away1.getTime();
                        sum = sum + dif;
                        i++;
                    }
                }
            }
        }
        return sum;
    }

    public long getWorkDay() {
        long sum;
        if (getAway() == 0){
            Calendar c1 = new GregorianCalendar();
            Date date = new Date(c1.getTimeInMillis());
            sum = date.getTime() - (comein + epsent);
            return sum;
        }else {
            sum = away - (comein + epsent);
            return sum;
        }
    }

    public long getComein() {
        for (List<Tracking> dayTracks : dayTracksMap.values()) {
            for (Tracking tracking : dayTracks) {
                if (tracking.getAction().getType().equals("comein")) {

                    long comein = tracking.getDate().getTime();
                    return comein;
                }
            }
        }
        return 0;
    }

    public long getAway() {
        for (List<Tracking> dayTracks : dayTracksMap.values()) {
            for (Tracking tracking : dayTracks) {
                if (tracking.getAction().getType().equals("gone")) {
                    long gone = tracking.getDate().getTime();
                    return gone;
                }
            }
        }
        return 0;
    }

//    public boolean isWeekend() {
//        if (hours == 0 || hours == -1) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Override
    public String toString() {
        return "WorkingDay{" +
                ", date='" + date + '\'' +
                ", hours='" + hours + '\'' +

                ", day='" + day + '\'' +
                ", epsent=" + epsent +
                ", workDay=" + workDay +
                ", comein=" + comein +
                ", away=" + away +
                '}'+"<br>";
    }
}























//package com.lun.util;
//
//import com.lun.model.Tracking;
//
//import java.util.*;
//
//public class WorkingDay {
//
//    @Override
//    public String toString() {
//        return "WorkingDay{" +
//                "dayTracks=" + dayTracks +
//                ", epsent='" + epsent + '\'' +
//                ", workDay='" + workDay + '\'' +
//                ", comein='" + comein + '\'' +
//                ", away='" + away + '\'' +
//                '}';
//    }
//
//    private List<Tracking> dayTracks;
//
//    private String date;
//    private String day;
//    private long epsent;
//    private long workDay;
//    private long comein;
//    private long away;
//
//    public WorkingDay(List<Tracking> dayTracks) {
//        this.dayTracks = dayTracks;
//        this.day = getDay();
//        this.date = getDate();
//        this.comein = getComein();
//        this.away = getAway();
//        this.epsent = getEpsent();
//        this.workDay = getWorkDay();
//    }
//
//    public String getDay() {
//
//        return day;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public long getEpsent() {
//        long sum = 0;
//
//        for (int i = 0; i < dayTracks.size(); i++) {
//            String status = dayTracks.get(i).getAction().getType();
//
//            if (status.equals("away")) {
//                Date away1 = dayTracks.get(i).getDate();
//                if (((i + 1) < dayTracks.size()) && dayTracks.get(i + 1).getAction().getType().equals("returned")) {
//                    Date returned = dayTracks.get(i + 1).getDate();
//                    long dif = returned.getTime() - away1.getTime();
//                    sum = sum + dif;
//                }
//            }
//        }
//
//        return sum;
//    }
//
//    public long getWorkDay() {
//        long sum = 0;
//
//        sum = getAway() - getComein() - getEpsent();
//
//        return sum;
//
//    }
//
//    public long getComein() {
//        for (Tracking tracking : dayTracks) {
//            if (tracking.getAction().getType().equals("comein")) {
//                long comein = tracking.getDate().getTime();
//                return comein;
//            }
//        }
//        return 0;
//    }
//
//    public long getAway() {
//        for (Tracking tracking : dayTracks) {
//            if (tracking.getAction().getType().equals("gone")) {
//                long gone = tracking.getDate().getTime();
//                return gone;
//            }
//        }
//        return 0;
//    }
//
//}
