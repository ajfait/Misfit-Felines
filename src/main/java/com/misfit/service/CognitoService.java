package com.misfit.service;

import com.misfit.persistence.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

import java.util.List;
import java.util.Properties;

public class CognitoService implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final CognitoIdentityProviderClient cognitoClient;
    private final String poolID;
    private final String tempPassword;

    public CognitoService() throws Exception {
        Properties cognitoProperties = loadProperties("/cognito.properties");

        this.poolID = cognitoProperties.getProperty("poolId");
        this.tempPassword = cognitoProperties.getProperty("tempPassword");

        Region region = Region.of(cognitoProperties.getProperty("region"));
        this.cognitoClient = CognitoIdentityProviderClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public void createUser(String email) {
        try {
            AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                    .userPoolId(poolID)
                    .username(email)
                    .userAttributes(List.of(
                            AttributeType.builder().name("email").value(email).build(),
                            AttributeType.builder().name("email_verified").value("false").build()
                    ))
                    .temporaryPassword(tempPassword)
                    .build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(request);
            logger.info("Created Cognito user: {}", response.user().username());

        } catch (CognitoIdentityProviderException e) {
            logger.error("Failed to create Cognito user", e);
        }
    }
}
