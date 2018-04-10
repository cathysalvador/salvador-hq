package com.salvador.login.controller;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.salvador.login.domain.Role;
import com.salvador.login.domain.User;
import com.salvador.login.service.EmailService;
import com.salvador.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class SignupController {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    public SignupController() {

        super();
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model, User user) {
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String submitSignupForm(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {

        User userFound = userService.findByEmail(user.getEmail());

        if (userFound != null) {
            model.addAttribute("errorMessage", "There is already a user with the corresponding email address in the system.");
            bindingResult.reject("email");
            return "signup";
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        user.setEnabled(Boolean.FALSE);
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmationToken(UUID.randomUUID().toString());
        userService.save(user);

        String appUrl =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        SimpleMailMessage signupEmail = new SimpleMailMessage();
        signupEmail.setTo(user.getEmail());
        signupEmail.setSubject("Signup Confirmation");
        signupEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + user.getConfirmationToken());
        signupEmail.setFrom("appuser.salvador@gmail.com");

        emailService.sendMail(signupEmail);

        model.addAttribute("successMessage", "A confirmation e-mail has been sent to " + user.getEmail());

        return "signup";
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
    public String submitConfirmSignupForm (@RequestParam Map requestParams, Model model, HttpServletRequest request) {

        //Zxcvbn passwordCheck = new Zxcvbn();

        //Strength strength = passwordCheck.measure(requestParams.get("password").toString());

//        if (strength.getScore() < 3) {
//            bindingResult.reject("password");

//            redirectAttributes.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
//            System.out.println(requestParams.get("token"));
//            return "redirect:confirm?token=\" + requestParams.get(\"token\")";
//        }

        // Find the user associated with the reset token
        User user = userService.findByConfirmationToken(requestParams.get("token").toString());

        // Set new password
        user.setPassword(passwordEncoder().encode(requestParams.get("password").toString()));

        // Set user to enabled
        user.setEnabled(true);

        // Save user
        User savedUser = userService.save(user);

        //model.addAtltribute("successMessage", "Your password has been set!");

        //model.addAttribute("email", user.getEmail());
        //model.addAttribute("password", user.getPassword());

//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), );
//        authToken.setDetails(new WebAuthenticationDetails(request));
//        Authentication authentication = authenticationManager.authenticate(authToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<GrantedAuthority> authorities = new HashSet();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}


