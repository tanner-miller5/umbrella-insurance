/*
 * Copyright (c) 2022-2023  Umbrella Insurance Inc.
 */

package com.umbrella.insurance.core.constants;

/**
 * <p>Headers class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 1.47
 */
public class Headers {

    /**
     * Cookie description used for session cookie. Constant <code>COOKIE_DESCRIPTION="Session
     * Cookie"</code>
     */
    public static final String COOKIE_DESCRIPTION = "Session Cookie";

    /**
     * UTF8 encoding value. Constant <code>UTF8="UTF-8"</code>
     */
    public static final String UTF8 = "UTF-8";

    /**
     * The text/html mime type. Constant <code>MIME_TYPE_HTML="text/html"</code>
     */
    public static final String MIME_TYPE_HTML = "text/html";
    /**
     * Cookie header name. Constant <code>COOKIE="Cookie"</code>
     */
    public static final String COOKIE = "Cookie";
    /**
     * Cookie template used for localhost. Constant
     * <code>COOKIE_TEMPLATE_INSECURE="{{COOKIE_NAME}}={{COOKIE_VALUE}}; Path="{trunked}</code>
     */
    public static final String COOKIE_TEMPLATE_INSECURE =
        "{{COOKIE_NAME}}={{COOKIE_VALUE}}; Path=/;";
    /**
     * Security header. Constant <code>X_CONTENT_TYPE_OPTIONS="X-Content-Type-Options"</code>
     */
    public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    /**
     * Content type header name. Constant <code>CONTENT_TYPE="Content-Type"</code>
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /*
    HEADERS
     */
    /**
     * The content type for forms. Constant
     * <code>CONTENT_TYPE_FORM="application/x-www-form-urlencoded"</code>
     */
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    /**
     * Security header name. Constant
     * <code>STRICT_TRANSPORT_SECURITY="Strict-Transport-Security"</code>
     */
    public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    /**
     * Security header name. Constant
     * <code>CONTENT_SECURITY_POLICY="Content-Security-Policy"</code>
     */
    public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";
    /**
     * Security header name. Constant <code>X_FRAME_OPTIONS="X-Frame-Options"</code>
     */
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";
    /**
     * Security header value. Constant <code>X_CONTENT_TYPE_OPTIONS_VALUE="nosniff"</code>
     */
    public static final String X_CONTENT_TYPE_OPTIONS_VALUE = "nosniff";
    /**
     * Security header value. Constant <code>STRICT_TRANSPORT_SECURITY_VALUE="max-age:1800;
     * includeSubDomains"</code>
     */
    public static final String STRICT_TRANSPORT_SECURITY_VALUE = "max-age:1800; includeSubDomains";
    /**
     * Security header value. Constant <code>CONTENT_SECURITY_POLICY_VALUE="default-src 'self'
     * 'unsafe-inline'; upg"{trunked}</code>
     */
    public static final String CONTENT_SECURITY_POLICY_VALUE =
        "default-src 'self' 'unsafe-inline'; upgrade-insecure-requests;";
    /**
     * Security header value. Constant <code>X_FRAME_OPTIONS_VALUE="SAMEORIGIN"</code>
     */
    public static final String X_FRAME_OPTIONS_VALUE = "SAMEORIGIN";
    /**
     * Security value header. Constant <code>ORIGIN="Origin"</code>
     */
    public static final String ORIGIN = "Origin";
    /**
     * Security header name. Constant <code>REFERRER_POLICY="Referrer-Policy"</code>
     */
    public static final String REFERRER_POLICY = "Referrer-Policy";
    /**
     * Security header value. Constant <code>REFERRER_POLICY_VALUE="no-referrer"</code>
     */
    public static final String REFERRER_POLICY_VALUE = "no-referrer";
    /**
     * Security header name. Constant <code>X_XXS_PROTECTION="X-XSS-Protection"</code>
     */
    public static final String X_XXS_PROTECTION = "X-XSS-Protection";
    /**
     * Security header value. Constant <code>X_XXS_PROTECTION_VALUE="X-XSS-Protection: 1;
     * mode=block"</code>
     */
    public static final String X_XXS_PROTECTION_VALUE = "X-XSS-Protection: 1; mode=block";
    /**
     * Security header name. Constant <code>ALLOW="Access-Control-Allow-Methods"</code>
     */
    public static final String ALLOW = "Access-Control-Allow-Methods";
    /**
     * Security header name. Constant
     * <code>ACCESS_CONTROL_REQUEST_METHODS="Access-Control-Request-Method"</code>
     */
    public static final String ACCESS_CONTROL_REQUEST_METHODS = "Access-Control-Request-Method";
    /**
     * Security header name. Constant
     * <code>ACCESS_CONTROL_REQUEST_HEADERS="Access-Control-Request-Headers"</code>
     */
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    /**
     * Security header name. Constant <code>ACCEPT="Accept"</code>
     */
    public static final String ACCEPT = "Accept";
    /**
     * Security header value. Constant <code>ALLOW_VALUE="GET, PUT, POST, DELETE, HEAD"</code>
     */
    public static final String ALLOW_VALUE = "GET, PUT, POST, DELETE, HEAD";
    /**
     * Security header value. Constant <code>POST="POST"</code>
     */
    public static final String POST = "POST";
    /**
     * Cookie header name. Constant <code>SET_COOKIE="Set-Cookie"</code>
     */
    public static final String SET_COOKIE = "Set-Cookie";
    /**
     * Security header value. Constant <code>SAME_SITE="SameSite"</code>
     */
    public static final String SAME_SITE = "SameSite";
    /**
     * Security header value. Constant <code>STRICT="Strict"</code>
     */
    public static final String STRICT = "Strict";
    /**
     * Security header value. Constant <code>HTTP_ONLY="HttpOnly"</code>
     */
    public static final String HTTP_ONLY = "HttpOnly";

    /**
     * Used to signal all Http request methods are accepted. Constant <code>ALL="*"</code>
     */
    public static final String ALL = "*";
}
