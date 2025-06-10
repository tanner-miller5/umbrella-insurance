package com.umbrella.insurance.utils;

import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa.SentEmailService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.util.EmailUtil;
import com.umbrella.insurance.website.WebsiteApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailUtilTests {
    private static Connection connection;

    @Autowired
    private SentEmailService sentEmailService;
    @BeforeEach
    public void beforeEach() throws SQLException {
    }

    @AfterEach
    public void afterEach() throws SQLException {
    }
    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        connection = Database.createConnectionWithEnv(EnvironmentEnum.TEST);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        Database.closeConnection(connection);
    }

    @Test
    @Order(1)
    public void sendResetPasswordEmailTest() throws SQLException {
        EmailUtil emailUtil = new EmailUtil();
        boolean isSuccess = emailUtil.sendResetPasswordEmail(
                "randomtest1234567@icloud.com", "1234567890", sentEmailService);
    }
}
