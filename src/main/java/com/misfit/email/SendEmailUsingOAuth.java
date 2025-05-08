package com.misfit.email;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.misfit.util.GmailUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SendEmailUsingOAuth {
    private static final Logger logger = LogManager.getLogger(SendEmailUsingOAuth.class);

    /**
     * Send an email with dynamic subject and body using Gmail API and OAuth2.
     *
     * @param subject The subject line of the email.
     * @param body    The text body of the email.
     */
    public static void sendEmail(String subject, String body) throws MessagingException, IOException, GeneralSecurityException {

        logger.debug("Preparing to send email.");

        // Build Gmail service
        Gmail gmailService = new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                GmailUtil.getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName("Misfit Felines Foster Portal")
                .build();

        logger.debug("Gmail service built.");

        // Create the email message
        MimeMessage email = CreateEmail.createEmail(
                "entjavastudent@gmail.com",
                "jafait1004@gmail.com",
                subject,
                body
        );

        logger.debug("Email message created.");

        // Send the email
        Message message = CreateMessage.createMessageWithEmail(email);
        gmailService.users().messages().send("me", message).execute();

        logger.debug("Email sent.");

        Message sentMessage = gmailService.users().messages().send("me", message).execute();

        if (sentMessage != null) {
            logger.info("Email sent successfully. Gmail Message ID: {}", sentMessage.getId());
        } else {
            logger.warn("Gmail API call returned null message.");
        }
    }
}