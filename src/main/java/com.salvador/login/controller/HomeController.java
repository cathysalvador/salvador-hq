package com.salvador.login.controller;

import com.salvador.user.persistence.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

//    @RequestMapping("/")
//    public String home(){
//
//        return "index";
//    }

    @RequestMapping("/index")
    public ModelAndView home(ModelAndView modelAndView){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            modelAndView.addObject("user", user);
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }
}


