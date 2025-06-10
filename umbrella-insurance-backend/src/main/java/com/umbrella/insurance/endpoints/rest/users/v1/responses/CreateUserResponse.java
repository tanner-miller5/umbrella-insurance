package com.umbrella.insurance.endpoints.rest.users.v1.responses;

import com.umbrella.insurance.core.deserializers.TimestampDeserializer;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.serializers.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreateUserResponse {
    private User user;
    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp createdDateTime;
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private Boolean isEmailAddressVerified;
    private Boolean isPhoneNumberVerified;
    private VerificationMethod verificationMethod;
    private String authAppDataUrl;
    private AccountBalance usdAccountBalance;
    private AccountBalance usdEscrowAccountBalance;
    private AccountBalance butterBucksAccountBalance;
    private AccountBalance butterBucksEscrowAccountBalance;
    private String sessionCode;
    private ApplicationRole[] applicationRoles;
    private String endDateTime;
    private String backendAppVersion;
    private Cart cart;
}
