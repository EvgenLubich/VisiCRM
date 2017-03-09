package com.lun.controller;

/**
 * Created by lubich on 10.02.17.
 */
import com.lun.model.Tracking;
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


import java.security.Principal;
import java.util.List;

@Controller
public class RootController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private TrackingService trackingService;

    @RequestMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("currentLogin", principal.getName());
        int status = appUserService.getUserStatus(principal.getName());
        model.addAttribute("status", status);
        WorkingYear workingYear = appUserService.getYears(principal.getName());
        model.addAttribute("workingYear", workingYear.getYears());
        List<WorkingDay> workingDay = trackingService.getWorkingDay(principal.getName());
        model.addAttribute("workingDay", workingDay);

        WorkingOff workingOff = new WorkingOff(workingDay, principal.getName());
        model.addAttribute("workingOff", workingOff.getCurrWorkingOffTime());

        return "index";
    }

    @RequestMapping(value = "/statistic-{year}")
    public String showStatForYear(Model model, @PathVariable int year, Principal principal) {
        WorkingMonthes workingMonthes = appUserService.getMonthes(principal.getName(), year);
        model.addAttribute("workingMonthes", workingMonthes.getMonthes());
        model.addAttribute("year", year);
        return "year";
    }

    @RequestMapping(value = "/statistic-{year}/{month}")
    public String showStatForMonth(Model model, @PathVariable int year, @PathVariable String month, Principal principal) {

        model.addAttribute("currentLogin", principal.getName());
        int status = appUserService.getUserStatus(principal.getName());
        model.addAttribute("status", status);
        WorkingYear workingYear = appUserService.getYears(principal.getName());
        model.addAttribute("workingYear", workingYear.getYears());

        int currMonth = java.time.Month.valueOf(month).ordinal();
        List<WorkingDay> workingDay = trackingService.getWorkingDayForSomeMonth(principal.getName(), year, currMonth);
        model.addAttribute("workingDay", workingDay);

        WorkingOff workingOff = new WorkingOff(workingDay, principal.getName(), year, currMonth);

        return "history";
    }

    @RequestMapping("/adduser")
    public String adduser(){
        return "adduser";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String adduserForm(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String role){
        try {
            appUserService.registerUser(login, password, role);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/adduser";
        }
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

}
