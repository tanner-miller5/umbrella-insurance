package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import lombok.Data;

@Data
public class ChangeTwoFactorAuthRequest {
    private String existingPassword;
    private String twoFactorAuthType;
}
