package com.misfit.email;

import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.*;

/**
 * The `CreateMessage` class provides a method to create a `Message` object with base64 encoded email
 * content from a `MimeMessage` object.
 */
public class CreateMessage {

    /**
     * The function `createMessageWithEmail` takes a MimeMessage object, converts it to a base64 encoded
     * string, and creates a Message object with the encoded email content.
     * 
     * @param emailContent MimeMessage emailContent is an object representing an email message in MIME
     * format. It contains the content, headers, and attachments of the email message.
     * @return An instance of the `Message` class is being returned.
     */
    public static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}