package vn.hoidanit.jobhunter.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailSender mailSender;

    public EmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("nguyendat090803@gmail.com");
        msg.setSubject("Testing from Spring boot");
        msg.setText("Hello world from Spring boot email");
        this.mailSender.send(msg);
    }
}
