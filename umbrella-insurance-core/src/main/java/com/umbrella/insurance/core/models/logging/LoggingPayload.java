package com.umbrella.insurance.core.models.logging;

import lombok.Data;

import java.math.BigInteger;
import java.time.Duration;

@Data
public class LoggingPayload {
    private String message;
    private HttpMethodEnum httpMethod;
    private Object requestBody;
    private Object responseBody;
    private Duration responseTime;
    private BigInteger requestNumber;
    private Integer statusCode;
    private Integer ErrorCode;
    private String requestURI;
    private String queryString;
}
