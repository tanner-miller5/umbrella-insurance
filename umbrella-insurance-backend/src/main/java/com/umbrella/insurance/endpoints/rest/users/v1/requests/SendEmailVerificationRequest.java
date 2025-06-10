package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import lombok.Data;

@Data
public class SendEmailVerificationRequest {
    public String username;
    public String emailAddress;
    public String phoneNumber;
}
