package com.visionaryproviders.agridoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visionaryproviders.agridoctor.payloads.EmailVerificationRequest;
import com.visionaryproviders.agridoctor.payloads.EmailVerificationResponse;
import com.visionaryproviders.agridoctor.services.EmailService;

import java.util.Random;




@RestController
@RequestMapping("/api")
public class EmailVerificationController {

	private final EmailService emailService;

    @Autowired
    public EmailVerificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/verifyemail")
    public EmailVerificationResponse sendVerificationEmail(@RequestBody EmailVerificationRequest emailRequest) {
        // Generate a random verification code
        String verificationCode = generateRandomCode();

        // Send the verification code to the email address
        emailService.sendEmail(emailRequest.getEmail(), "Verification Code Registration on AgriDoctor", verificationCode);
        
        EmailVerificationResponse emailVerificationResponse = new EmailVerificationResponse();
      
        emailVerificationResponse.setVerificationCode(verificationCode);
        // Return the verification code to the caller
        return emailVerificationResponse;
    }

    private String generateRandomCode() {
        // Generate a random alphanumeric code (you can customize this)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        int length = 6; // You can change the length of the code
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }
	 
	
}
