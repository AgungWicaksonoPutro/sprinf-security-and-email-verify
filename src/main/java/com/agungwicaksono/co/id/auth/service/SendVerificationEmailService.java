package com.agungwicaksono.co.id.auth.service;

import com.agungwicaksono.co.id.auth.model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendVerificationEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void execute(Customer user, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "wiputrosnc@gmail.com";
        String senderName = "TestEmailCompany";
        String subject = "PLEASE VERIFY YOUR EMAIL";
        String content = "Dear [[name]], <br>"
                + "Please click link below to verification: <br>"
                + "<h3><a href=\"[[url]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank You"
                + "[[company]]";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUserName());
        String path = siteUrl + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[url]]", path);
        content = content.replace("[[company]]", senderName);

        helper.setText(content, true);
        javaMailSender.send(message);
    }
}
