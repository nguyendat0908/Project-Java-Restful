package vn.hoidanit.jobhunter.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.repository.JobRepository;

@Service
public class EmailService {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final JobRepository jobRepository;

    public EmailService(MailSender mailSender, JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine,
            JobRepository jobRepository) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
        this.jobRepository = jobRepository;
    }

    /**
     * Sends a simple email with a predefined recipient, subject, and message body.
     * 
     * This method creates a SimpleMailMessage object, sets the recipient's email
     * address,
     * the subject of the email, and the text content of the email. It then uses the
     * mailSender to send the email.
     */
    public void sendSimpleEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("nguyendat090803@gmail.com");
        msg.setSubject("Testing from Spring boot");
        msg.setText("Hello world from Spring boot email");
        this.mailSender.send(msg);
    }

    /**
     * Sends an email synchronously.
     *
     * @param to          the recipient email address
     * @param subject     the subject of the email
     * @param content     the content of the email
     * @param isMultipart whether the email is multipart
     * @param isHtml      whether the email content is in HTML format
     */
    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            System.out.println("ERROR SEND EMAIL: " + e);
        }
    }

    public void sendEmailFromTemplateSync(String to, String subject, String templateName) {

        Context context = new Context();
        List<Job> arrJobs = this.jobRepository.findAll();
        String name = "Datleo";
        context.setVariable("name", name);
        context.setVariable("jobs", arrJobs);
        
        String content = this.springTemplateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }

}
