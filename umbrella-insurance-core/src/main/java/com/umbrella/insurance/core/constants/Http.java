/*
 * Copyright (c) 2022-2023  Umbrella Insurance Inc.
 */

package com.umbrella.insurance.core.constants;

/**
 * <p>Http class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 1.47
 */
public class Http {

    /**
     * Timeout used in session cookie. Constant <code>SESSION_TIMEOUT=1800</code>
     */
    public static final long SESSION_TIMEOUT = 1800;
    /**
     * http protocol used for localhost. Constant <code>Http="http://"</code>
     */
    public static final String HTTP = "http://";
    /**
     * https protocol. Constant <code>HTTPS="https://"</code>
     */
    public static final String HTTPS = "https://";
    /**
     * Used in urls. Constant <code>ROOT="/"</code>
     */
    public static final String ROOT = "/";
    /**
     * Used to get the hostname. Constant <code>HOSTNAME_REGEX="/[^/]*$"</code>
     */
    public static final String HOSTNAME_REGEX = "/[^/]*$";
    /**
     * Server error status code. Constant <code>ERROR_STATUS_CODE=500</code>
     */
    public static final int ERROR_STATUS_CODE = 500;
    /**
     * Server success status code. Constant <code>SUCCESS_STATUS_CODE=200</code>
     */
    public static final int SUCCESS_STATUS_CODE = 200;
    /**
     * How long request wait before timing out. Constant <code>REQUEST_TIMEOUT=10000</code>
     */
    public static int REQUEST_TIMEOUT = 10000;

}
