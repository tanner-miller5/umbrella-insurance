package com.umbrella.insurance.core.models.logging;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LogWrapper {
    private Timestamp timestamp;
    private LogLevelEnum logLevel;
    private LoggingPayload loggingPayload;
    private String callingLoggerName;
    private String callingMethod;
}
