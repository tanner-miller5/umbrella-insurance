package com.umbrella.insurance.androidMessageForwarder.logger;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RemoteLogger {
    public static void callRemoteLogger(LoggingMessage loggingMessage) {
        final HttpPost httpPost = new HttpPost("https://backend-app.umbrella-insurance.dev/rest/logging/v1?env=PROD");
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("Content-Type", "application/json"));
        //System.out.println(loggingMessage.to());
        try {
            HttpEntity entity = new ByteArrayEntity(loggingMessage.toJSON().getBytes("UTF-8"), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = (CloseableHttpResponse) client
                     .execute(httpPost, new HandleResponse())) {

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

