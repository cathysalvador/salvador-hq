package com.salvador.util.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    public void sendMail (SimpleMailMessage mailMessage);
}
