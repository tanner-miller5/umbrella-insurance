package com.umbrella.insurance.core.utils;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Totp;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.totps.v1.db.jpa.TotpService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.github.dockerjava.zerodep.shaded.org.apache.commons.codec.binary.Base32;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

import static com.umbrella.insurance.core.utils.TOTPUtils.bytesToHexString;
import static com.umbrella.insurance.core.utils.TOTPUtils.generateTOTP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TotpUtilsTests {
    private static Connection connection;
    private static Savepoint savepoint;
    @BeforeEach
    public void beforeEach() throws SQLException {

    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();

    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private TotpService totpService;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    @Test
    void testTOTP() throws NoSuchAlgorithmException, InvalidKeyException {

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
        System.out.println(totpUri);

        // Seed for HMAC-SHA1 - 20 bytes
        //String seed = bytesToHexString(seedAsBytes);
        String seed = bytesToHexString(randomBytes);
        System.out.println("seed:" + seed);
        long T0 = 0;
        long X = 30;
        long current = Instant.now().getEpochSecond();
        long testTime[] = {current, 59L, 1111111109L, 1111111111L,
                1234567890L, 2000000000L, 20000000000L};

        String steps = "0";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            System.out.println(
                    "+---------------+-----------------------+" +
                            "------------------+--------+--------+");
            System.out.println(
                    "|  Time(sec)    |   Time (UTC format)   " +
                            "| Value of T(Hex)  |  TOTPUtils  | Mode   |");
            System.out.println(
                    "+---------------+-----------------------+" +
                            "------------------+--------+--------+");

            for (int i=0; i<testTime.length; i++) {
                long T = (testTime[i] - T0)/X;
                steps = Long.toHexString(T).toUpperCase();
                while (steps.length() < 16) steps = "0" + steps;
                String fmtTime = String.format("%1$-11s", testTime[i]);
                String utcTime = df.format(new Date(testTime[i]*1000));
                System.out.print("|  " + fmtTime + "  |  " + utcTime +
                        "  | " + steps + " |");
                System.out.println(generateTOTP(seed, steps, "6",
                        "HmacSHA1") + "| SHA1   |");

                System.out.println(
                        "+---------------+-----------------------+" +
                                "------------------+--------+--------+");
            }
        }catch (final Exception e){
            System.out.println("Error : " + e);
        }
    }


    //@Test
    void testUserTOTP() throws NoSuchAlgorithmException, InvalidKeyException, SQLException {
        User user = userService.getUserByEmailAddress("randomtest1234567@icloud.com").get();
        Totp totp = totpService.getTotpByUserId(user.getId()).get();

        Base32 base32 = new Base32();
        String[] appUrlArray = totp.getTotpCode().split("&");
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
        System.out.println(otp);
    }
}
