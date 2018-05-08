package com.salvador.user.controller;

import com.salvador.user.dto.UserDto;
import com.salvador.user.persistence.model.User;
import com.salvador.user.service.UserDetailsImpl;
import com.salvador.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;



    @GetMapping("/users")
    public ModelAndView list (ModelAndView modelAndView, Principal principal){
                Authentication authentication = (Authentication) principal;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("/api/users")
    public List <UserDto> apiList(ModelAndView modelAndView, Principal principal){
        List <User> userList = userService.findAllByOrderByEmailAsc();
        List <UserDto> userDtoList = new ArrayList();

        for (User user: userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

}


