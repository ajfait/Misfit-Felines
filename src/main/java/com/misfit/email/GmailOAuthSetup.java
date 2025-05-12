package com.misfit.email;

import com.google.api.client.extensions.java6.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.logging.log4j.*;

import java.io.*;
import java.util.List;

/**
 * The `GmailOAuthSetup` class sets up OAuth 2.0 authorization for accessing Gmail services in a Java
 * application.
 */
public class GmailOAuthSetup {
    private static final Logger logger = LogManager.getLogger(GmailOAuthSetup.class);
    private static final String APPLICATION_NAME = "Misfit Felines Foster Portal";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/Volumes/AJPC_2TB_SSD/*Enterprise Java/Misfit-Felines/src/main/resources/credentials.json";

    /**
     * The function `getGmailService` returns a Gmail service instance using OAuth 2.0 authorization.
     * 
     * @return An instance of the Gmail service is being returned.
     */
    private static Gmail getGmailService() throws Exception {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(CREDENTIALS_FILE_PATH));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build();

        return new Gmail.Builder(httpTransport, JSON_FACTORY, new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user")).setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * The main function authorizes access to Gmail service and logs a success message.
     */
    public static void main(String[] args) throws Exception {
        Gmail service = getGmailService();
        logger.debug("Successfully authorized Gmail access!");
    }
}