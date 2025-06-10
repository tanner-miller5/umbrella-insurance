package com.umbrella.insurance.endpoints.rest.users.sessions.v1;

import lombok.Data;

@Data
public class SessionCreateRestRequest {
    private String username;
    private String password;
    private String twoFactorCode;
}
