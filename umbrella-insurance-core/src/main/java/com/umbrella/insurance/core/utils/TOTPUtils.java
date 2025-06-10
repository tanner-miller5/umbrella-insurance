package com.umbrella.insurance.core.utils;
/**
 Copyright (c) 2011 IETF Trust and the persons identified as
 authors of the code. All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, is permitted pursuant to, and subject to the license
 terms contained in, the Simplified BSD License set forth in Section
 4.c of the IETF Trust's Legal Provisions Relating to IETF Documents
 (http://trustee.ietf.org/license-info).
 */

import com.github.dockerjava.zerodep.shaded.org.apache.commons.codec.binary.Base32;

import java.lang.reflect.UndeclaredThrowableException;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.time.Instant;


/**
 * This is an example implementation of the OATH
 * TOTPUtils algorithm.
 * Visit www.openauthentication.org for more information.
 *
 * @author Johan Rydell, PortWise, Inc.
 */

public class TOTPUtils {
    private TOTPUtils() {}
    /**
     * This method uses the JCE to provide the crypto algorithm.
     * HMAC computes a Hashed Message Authentication Code with the
     * crypto hash algorithm as a parameter.
     *
     * @param crypto: the crypto algorithm (HmacSHA1, HmacSHA256,
     *                             HmacSHA512)
     * @param keyBytes: the bytes to use for the HMAC key
     * @param text: the message or text to be authenticated
     */
    private static byte[] hmac_sha(String crypto, byte[] keyBytes,
                                   byte[] text){
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey =
                    new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }
    /**
     * This method converts a HEX string to Byte[]
     *
     * @param hex: the HEX string
     *
     * @return: a byte array
     */
    private static byte[] hexStr2Bytes(String hex){
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex,16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++)
            ret[i] = bArray[i+1];
        return ret;
    }
    private static final int[] DIGITS_POWER
            // 0 1  2   3    4     5      6       7        8
            = {1,10,100,1000,10000,100000,1000000,10000000,100000000 };
    /**
     * This method generates a TOTPUtils value for the given
     * set of parameters.
     *
     * @param key: the shared secret, HEX encoded
     * @param time: a value that reflects a time
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes
     *              {@link String} digits
     */
    public static String generateTOTP(String key,
                                      String time,
                                      String returnDigits){
        return generateTOTP(key, time, returnDigits, "HmacSHA1");
    }
    /**
     * This method generates a TOTPUtils value for the given
     * set of parameters.
     *
     * @param key: the shared secret, HEX encoded
     * @param time: a value that reflects a time
     * @param returnDigits: number of digits to return
     * @param crypto: the crypto function to use
     *
     * @return: a numeric String in base 10 that includes
     *              {@link String} digits
     */
    public static String generateTOTP(String key,
                                      String time,
                                      String returnDigits,
                                      String crypto){
        int codeDigits = Integer.decode(returnDigits).intValue();
        String result = null;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16 )
            time = "0" + time;

        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);
        byte[] hash = hmac_sha(crypto, k, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary =
                ((hash[offset] & 0x7f) << 24) |
                        ((hash[offset + 1] & 0xff) << 16) |
                        ((hash[offset + 2] & 0xff) << 8) |
                        (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * This method generates a TOTPUtils value for the given
     * set of parameters.
     *
     * @param key: the shared secret, HEX encoded
     * @param time: a value that reflects a time
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes
     *              {@link String} digits
     */

    public static String generateTOTP256(String key,
                                         String time,
                                         String returnDigits){
        return generateTOTP(key, time, returnDigits, "HmacSHA256");
    }
    /**
     * This method generates a TOTPUtils value for the given
     * set of parameters.
     *
     * @param key: the shared secret, HEX encoded
     * @param time: a value that reflects a time
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes
     *              {@link String} digits
     */

    public static String generateTOTP512(String key,
                                         String time,
                                         String returnDigits){
        return generateTOTP(key, time, returnDigits, "HmacSHA512");
    }
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < bytes.length ; i++) {
            stringBuilder.append(byteToHex(bytes[i]));
        }
        return stringBuilder.toString();
    }
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static String createSeedUrl() {
        Base32 base32 = new Base32();
        byte[] randomBytes = new byte[20];
        for (int i = 0; i < 20; i++) {
            Integer x = getRandomNumber(0, 255);
            randomBytes[i] = x.byteValue();
        }
        String encodedRandomBytes = base32.encodeAsString(randomBytes);
        System.out.println("encodedRandomBytes:" + encodedRandomBytes);
        String randomEncodedBytes = bytesToHexString(encodedRandomBytes.getBytes());
        System.out.println("RandomSeed:" + randomEncodedBytes);

        //byte[] seedAsBytes = base32.decode("AECQIBADA3PK3PXP".getBytes());
        //byte[] seedAsBytes = base32.decode("7LWXP52BRBB2CGTCKPGFXV7CMFPAM6AG".getBytes());
        //byte[] seedAsBytes = base32.decode("MR7AXQVZAMQRZAP2PZJ6FCDDTCEPLBWV".getBytes());
        String totpTemplate = "otpauth://totp/Umbrella-Insurance:{FIRST_NAME}%20{LAST_NAME}?issuer=Umbrella-Insurance&secret={SECRET}&algorithm=SHA1&digit=6&period=30";
        String totpUri = totpTemplate.replace("{SECRET}", encodedRandomBytes);
        return totpUri;
    }

    public static boolean verifyOTP(String seedUrl, String userOtp) {
        String serverOtp = generateOTP(seedUrl);
        return serverOtp.equals(userOtp);
    }

    public static String generateOTP(String seedUrl) {
        Base32 base32 = new Base32();
        String[] appUrlArray = seedUrl.split("&");
        String encodedSecret = "";
        for(int i = 0; i < appUrlArray.length; i++) {
            if(appUrlArray[i].startsWith("secret")) {
                String[] secretArray = appUrlArray[i].split("=");
                encodedSecret = secretArray[i];
            }
        }

        byte[] seedAsBytes = base32.decode(encodedSecret.getBytes());
        String seed = bytesToHexString(seedAsBytes);
        long current = Instant.now().getEpochSecond();
        long T0 = 0;
        long X = 30;
        long T = (current - T0)/X;
        String steps = Long.toHexString(T).toUpperCase();

        String otp = generateTOTP(seed, steps, "6", "HmacSHA1");
        return otp;
    }
}
