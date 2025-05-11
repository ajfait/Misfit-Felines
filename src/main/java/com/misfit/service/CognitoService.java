package com.misfit.service;

import com.misfit.persistence.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.List;
import java.util.Properties;

/**
 * The type Cognito service.
 */
public class CognitoService implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final CognitoIdentityProviderClient cognitoClient;
    private final String poolID;
    private final String tempPassword;

    /**
     * Instantiates a new Cognito service.
     *
     * @throws Exception the exception
     */
    public CognitoService() throws Exception {
        Properties cognitoProperties = loadProperties("/cognito.properties");

        this.poolID = cognitoProperties.getProperty("poolId");
        this.tempPassword = cognitoProperties.getProperty("tempPassword");

        Region region = Region.of(cognitoProperties.getProperty("region"));
        this.cognitoClient = CognitoIdentityProviderClient.builder().region(region).credentialsProvider(DefaultCredentialsProvider.create()).build();
    }

    /**
     * User exists boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean userExists(String email) {
        try {
            AdminGetUserRequest request = AdminGetUserRequest.builder().userPoolId(poolID).username(email).build();

            AdminGetUserResponse response = cognitoClient.adminGetUser(request);
            return response != null;
        } catch (UserNotFoundException e) {
            return false;
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Error checking user existence in Cognito", e);
        }
    }

    /**
     * Create user.
     *
     * @param email the email
     */
    public void createUser(String email) {
        try {
            AdminCreateUserRequest request = AdminCreateUserRequest.builder().userPoolId(poolID).username(email).userAttributes(List.of(AttributeType.builder().name("email").value(email).build(), AttributeType.builder().name("email_verified").value("true").build())).temporaryPassword(tempPassword).build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(request);
            logger.debug("Created Cognito user: {}", response.user().username());

        } catch (CognitoIdentityProviderException e) {
            logger.error("Failed to create Cognito user", e);
        }
    }

    /**
     * Delete user.
     *
     * @param email the email
     */
    public void deleteUser(String email) {
        try {
            AdminDeleteUserRequest request = AdminDeleteUserRequest.builder().userPoolId(poolID).username(email).build();

            cognitoClient.adminDeleteUser(request);

            logger.debug("User " + email + " deleted successfully from User Pool " + poolID);
        } catch (CognitoIdentityProviderException e) {
            logger.error("Error deleting user: " + e.getMessage(), e);
        }
    }
}
