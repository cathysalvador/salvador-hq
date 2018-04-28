package com.salvador.user.controller;

import com.salvador.user.persistence.model.User;
import com.salvador.user.service.UserDetailsImpl;
import com.salvador.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;



    @GetMapping("/user")
    public ModelAndView user(ModelAndView modelAndView, Principal principal){
        Authentication authentication = (Authentication) principal;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user");
        return modelAndView;
    }
}


