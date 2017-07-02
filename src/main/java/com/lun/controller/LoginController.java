package com.lun.controller;

/**
 * Created by lubich on 10.02.17.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.lun.service.AppUserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Calendar;

@Controller
@RequestMapping("/")
public class LoginController {

//    @Autowired
//    private AppUserService appUserService;


    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        Calendar cal = Calendar.getInstance();
        int time = cal.get(Calendar.HOUR_OF_DAY);
        if (time != 24) {
            String referrer = request.getHeader("Referer");
            if (referrer == null || referrer.contains("signup")) {
                referrer = "/";
            }
            request.getSession().setAttribute("url_prior_login", referrer);
            return "login";
        } else {
            return "unauthorized";
        }
    }



    @RequestMapping("/unauthorized")
    public String unauthorized(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("currentLogin", principal.getName());
        }
        return "unauthorized";
    }
}