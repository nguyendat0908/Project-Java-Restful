package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.service.SubscriberService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    private EmailService emailService;
    private SubscriberService subscriberService;

    public EmailController(EmailService emailService, SubscriberService subscriberService) {
        this.emailService = emailService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/email")
    @ApiMessage("Send simple email")
    @Scheduled(cron = "*/60 * * * * *")
    @Transactional
    public String sendSimpleEmail() {

        // this.emailService.sendSimpleEmail();
        // this.emailService.sendEmailSync("datleo090803@gmail.com", "Test send email",
        // "<h1><b>Hello</b></h1>", false,
        // true);

        this.subscriberService.sendSubscribersEmailJobs();

        return "Successful!";
    }

}
