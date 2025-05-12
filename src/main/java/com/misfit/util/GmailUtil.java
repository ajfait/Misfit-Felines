package com.misfit.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.*;
import org.apache.logging.log4j.*;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * The `GmailUtil` class in Java provides methods to obtain authorized credentials and create a Gmail
 * service for sending emails using the Gmail API.
 */
public class GmailUtil {

    private static final Logger logger = LogManager.getLogger(GmailUtil.class);

    private static final String APPLICATION_NAME = "Misfit Felines Foster Portal";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * The function `getCredentials` loads client secrets, builds a flow for user authorization, and
     * returns the authorized credential object.
     * 
     * @param HTTP_TRANSPORT The `HTTP_TRANSPORT` parameter in the `getCredentials` method is an instance
     * of `NetHttpTransport` class. This class is typically used in Java applications to handle HTTP
     * requests and responses. It provides the necessary functionality to communicate over HTTP and HTTPS
     * protocols.
     * @return The method `getCredentials` returns a `Credential` object after authorizing the user with
     * the provided client secrets and authorization flow.
     */
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        logger.debug("Authorized credential object created.");
        return credential;
    }

    /**
     * The function `getGmailService` returns a Gmail service instance using specified HTTP transport, JSON
     * factory, credentials, and application name.
     * 
     * @return An instance of the Gmail service is being returned.
     */
    public static Gmail getGmailService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
    }
}