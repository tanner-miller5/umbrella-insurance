package com.umbrella.insurance.endpoints.rest.users.v1.responses;

import lombok.Data;

@Data
public class CheckUserTwoFactorResponse {
    private String twoFactorAuthType;
}
