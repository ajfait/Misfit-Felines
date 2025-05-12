package com.misfit.email;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

/**
 * The `CreateEmail` class provides a method `createEmail` to generate a MimeMessage email with
 * specified recipient, sender, subject, and body text.
 */
public class CreateEmail {

    /**
     * The function `createEmail` creates a MimeMessage email with specified recipient, sender,
     * subject, and body text.
     * 
     * @param toEmailAddress The `toEmailAddress` parameter in the `createEmail` method is the email
     * address of the recipient to whom you want to send the email. This is where the email will be
     * delivered.
     * @param fromEmailAddress The `fromEmailAddress` parameter in the `createEmail` method represents the
     * email address from which the email will be sent. This should be a valid email address that the
     * recipient can identify as the sender.
     * @param subject The `subject` parameter in the `createEmail` method is a String that represents the
     * subject of the email that will be sent. It is typically a brief description or summary of the
     * content of the email.
     * @param bodyText The `bodyText` parameter in the `createEmail` method represents the content or text
     * of the email that you want to send. This is where you would typically include the main message or
     * information you want to convey in the email. It could be a simple text message, an HTML content, or
     * @return The `createEmail` method returns a `MimeMessage` object that is created with the specified
     * email details such as the recipient email address, sender email address, subject, and body text.
     */
    public static MimeMessage createEmail(String toEmailAddress, String fromEmailAddress, String subject, String bodyText) throws MessagingException {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(fromEmailAddress));
        email.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
}