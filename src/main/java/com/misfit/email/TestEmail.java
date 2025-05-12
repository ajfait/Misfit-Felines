package com.misfit.email;

import jakarta.mail.MessagingException;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * The `TestEmail` class is designed to assist in testing email-related functionalities.
 * It serves as a utility or test framework to validate email creation, message encoding,
 * Gmail service authorization, and OAuth2 authentication processes.
 *
 * The `TestEmail` class ensures the functionality of the email system by providing a platform
 * for unit tests or integration tests within a development environment.
 */
public class TestEmail {
    private static final Logger logger = LogManager.getLogger(TestEmail.class);

    public static void main(String[] args) {
        try {
            String subject = "Test Email from Misfit";
            String body = "This is a test email sent using the Gmail API.";

            SendEmailUsingOAuth.sendEmail(subject, body);

            logger.debug("Email sent successfully.");

        } catch (MessagingException | IOException | GeneralSecurityException e) {
            logger.error("Failed to send email: " + e.getMessage());
        }
    }
}