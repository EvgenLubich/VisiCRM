package com.lun.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by lubich on 14.04.17.
 */
public class UserReport implements Comparable<UserReport> {
    private String userLogin;
    private TreeMap<Integer, Float> timeForDay;
    private int overWorking;
    private int workingHoursInMonth;
    private int daysOffForMonth;
    private int daysOffForYear;
    private Date dateDate;
    private int weekendsCount;
    private int weekendsCurrCount;
    private String date;
    private long workDay;
    private boolean isWeekend;
    private boolean isHospital;
    private boolean isVacation;
    private boolean isCommanding;
    private List<WorkingDay> workingDays;
    private WorkingOff workingOff;

    public UserReport(String userLogin, Date date, int weekendsCount, int weekendsCurrCount) {
        this.userLogin = userLogin;
        this.dateDate = date;
        this.weekendsCount = weekendsCount;
        this.weekendsCurrCount = weekendsCurrCount;
    }

    public WorkingOff getWorkingOff() {
        return workingOff;
    }

    public void setWorkingOff(WorkingOff workingOff) {
        this.workingOff = workingOff;
    }

    public List<WorkingDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WorkingDay> workingDays) {
        this.workingDays = workingDays;
    }

    public String getWorkDayString(){
        Date date = new Date();
        date.setTime(this.workDay);
        DateFormat df = new SimpleDateFormat("HH:mm");
        String reportDate = df.format(date);
        return reportDate;
    }

    public long getWorkDay() {
        return workDay;
    }

    public void setWorkDay(long workDay) {
        this.workDay = workDay;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
    }

    public boolean isHospital() {
        return isHospital;
    }

    public void setHospital(boolean hospital) {
        isHospital = hospital;
    }

    public boolean isVacation() {
        return isVacation;
    }

    public void setVacation(boolean vacation) {
        isVacation = vacation;
    }

    public boolean isCommanding() {
        return isCommanding;
    }

    public void setCommanding(boolean commanding) {
        isCommanding = commanding;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeekendsCurrCount() {
        return weekendsCurrCount;
    }

    public void setWeekendsCurrCount(int weekendsCurrCount) {
        this.weekendsCurrCount = weekendsCurrCount;
    }

    public int getWeekendsCount() {
        return weekendsCount;
    }

    public void setWeekendsCount(int weekendsCount) {
        this.weekendsCount = weekendsCount;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public TreeMap<Integer, Float> getTimeForDay() {
        return timeForDay;
    }

    public void setTimeForDay(TreeMap<Integer, Float> timeForDay) {
        this.timeForDay = timeForDay;
    }

    public int getOverWorking() {
        return overWorking;
    }

    public void setOverWorking(int overWorking) {
        this.overWorking = overWorking;
    }

    public int getWorkingHoursInMonth() {
        return workingHoursInMonth;
    }

    public void setWorkingHoursInMonth(int workingHoursInMonth) {
        this.workingHoursInMonth = workingHoursInMonth;
    }

    public int getDaysOffForMonth() {
        return daysOffForMonth;
    }

    public void setDaysOffForMonth(int daysOffForMonth) {
        this.daysOffForMonth = daysOffForMonth;
    }

    public int getDaysOffForYear() {
        return daysOffForYear;
    }

    public void setDaysOffForYear(int daysOffForYear) {
        this.daysOffForYear = daysOffForYear;
    }

    public Date getDate() {
        return dateDate;
    }

    public void setDate(Date date) {
        this.dateDate = date;
    }

    @Override
    public int compareTo(UserReport o) {
        String comp = o.getUserLogin();
        int result = this.getUserLogin().compareTo(comp);
        return result;
    }
}
