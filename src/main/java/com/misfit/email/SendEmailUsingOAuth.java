package com.misfit.email;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.misfit.util.GmailUtil;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.auth.oauth2.Credential;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;

public class SendEmailUsingOAuth {

    /**
     * Send the email using Gmail API with OAuth2 authentication.
     *
     * @param gmailService Gmail API service
     * @param emailContent The MimeMessage to be sent
     * @return The sent message
     * @throws MessagingException if there are issues with message formatting
     * @throws IOException if there are issues with communication with Gmail API
     */
    public static Message sendEmail(Gmail gmailService, MimeMessage emailContent)
            throws MessagingException, IOException {
        // Convert MimeMessage to Message object (Base64 encoded)
        Message message = CreateMessage.createMessageWithEmail(emailContent);

        // Send the email using Gmail API
        return gmailService.users().messages().send("me", message).execute();
    }

    public static void main(String[] args) throws Exception {
        // Initialize Gmail service with OAuth credentials
        Credential credential = GmailUtil.getCredentials(GoogleNetHttpTransport.newTrustedTransport());

        // Create the Gmail service
        Gmail gmailService = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(), credential)
                .setApplicationName("Misfit Felines Foster Portal")
                .build();

        // Create MimeMessage
        MimeMessage email = CreateEmail.createEmail("to@example.com", "from@example.com",
                "Test Subject", "This is a test email body.");

        // Send the email
        Message sentMessage = sendEmail(gmailService, email);
        System.out.println("Email sent successfully! Message ID: " + sentMessage.getId());
    }
}
