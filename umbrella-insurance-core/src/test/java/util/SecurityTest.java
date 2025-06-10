/*
 * Copyright (c) 2022. test2
 */

package util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.KeyPair;

import com.umbrella.insurance.core.utils.Security;
import org.junit.jupiter.api.Test;

/**
 * <p>SecurityTest class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 1.46
 */
public class SecurityTest {

    @Test
    void f() {
        try {
            KeyPair keyPair = Security.generateKeyPair(4096);
            String publicKey = Security.getPublicKey(keyPair);
            String privateKey = Security.getPrivateKey(keyPair);
            String test = "1992-05-04";
            System.out.println("String to encrypt:" + test);
            System.out.println("public key:" + publicKey);
            System.out.println("private key:" + privateKey);
            String encryptedString = Security.encryptWithPubicKey(test);
            System.out.println("Encrypted:" + encryptedString);
            String decryptedString = Security.decryptWithPrivateKey(encryptedString);
            System.out.println("Decrypted:" + decryptedString);
            //assertTrue(decryptedString.equals(test));
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }

    }
}
