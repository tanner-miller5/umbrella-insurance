package com.umbrella.insurance.androidMessageForwarder.logger;

import org.json.JSONException;
import org.json.JSONObject;

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
    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("appName", getAppName());
            jsonObject.put("logLevel", getLogLevel());
            jsonObject.put("loggingPayload", getLoggingPayload());
            jsonObject.put("callingLoggerName", getCallingLoggerName());
            jsonObject.put("callingMethod", getCallingMethod());
            jsonObject.put("longitude", getLongitude());
            jsonObject.put("latitude", getLatitude());
            jsonObject.put("accuracy", getAccuracy());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }
}