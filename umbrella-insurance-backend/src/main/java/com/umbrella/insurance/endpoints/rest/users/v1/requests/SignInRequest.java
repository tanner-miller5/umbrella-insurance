package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SignInRequest {
    private String username;
    private String password;
    private String totp;
    private BigInteger verificationMethodId;
    private String verificationCode;
}
