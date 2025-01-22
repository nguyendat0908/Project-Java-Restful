package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/email")
    @ApiMessage("Send simple email")
    public String sendSimpleEmail() {

        // this.emailService.sendSimpleEmail();
        // this.emailService.sendEmailSync("datleo090803@gmail.com", "Test send email",
        // "<h1><b>Hello</b></h1>", false,
        // true);

        this.emailService.sendEmailFromTemplateSync("datleo090803@gmail.com", "Test send email", "job");

        return "Hello!";
    }

}
