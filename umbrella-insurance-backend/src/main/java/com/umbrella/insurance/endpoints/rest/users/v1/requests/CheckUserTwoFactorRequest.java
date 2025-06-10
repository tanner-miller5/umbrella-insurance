package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import lombok.Data;

@Data
public class CheckUserTwoFactorRequest {
    private String username;
    private String phoneNumber;
    private String emailAddress;
}
