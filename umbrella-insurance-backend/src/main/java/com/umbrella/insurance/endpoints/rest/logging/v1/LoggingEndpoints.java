package com.umbrella.insurance.endpoints.rest.logging.v1;

import com.umbrella.insurance.core.models.logging.LoggingMessage;
import jakarta.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/rest/logging/v1")
public class LoggingEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(LoggingEndpoints.class);

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    LoggingMessage createLogEndpoint(
            @RequestParam String env,
            @RequestBody LoggingMessage loggingMessage,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        MDC.put("appName", loggingMessage.getAppName());
        if (loggingMessage.getLongitude() != null) {
            MDC.put("longitude", loggingMessage.getLongitude().toString());
        }
        if (loggingMessage.getLatitude() != null) {
            MDC.put("latitude", loggingMessage.getLatitude().toString());
        }
        if (loggingMessage.getAccuracy() != null) {
            MDC.put("accuracy", loggingMessage.getAccuracy().toString());
        }

        switch (loggingMessage.getLogLevel()) {
            case "ERROR":
                logger.error(loggingMessage.toString());
            case "INFO":
            default:
                logger.info(loggingMessage.toString());
        }
        request.setAttribute("responseBody", loggingMessage);
        return loggingMessage;
    }
}
