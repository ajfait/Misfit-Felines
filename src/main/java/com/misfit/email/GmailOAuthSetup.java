package com.misfit.email;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.logging.log4j.*;

import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * The type Gmail o auth setup.
 */
public class GmailOAuthSetup {
    private static final Logger logger = LogManager.getLogger(GmailOAuthSetup.class);
    private static final String APPLICATION_NAME = "Misfit Felines Foster Portal";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/Volumes/AJPC_2TB_SSD/*Enterprise Java/Misfit-Felines/src/main/resources/credentials.json";

    private static Gmail getGmailService() throws Exception {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(CREDENTIALS_FILE_PATH));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build();

        return new Gmail.Builder(httpTransport, JSON_FACTORY, new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user")).setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        Gmail service = getGmailService();
        logger.debug("Successfully authorized Gmail access!");
    }
}
