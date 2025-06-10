package com.umbrella.insurance.core.models.applicationRoles.v1.db;

import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.entities.ApplicationRole;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationRolesTableTests {
    private static String applicationRoleName = "1234";
    private static String updatedApplicationRoleName = "12345";
    private static ApplicationRole applicationRole = new ApplicationRole();
    private static ApplicationRole updatedApplicationRole = new ApplicationRole();
    static {
        applicationRole.setApplicationRoleName(applicationRoleName);

        updatedApplicationRole.setApplicationRoleName(updatedApplicationRoleName);
    }

    private static Connection connection;
    private static Savepoint savepoint;
    @BeforeEach
    public void beforeEach() throws SQLException {

    }

    @AfterEach
    public void afterEach() throws SQLException {
    }

    @Autowired
    ApplicationRoleService applicationRoleService;

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

    @Test
    @Order(2)
    void insertApplicationRoleTest() throws SQLException {
        applicationRole = applicationRoleService
                .saveApplicationRole(applicationRole);
        updatedApplicationRole.setId(applicationRole.getId());
    }
    @Test
    @Order(3)
    void selectApplicationRoleByApplicationRoleNameTest() throws SQLException {
        ApplicationRole applicationRole1 = applicationRoleService.getApplicationRoleByApplicationRoleName(
                applicationRoleName).get();
        assertTrue(applicationRole1.getApplicationRoleName().equals(applicationRoleName));
    }
    @Test
    @Order(4)
    void updateApplicationRoleByApplicationRoleNameTest() throws SQLException {
        applicationRoleService.updateApplicationRole(
                updatedApplicationRole);
    }
    @Test
    @Order(5)
    void selectUpdatedApplicationRoleByApplicationRoleNameTest() throws SQLException {
        ApplicationRole applicationRole1 = applicationRoleService.getApplicationRoleByApplicationRoleName(
                updatedApplicationRoleName).get();
        assertTrue(applicationRole1.getApplicationRoleName().equals(updatedApplicationRoleName));
    }
    @Test
    @Order(6)
    void deleteApplicationRoleByApplicationRoleNameTest() throws SQLException {
        applicationRoleService
                .deleteApplicationRole(updatedApplicationRole.getId());

    }


}
