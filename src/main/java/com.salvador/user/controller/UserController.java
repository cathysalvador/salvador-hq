package com.salvador.user.controller;

import com.salvador.user.dto.UserDto;
import com.salvador.user.persistence.model.User;
import com.salvador.user.service.UserDetailsImpl;
import com.salvador.user.service.UserService;
import com.salvador.util.error.CustomErrorType;
import com.salvador.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;


//    APIs
    @GetMapping("/api/users")
    public List<UserDto> apiList(){
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

    @GetMapping("/api/users/{userId}")
    public UserDto apiView(@PathVariable(value="userId") Long id) {
        Optional<User> optionalUser = userService.findById(id);
        User user = optionalUser.get();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    @PostMapping("api/users")
    public ResponseEntity<?> apiCreate(@RequestBody UserDto userDto, HttpServletRequest request) {
        User user = userService.findByEmail(userDto.getEmail());

        if (user != null) {
            return new ResponseEntity(
                    new CustomErrorType("There is already a user with the corresponding email address in the system."),
                    HttpStatus.CONFLICT);
        }

        user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEnabled(Boolean.FALSE);
        user.setConfirmationToken(UUID.randomUUID().toString());
        userService.save(user);

        userDto.setId(user.getId());

        StringBuilder sbEmailBody = new StringBuilder();
        sbEmailBody.append("To confirm your e-mail address, please click the link below:\n");
        sbEmailBody.append(request.getScheme());
        sbEmailBody.append("://");
        sbEmailBody.append(request.getServerName());
        sbEmailBody.append(":");
        sbEmailBody.append(request.getServerPort());
        sbEmailBody.append("/confirm?token=");
        sbEmailBody.append(user.getConfirmationToken());

        SimpleMailMessage signupEmail = new SimpleMailMessage();
        signupEmail.setTo(user.getEmail());
        signupEmail.setSubject("Signup Confirmation");
        signupEmail.setText(sbEmailBody.toString());
        signupEmail.setFrom("appuser.salvador@gmail.com");

        emailService.sendMail(signupEmail);

        StringBuilder sbMessage = new StringBuilder("A confirmation e-mail has been sent to ");
        sbMessage.append(user.getEmail());

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping("/api/users/{userId}")
    public ResponseEntity<?> apiEdit(@PathVariable("userId") Long id, @RequestBody UserDto userDto) {

        Optional<User> optionalUser = userService.findById(id);
        User user = optionalUser.get();

        if (user == null || (user.getId().compareTo(userDto.getId()) != 0 ) ) {
            return new ResponseEntity(
                    new CustomErrorType("Unable to update user."),
                    HttpStatus.NOT_FOUND);
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        userService.save(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<?> apiDelete(@PathVariable("userId") Long id) {
        Optional<User> optionalUser = userService.findById(id);
        User user = optionalUser.get();
        if (user == null) {
            return new ResponseEntity(
                    new CustomErrorType("Unable to delete user."),
                    HttpStatus.NOT_FOUND);
        }

        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//    Internal
    @GetMapping("/users")
    public ModelAndView list (ModelAndView modelAndView, Principal principal){
                Authentication authentication = (Authentication) principal;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user");
        return modelAndView;
    }





}


