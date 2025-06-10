package com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db;

import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa.ApplicationPrivilegeService;
import com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.jpa.ApplicationRoleApplicationPrivilegeRelationshipService;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.entities.ApplicationPrivilege;
import com.umbrella.insurance.core.models.entities.ApplicationRole;
import com.umbrella.insurance.core.models.entities.ApplicationRoleApplicationPrivilegeRelationship;
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
public class ApplicationRoleApplicationPrivilegeRelationshipsTableTests {
    private static ApplicationRole applicationRole =
            new ApplicationRole();
    private static ApplicationPrivilege applicationPrivilege =
            new ApplicationPrivilege();
    private static ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship =
            new ApplicationRoleApplicationPrivilegeRelationship();
    private static ApplicationRoleApplicationPrivilegeRelationship updatedApplicationRoleApplicationPrivilegeRelationship =
            new ApplicationRoleApplicationPrivilegeRelationship();

    static {
        applicationRole.setApplicationRoleName("customer1");

        applicationPrivilege.setApplicationPrivilegeName("createUsers");
        applicationPrivilege.setPath("1");
        applicationPrivilege.setAccess("");
        applicationPrivilege.setMethod("POST");
    }

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

    @Autowired
    ApplicationRoleService applicationRoleService;

    @Autowired
    ApplicationPrivilegeService applicationPrivilegeService;

    @Autowired
    ApplicationRoleApplicationPrivilegeRelationshipService applicationRoleApplicationPrivilegeRelationshipService;

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Test
    @Order(4)
    void insertApplicationRoleApplicationPrivilegeRelationshipTest() throws SQLException {
        applicationRole = applicationRoleService.saveApplicationRole(
                applicationRole);
        applicationRoleApplicationPrivilegeRelationship.setApplicationRole(
                applicationRole);
        updatedApplicationRoleApplicationPrivilegeRelationship.setApplicationRole(
                applicationRole);

        applicationPrivilege = applicationPrivilegeService.saveApplicationPrivilege(
                applicationPrivilege);
        applicationRoleApplicationPrivilegeRelationship.setApplicationPrivilege(
                applicationPrivilege);
        updatedApplicationRoleApplicationPrivilegeRelationship.setApplicationPrivilege(
                applicationPrivilege);

        applicationRoleApplicationPrivilegeRelationship = applicationRoleApplicationPrivilegeRelationshipService
                .saveApplicationRoleApplicationPrivilegeRelationship(
                applicationRoleApplicationPrivilegeRelationship);
        updatedApplicationRoleApplicationPrivilegeRelationship.setId(applicationRoleApplicationPrivilegeRelationship.getId());
    }
    @Test
    @Order(5)
    void selectApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeIdTest() throws SQLException {
        ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship1 =
                applicationRoleApplicationPrivilegeRelationshipService
                        .findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeId(
                        applicationRole.getId(),
                        applicationPrivilege.getId()).get();
        assertTrue(applicationRoleApplicationPrivilegeRelationship1
                .getApplicationRole().getId().equals(applicationRole.getId()));
        assertTrue(applicationRoleApplicationPrivilegeRelationship1
                .getApplicationPrivilege().getId().equals(applicationPrivilege.getId()));
    }
    @Test
    @Order(6)
    void updateApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeIdTest() throws SQLException {
        updatedApplicationRoleApplicationPrivilegeRelationship = applicationRoleApplicationPrivilegeRelationshipService
                        .updateApplicationRoleApplicationPrivilegeRelationship(
                                updatedApplicationRoleApplicationPrivilegeRelationship);
    }
    @Test
    @Order(7)
    void selectUpdatedApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeIdTest() throws SQLException {
        ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship1 =
                applicationRoleApplicationPrivilegeRelationshipService
                        .findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeId(
                                applicationRole.getId(),
                                applicationPrivilege.getId()).get();
        assertTrue(applicationRoleApplicationPrivilegeRelationship1
                .getApplicationRole().getId().equals(applicationRole.getId()));
        assertTrue(applicationRoleApplicationPrivilegeRelationship1
                .getApplicationPrivilege().getId().equals(applicationPrivilege.getId()));
    }
    @Test
    @Order(8)
    void deleteApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeIdTest() throws SQLException {
        applicationRoleApplicationPrivilegeRelationshipService
                .deleteApplicationRoleApplicationPrivilegeRelationship(
                        updatedApplicationRoleApplicationPrivilegeRelationship.getId());

        applicationRoleService.deleteApplicationRole(applicationRole.getId());

        applicationPrivilegeService.deleteApplicationPrivilege(applicationPrivilege.getId());
    }

}
