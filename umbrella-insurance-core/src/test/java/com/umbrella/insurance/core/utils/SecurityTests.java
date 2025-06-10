package com.umbrella.insurance.core.utils;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static com.umbrella.insurance.core.utils.Security.generatePassword;


public class SecurityTests {
    @Test
    public void generatePasswordTest() {
        String password = generatePassword(
                10, "88f2b3188c927f94553dd8f94794f3ce".getBytes(StandardCharsets.UTF_8));
        System.out.println(password);
    }


}
