package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import com.umbrella.insurance.core.deserializers.DateDeserializer;
import com.umbrella.insurance.core.serializers.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Date;

@Data
public class CreateUserRequest {
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private String firstName;
    private String middleName;
    private String surname;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date dateOfBirth;
    private String ssn;
    private String password;
    private String twoFactorMethod;
    private Boolean consentedToEmails;
    private Boolean consentedToTexts;
    private Boolean consentedToTermsAndConditions;
}
