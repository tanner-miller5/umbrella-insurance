package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String existingPassword;
    private String newPassword;
}
