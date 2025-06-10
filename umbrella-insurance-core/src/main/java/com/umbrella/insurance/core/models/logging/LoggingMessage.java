package com.umbrella.insurance.core.models.logging;

import lombok.Data;

@Data
public class LoggingMessage {
    private String appName;
    private String logLevel;
    private String loggingPayload;
    private String callingLoggerName;
    private String callingMethod;
    private Double longitude;
    private Double latitude;
    private Double accuracy;
}
