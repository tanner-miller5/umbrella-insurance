package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String emailAddress;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Boolean consentedToEmails;
    private Boolean consentedToTexts;
    private String twoFactorAuthType;
    private String dateOfBirth;
}
