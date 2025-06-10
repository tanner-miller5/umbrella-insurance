package com.umbrella.insurance.endpoints.rest.users.v1.requests;

import lombok.Data;

@Data
public class DeleteUserRequest {
    private String username;
    private String password;
}
