package com.lun.controller;

/**
 * Created by lubich on 10.02.17.
 */
import com.lun.model.AppUser;
import com.lun.service.ReportService;
import com.lun.service.TrackingService;
import com.lun.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.lun.service.AppUserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class RootController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private TrackingService trackingService;
    @Autowired
    private ReportService reportService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model, Principal principal) {

        String ip = request.getRemoteAddr();
        String [] arr = ip.split("\\.");
        String visiIp = arr[0] + "." + arr[1] + "." +arr[2];
        if (visiIp.equals("193.200.248") || (principal.getName().equals("wweeww")) || (principal.getName().equals("lubich-un"))){
            model.addAttribute("currentLogin", principal.getName());
            int status = appUserService.getUserStatus(principal.getName(), "").getStatus();
            model.addAttribute("status", status);
            WorkingYear workingYear = appUserService.getYears(principal.getName());
            model.addAttribute("workingYear", workingYear.getYears());
            List<WorkingDay> workingDay = trackingService.getWorkingDay(principal.getName());
            model.addAttribute("workingDay", workingDay);

            //WorkingOff workingOff = new WorkingOff(workingDay, principal.getName());
            WorkingOff workingOff = trackingService.getWorkingOff(workingDay, principal.getName());
            model.addAttribute("currWorkingOff", workingOff.getCurrWorkingOffTime());
            model.addAttribute("workingOff", workingOff.getWorkingOffTime());

            model.addAttribute("if", "if");
            model.addAttribute("ip", ip);

            return "index";
        } else {
            return "redirect:/logout";
        }
    }


//    @RequestMapping("/")
//    public String index(HttpServletRequest request, Model model, Principal principal) {
//
//
//            model.addAttribute("currentLogin", principal.getName());
//            int status = appUserService.getUserStatus(principal.getName(), "").getStatus();
//            model.addAttribute("status", status);
//            WorkingYear workingYear = appUserService.getYears(principal.getName());
//            model.addAttribute("workingYear", workingYear.getYears());
//            List<WorkingDay> workingDay = trackingService.getWorkingDay(principal.getName());
//            model.addAttribute("workingDay", workingDay);
//
//            //WorkingOff workingOff = new WorkingOff(workingDay, principal.getName());
//            WorkingOff workingOff = trackingService.getWorkingOff(workingDay, principal.getName());
//            model.addAttribute("currWorkingOff", workingOff.getCurrWorkingOffTime());
//            model.addAttribute("workingOff", workingOff.getWorkingOffTime());
//
//            model.addAttribute("if", "if");
//            model.addAttribute("ip", "ip");
//
//            return "index";
//
//    }


    @RequestMapping(value = "/statistic-{year}")
    public String showStatForYear(Model model, @PathVariable int year, Principal principal) {
        WorkingMonthes workingMonthes = appUserService.getMonthes(principal.getName(), year);
        model.addAttribute("workingMonthes", workingMonthes.getMonthes());
        model.addAttribute("year", year);
        return "year";
    }

    @RequestMapping(value = "/statistic_{year}_{login}")
    public String showAdminStatForYear(Model model, @PathVariable int year, @PathVariable String login) {
        WorkingMonthes workingMonthes = appUserService.getMonthes(login, year);
        model.addAttribute("workingMonthes", workingMonthes.getMonthes());
        model.addAttribute("year", year);
        model.addAttribute("login", login);
        return "yearForAdmin";
    }

    @RequestMapping(value = "/statistic-{year}/{month}")
    public String showStatForMonth(Model model, @PathVariable int year, @PathVariable String month, Principal principal) {

        model.addAttribute("currentLogin", principal.getName());

        WorkingYear workingYear = appUserService.getYears(principal.getName());
        model.addAttribute("workingYear", workingYear.getYears());

        int currMonth = java.time.Month.valueOf(month).ordinal();
        List<WorkingDay> workingDay = trackingService.getWorkingDayForSomeMonth(principal.getName(), year, currMonth);
        model.addAttribute("workingDay", workingDay);

        //WorkingOff workingOff = new WorkingOffHistory(workingDay, principal.getName(), year, currMonth);
        WorkingOff workingOff = trackingService.getWorkingOffHistory(workingDay, principal.getName(), year, currMonth);
        model.addAttribute("currWorkingOff", workingOff.getCurrWorkingOffTime());
        model.addAttribute("workingOff", workingOff.getWorkingOffTime());

        return "history";
    }

//    @RequestMapping(value = "/admin-another-date-{userLogin}",  method = RequestMethod.POST)
//    public String routToUserStatAnotherDate(
//            @RequestParam String year,
//            @RequestParam String month,
//            @PathVariable String userLogin){
//        return "redirect:/admin-statfor-" + userLogin + "-" + year + "-" + month;
//    }


    @RequestMapping(value = "/admin_statfor_{login}")
    public String showUserStatCurrentDate(
            Model model,
            @PathVariable String login
    ){
        model.addAttribute("currentLogin", login);
        int status = appUserService.getUserStatus(login, "").getStatus();
        model.addAttribute("status", status);
        WorkingYear workingYear = appUserService.getYears(login);
        model.addAttribute("workingYear", workingYear.getYears());
        List<WorkingDay> workingDay = trackingService.getWorkingDay(login);
        model.addAttribute("workingDay", workingDay);

        //WorkingOff workingOff = new WorkingOff(workingDay, principal.getName());
        WorkingOff workingOff = trackingService.getWorkingOff(workingDay, login);
        model.addAttribute("currWorkingOff", workingOff.getCurrWorkingOffTime());
        model.addAttribute("workingOff", workingOff.getWorkingOffTime());

        return "adminUserHistory";
    }

    @RequestMapping(value = "/admin_statfor_{login}/{year}_{month}")
    public String showUserStatAnotherDate(
            Model model,
            @PathVariable String login,
            @PathVariable String year,
            @PathVariable String month
    ){
        model.addAttribute("currentLogin", login);

        WorkingYear workingYear = appUserService.getYears(login);
        model.addAttribute("workingYear", workingYear.getYears());

        int currMonth = java.time.Month.valueOf(month).ordinal();
        int currYear = Integer.parseInt(year);
        List<WorkingDay> workingDay = trackingService.getWorkingDayForSomeMonth(login, currYear, currMonth);
        model.addAttribute("workingDay", workingDay);

        //WorkingOff workingOff = new WorkingOffHistory(workingDay, principal.getName(), year, currMonth);
        WorkingOff workingOff = trackingService.getWorkingOffHistory(workingDay, login, currYear, currMonth);
        model.addAttribute("currWorkingOff", workingOff.getCurrWorkingOffTime());
        model.addAttribute("workingOff", workingOff.getWorkingOffTime());

        return "adminUserHistory";
    }


    @RequestMapping("/delete-user-{user}")
    public String deleteUser(Model model, @PathVariable String user){
        appUserService.deleteUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/edit-user-{user}")
    public String editUser(Model model, @PathVariable String user){
        AppUser appUser = appUserService.getUser(user);
        model.addAttribute("user",appUser);
        return "editUser";
    }

    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String editUserForm(
            @RequestParam String login,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String password,
            @RequestParam String role){
        try {
            appUserService.updateUser(login, firstName, lastName, password, role);
            return "redirect:/admin";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @RequestMapping("/adduser")
    public String adduser(){
        return "adduser";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduserForm(
            @RequestParam String login,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String password,
            @RequestParam String role){
        try {
            appUserService.registerUser(login, firstName, lastName, password, role);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/adduser";
        }
    }

    @RequestMapping(value = "/add_hospital_{user}", method = RequestMethod.POST)
    public String addHospital(HttpServletRequest request,
                              Model model,
                              @PathVariable String user,
                              @RequestParam String dateStart,
                              @RequestParam String dateEnd){
        dateStart = dateStart+" 00:01:00";
        dateEnd = dateEnd+" 00:01:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        try {
            start= format.parse(dateStart);
            end= format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trackingService.addHospital(start, end, user);
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/add_vacation_{user}", method = RequestMethod.POST)
    public String addVacation(HttpServletRequest request,
                              Model model,
                              @PathVariable String user,
                              @RequestParam String dateStart,
                              @RequestParam String dateEnd){
        dateStart = dateStart+" 00:01:00";
        dateEnd = dateEnd+" 00:01:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        try {
            start= format.parse(dateStart);
            end= format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trackingService.addVacation(start, end, user);
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/add_commanding_{user}", method = RequestMethod.POST)
    public String addCommanding(HttpServletRequest request,
                              Model model,
                              @PathVariable String user,
                              @RequestParam String dateStart,
                              @RequestParam String dateEnd){
        dateStart = dateStart+" 00:01:00";
        dateEnd = dateEnd+" 00:01:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        try {
            start= format.parse(dateStart);
            end= format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trackingService.addCommanding(start, end, user);
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/delete_{user}_{dateStr}")
    public String deleteHospital(
            HttpServletRequest request,
            @PathVariable String user,
            @PathVariable String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date= format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trackingService.deleteTrack(date, user);
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/update_{user}_{date}_{timeOld}", method = RequestMethod.POST)
    public String updateTime(
            HttpServletRequest request,
            Model model,
            @PathVariable String user,
            @PathVariable String date,
            @PathVariable String timeOld,
            @RequestParam String time){

        trackingService.updateTime(user, date, timeOld, time);
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping("/calendar-delete-{date}")
    public String deleteDate(Model model, @PathVariable String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate= format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appUserService.deleteDate(newDate);
        return "redirect:/calendar";
    }



    @RequestMapping(value = "/addDate",  method = RequestMethod.POST)
    public String addDate(Model model,
            @RequestParam String date,
            @RequestParam int time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
            try {
                newDate= format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (appUserService.isDayExist(newDate)){
                model.addAttribute("error", true);
                Map<String, Integer> calendarMap = trackingService.getCallendar();
                model.addAttribute("calendarMap", calendarMap);
                return "calendar";
            } else {
                appUserService.addDate(newDate, time);
                return "redirect:/calendar";
            }
    }

    @RequestMapping(value = "/admin-another-date",  method = RequestMethod.POST)
    public String showAnotherDate(@RequestParam String date){
        return "redirect:/admin-"+date;
    }

    @RequestMapping("/admin-{date}")
    public String adminAnotherDate(Model model, Principal principal, @PathVariable String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, UserForAdmin> usersStatusMap = appUserService.getAllUsers(date);
        model.addAttribute("currDate", date);
        model.addAttribute("usersStatusMap", usersStatusMap);
        model.addAttribute("nostatus", true);
        return "admin";
    }

    @RequestMapping("/admin")
    public String admin(Model model, Principal principal){
        Map<String, UserForAdmin> usersStatusMap = appUserService.getAllUsers("");
        String currDate = appUserService.getStringDate();
        model.addAttribute("currDate", currDate);
        model.addAttribute("usersStatusMap", usersStatusMap);
        return "admin";
    }

    @RequestMapping("/calendar")
    public String calendar (Model model){
        Map<String, Integer> calendarMap = trackingService.getCallendar();
        model.addAttribute("calendarMap", calendarMap);

        return "calendar";
    }

    @RequestMapping("/comein")
    public String comein(Model model, Principal principal){
        appUserService.addTime(principal.getName(), 1);
        return "redirect:/";
    }

    @RequestMapping("/away")
    public String away(Model model, Principal principal){
        appUserService.addTime(principal.getName(), 2);
        return "redirect:/";
    }

    @RequestMapping("/returned")
    public String returned(Model model, Principal principal){
        appUserService.addTime(principal.getName(), 3);
        return "redirect:/";
    }

    @RequestMapping("/gone")
    public String gone(Model model, Principal principal){
        appUserService.addTime(principal.getName(), 4);
        return "redirect:/";
    }


    @RequestMapping(value = "/addfile",  method = RequestMethod.POST)
    public String addfile(Model model,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam String year,
                          @RequestParam String month){

        Date date = null;
        String dateStr = year + "-" + month;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        try {
            date= format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        reportService.generateReport(request, date);

        response.setHeader("Content-Disposition", "attachment; filename=" + "report.xls");
        return "redirect:/resources/report.xls";
    }

}
