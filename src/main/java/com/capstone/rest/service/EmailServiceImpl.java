package com.capstone.rest.service;

import com.capstone.rest.util.MailMsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl{

    @Autowired
    private JavaMailSender sender;

    public void sendSimpleMessage(String to, String password, String noRm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("siunusa15@gmail.com");
        message.setTo(to);
        message.setSubject("Akun untuk Login Aplikasi KlikNik Pratama UNUSA");
        message.setText("Perhatian!!! Jangan memberitahu Email dan Password anda kepada Orang Lain. \n\nNo Rekam medis: " + noRm + "\nEmail: " + to + "\nPassword: " + password + "\n\n Note: Silahkan memberi bintang pada pesan ini agar tidak hilang.");
        sender.send(message);
    }

}
