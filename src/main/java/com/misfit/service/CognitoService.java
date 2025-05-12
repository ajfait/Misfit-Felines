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
 * The `CognitoService` class in Java provides methods to interact with Amazon Cognito user pools such
 * as checking user existence, creating users, and deleting users.
 */
public class CognitoService implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final CognitoIdentityProviderClient cognitoClient;
    private final String poolID;
    private final String tempPassword;

    /**
     * This `public CognitoService() throws Exception` constructor in the `CognitoService` class is
     * responsible for initializing the Cognito service by loading properties from a file named
     * `cognito.properties`.
     */
    public CognitoService() throws Exception {
        Properties cognitoProperties = loadProperties("/cognito.properties");

        this.poolID = cognitoProperties.getProperty("poolId");
        this.tempPassword = cognitoProperties.getProperty("tempPassword");

        Region region = Region.of(cognitoProperties.getProperty("region"));
        this.cognitoClient = CognitoIdentityProviderClient.builder().region(region).credentialsProvider(DefaultCredentialsProvider.create()).build();
    }

    /**
     * The function `userExists` checks if a user with a specific email exists in a Cognito user pool.
     * 
     * @param email The `email` parameter in the `userExists` method represents the email address of the
     * user for whom you want to check existence in the Cognito user pool. The method uses the Cognito
     * AdminGetUser API to retrieve user information based on the provided email address. If the user is
     * found,
     * @return The `userExists` method returns a boolean value indicating whether a user with the specified
     * email exists in the Cognito user pool. If the user is found, it returns `true`, otherwise it returns
     * `false`.
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
     * The `createUser` function creates a new user in Amazon Cognito user pool with the provided email and
     * sets the user attributes accordingly.
     * 
     * @param email The `email` parameter in the `createUser` method represents the email address of the
     * user for whom you are creating a Cognito user in AWS Cognito User Pools. This email address will be
     * used as the username for the user in the Cognito user pool.
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
     * The `deleteUser` function deletes a user from a Cognito User Pool using the provided email address.
     * 
     * @param email The `email` parameter in the `deleteUser` method represents the email address of the
     * user whose account is being deleted from the user pool.
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