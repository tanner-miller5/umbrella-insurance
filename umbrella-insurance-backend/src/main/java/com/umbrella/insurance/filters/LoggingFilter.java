package com.umbrella.insurance.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private static BigInteger requestNumber = BigInteger.valueOf(0);

    private static Boolean isDebug = true;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        LocalDateTime startLocalDateTime = LocalDateTime.now();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        BigInteger currentRequestNumber = new BigInteger(requestNumber.toString());
        requestNumber = requestNumber.add(BigInteger.ONE);
        MDC.put("currentRequestNumber", currentRequestNumber.toString());
        MDC.put("method", req.getMethod());
        MDC.put("url", req.getRequestURI());
        String session = req.getHeader("session");
        String userAgent = req.getHeader("User-Agent");
        String ip = req.getRemoteAddr();
        String queryString = req.getQueryString();
        String[] queryParams = null;
        Map<String, String> queryMap = new HashMap();
        if(queryString != null) {
            queryParams = queryString.split("&");
            for(int i = 0; i < queryParams.length; i++) {
                String[] query = queryParams[i].split("=");
                if(query[1] != null) {
                    queryMap.put(query[0], query[1]);
                    MDC.put(query[0], query[1]);
                }
            }
        }
        String envVal = queryMap.get("env");
        if(envVal != null) {
            EnvironmentEnum env = EnvironmentEnum.valueOf(envVal);
            MDC.put("env", env.name());
        }

        MDC.put("session", session == null || session.isEmpty() ? "undefined" : session);
        MDC.put("userAgent", userAgent == null || userAgent.isEmpty() ? "undefined" : userAgent);
        MDC.put("ip", ip == null || ip.isEmpty() ? "undefined" : ip);
        req.setAttribute("currentRequestNumber", currentRequestNumber);
        req.setAttribute("userAgent", MDC.get("userAgent"));
        req.setAttribute("session", MDC.get("session"));
        chain.doFilter(request, response);
        MDC.put("contentType", res.getContentType());
        MDC.put("responseStatus", String.valueOf(res.getStatus()));
        logger.info(
                "Logging Request Number: {}, method: {}, requestURI: {}, queryString: {}", currentRequestNumber, req.getMethod(),
                req.getRequestURI(), req.getQueryString());
        if (isDebug) {
            logger.info("Logging Request Number: {}, requestBody:{}", currentRequestNumber, req.getAttribute("requestBody"));
        }
        LocalDateTime endLocalDateTime = LocalDateTime.now();
        Duration d = Duration.between(startLocalDateTime, endLocalDateTime);
        long seconds = d.toSeconds();
        if(res.getStatus() < 300) {
            logger.info(
                    "Logging Response Number: {}, content type: {}, response time: {} seconds, status code: {}",
                    currentRequestNumber, res.getContentType(), seconds, res.getStatus());
            if (isDebug) {
                logger.info("Logging Response Number: {}, responseBody:{}", currentRequestNumber, req.getAttribute("responseBody"));
            }
        } else {
            logger.error(
                    "Logging Response Number: {}, content type: {}, response time: {} seconds, status code: {}",
                    currentRequestNumber, res.getContentType(), seconds, res.getStatus());
            if (isDebug) {
                logger.error("Logging Response Number: {}, responseBody:{}", currentRequestNumber, req.getAttribute("responseBody"));
            }
        }
        MDC.clear();
    }
}