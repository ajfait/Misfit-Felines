package com.misfit.email;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.misfit.util.GmailUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TestEmail {

    public static void main(String[] args) {
        // Initialize the logger
        Logger logger = LogManager.getLogger(TestEmail.class);

        try {
            // Step 1: Get the Gmail service
            Gmail gmailService = GmailUtil.getGmailService();

            // Step 2: Create the email
            MimeMessage email = CreateEmail.createEmail("entjavastudent@gmail.com", "jafait1004@gmail.com",
                    "Test Subject", "This is the body of the test email");

            // Step 3: Send the email using the OAuth credentials and Gmail service
            Message sentMessage = SendEmailUsingOAuth.sendEmail(gmailService, email);

            // Log success
            logger.debug("Email sent successfully. Message ID: " + sentMessage.getId());
        } catch (MessagingException | IOException e) {
            // Log errors
            logger.error("Error sending email: ", e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
