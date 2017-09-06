package com.lun.util;

import com.lun.model.Tracking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DayFinalizer {
    List<Tracking> dayTracks;
    private long epsent;
    private long workDay;
    private long comein;
    private long away;
    private int hours;
    //private boolean isDinner;

    public DayFinalizer(List<Tracking> trackingListPerDay, int hours) {
        this.dayTracks = trackingListPerDay;
        this.comein = getComein();
        this.away = getAway();
        this.epsent = getEpsent();
        this.workDay = getWorkDay();
        this.hours = hours;
    }

    public long getEpsent() {
        long sum = 0;

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
            for (Tracking tracking : dayTracks) {
                if (tracking.getAction().getType().equals("comein")) {
                    long comein = tracking.getDate().getTime();
                    return comein;
                }
            }
        return 0;
    }

    public long getAway() {
        for (Tracking tracking : dayTracks) {
            if (tracking.getAction().getType().equals("gone")) {
                long gone = tracking.getDate().getTime();
                return gone;
            }
        }
        return 0;
    }

    public boolean isDinner(){
        String currDay = "err";
        currDay = new SimpleDateFormat("EEEE").format(new Date());
        if (hours == 0) {
            return false;
        } else if ((currDay.equals("суббота") || currDay.equals("воскресенье")) && hours == -1){
            return false;
        } else {
            if (epsent == 0 && workDay >= 18000000) {
                return true;
            } else if (workDay < 33900000 && workDay > 30300000 && epsent < 1500000){
                return true;
            } else if (workDay >= 33900000){
                return true;
            } else if (epsent != 0 && workDay >= 18000000){
                return false;
            } else {
                return false;
            }
        }
    }
}
