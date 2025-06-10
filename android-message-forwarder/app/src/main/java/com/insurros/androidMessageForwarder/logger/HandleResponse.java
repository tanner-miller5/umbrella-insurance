package com.umbrella.insurance.androidMessageForwarder.logger;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class HandleResponse implements HttpClientResponseHandler {

    @Override
    public Object handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        System.out.println(response.getCode());
        System.out.println(EntityUtils.toString(response.getEntity()));
        return null;
    }
}
