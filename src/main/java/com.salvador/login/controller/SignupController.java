package com.salvador.login.controller;

import com.salvador.user.dto.UserDto;
import com.salvador.login.dto.UserPasswordDto;
import com.salvador.user.persistence.model.Role;
import com.salvador.user.persistence.model.User;
import com.salvador.util.service.EmailService;
import com.salvador.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class SignupController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messages;

    @Autowired
    public SignupController() {

        super();
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView submitSignupForm(@ModelAttribute("user") @Valid UserDto userDto,
                                         BindingResult bindingResult,
                                         HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findByEmail(userDto.getEmail());

        if (user != null) {
            modelAndView.addObject("errorMessage",
                    "There is already a user with the corresponding email address in the system.");
            bindingResult.reject("email");
            modelAndView.setViewName("signup");
            return modelAndView;
        }

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            bindingResult.reject("email");
            modelAndView.setViewName("signup");
            return modelAndView;
        }

        user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEnabled(Boolean.FALSE);
        user.setConfirmationToken(UUID.randomUUID().toString());
        userService.save(user);

        //String appUrl =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

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

        modelAndView.addObject("successMessage", sbMessage.toString());
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @GetMapping("/confirm")
    public String showConfirmSignupForm (@RequestParam("token") String token, Model model) {

        User user = userService.findByConfirmationToken(token);

        if (user == null) { // No token found in DB
            model.addAttribute("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else { // Token found
            model.addAttribute("confirmationToken", user.getConfirmationToken());
        }

        return "confirm";
    }

    @PostMapping("/confirm")
    public ModelAndView submitConfirmSignupForm (@RequestParam Map requestParams,
                                           @ModelAttribute("password")
                                           @Valid UserPasswordDto userPasswordDto,
                                           BindingResult bindingResult,
                                           Model model,
                                           HttpServletRequest request) {


        String strToken = requestParams.get("token").toString();
        if (strToken.isEmpty()) {
            strToken = model.asMap().get("token").toString();
        }

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errorMessage",
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
            bindingResult.reject("confirmPassword");
            modelAndView.setViewName("confirm");
            modelAndView.addObject("token", strToken);
            return modelAndView;
        }

        // Find the user associated with the reset token
        User user = userService.findByConfirmationToken(strToken);
        //User user = userService.findByConfirmationToken(userPasswordDto.getPassword());

        if (user == null) { // No token found in DB
            modelAndView.addObject("errorMessage", "User not found.");
            modelAndView.setViewName("confirm");
            return modelAndView;
        }
            // Set new password
        //user.setPassword(passwordEncoder().encode(requestParams.get("password").toString()));
        user.setPassword(passwordEncoder().encode(userPasswordDto.getPassword()));

        // Set user to enabled
        user.setEnabled(true);

        // Save user
        User savedUser = userService.save(user);

        Collection<GrantedAuthority> authorities = new HashSet();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(savedUser.getEmail()), null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}


