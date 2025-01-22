package vn.hoidanit.jobhunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//disable security
// @SpringBootApplication(exclude = {
// 		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
// 		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
// })

/**
 * The main entry point for the Jobhunter Spring Boot application.
 * This class is annotated with @SpringBootApplication, @EnableAsync,
 * and @EnableScheduling.
 * 
 * @SpringBootApplication: Indicates a configuration class that declares one or
 *                         more @Bean methods and also triggers
 *                         auto-configuration and component scanning.
 * @EnableAsync: Enables Spring's asynchronous method execution capability.
 * @EnableScheduling: Enables Spring's scheduled task execution capability.
 * 
 *                    The main method uses Spring Boot's SpringApplication.run()
 *                    method to launch the application.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class JobhunterApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobhunterApplication.class, args);
	}

}
