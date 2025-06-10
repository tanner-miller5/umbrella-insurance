package com.umbrella.insurance.core.models.exceptions;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DefaultExceptionResponse {
    private Timestamp timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String method;
}