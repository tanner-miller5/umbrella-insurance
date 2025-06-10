/*
 * Copyright (c) 2022  Umbrella Insurance
 */

package com.umbrella.insurance.util;

import java.math.BigInteger;
import java.util.Random;

/**
 * <p>Verification class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 0.0.19
 */
public class Verification {

    /**
     * <p>generateVerificationCode.</p>
     *
     * @return a int
     */
    public static int generateVerificationCode() {
        int min = 100000;
        int max = 999999;
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static String generateVerificationCodeString() {
        int min = 100000;
        int max = 999999;
        int randomInt = (int) (Math.random() * (max - min + 1) + min);
        return String.valueOf(randomInt);
    }

    /**
     * <p>generateSessionId.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public static String generateSessionId() {
        return (new BigInteger(64, new Random())).toString();
    }
}
