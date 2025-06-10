package com.umbrella.insurance.core.models.applicationPrivileges.v1.db;

import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa.ApplicationPrivilegeService;
import com.umbrella.insurance.core.models.entities.ApplicationPrivilege;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationPrivilegesTableTests {
    private static String applicationPrivilegeName = "1234";
    private static String path = "1";
    private static String method = "2";
    private static String access = "3";
    private static String updatedApplicationPrivilegeName = "12345";
    private static String updatedPath = "4";
    private static String updatedMethod = "5";
    private static String updatedAccess = "6";
    private static ApplicationPrivilege applicationPrivilege = new ApplicationPrivilege();
    private static ApplicationPrivilege updatedApplicationPrivilege = new ApplicationPrivilege();
    static {
        applicationPrivilege.setApplicationPrivilegeName(applicationPrivilegeName);
        applicationPrivilege.setPath(path);
        applicationPrivilege.setMethod(method);
        applicationPrivilege.setAccess(access);

        updatedApplicationPrivilege.setApplicationPrivilegeName(updatedApplicationPrivilegeName);
        updatedApplicationPrivilege.setPath(updatedPath);
        updatedApplicationPrivilege.setMethod(updatedMethod);
        updatedApplicationPrivilege.setAccess(updatedAccess);
    }

    @Autowired
    ApplicationPrivilegeService applicationPrivilegeService;

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

    @Test
    @Order(2)
    void insertApplicationPrivilegeTest() throws SQLException {
        applicationPrivilege = applicationPrivilegeService.saveApplicationPrivilege(
                applicationPrivilege);
        updatedApplicationPrivilege.setId(applicationPrivilege.getId());
    }
    @Test
    @Order(3)
    void selectApplicationPrivilegeByApplicationPrivilegeNameTest() throws SQLException {
        ApplicationPrivilege applicationPrivilege1 = applicationPrivilegeService
                .findApplicationPrivilegeByApplicationPrivilegeName(
                applicationPrivilegeName).get();
        assertTrue(applicationPrivilege1.getApplicationPrivilegeName().equals(applicationPrivilegeName));
        assertTrue(applicationPrivilege1.getPath().equals(path));
        assertTrue(applicationPrivilege1.getMethod().equals(method));
        assertTrue(applicationPrivilege1.getAccess().equals(access));
    }
    @Test
    @Order(4)
    void updateApplicationPrivilegeByApplicationPrivilegeNameTest() throws SQLException {
        updatedApplicationPrivilege = applicationPrivilegeService.updateApplicationPrivilege(
                updatedApplicationPrivilege);

    }
    @Test
    @Order(5)
    void selectUpdatedApplicationPrivilegeByApplicationPrivilegeNameTest() throws SQLException {
        ApplicationPrivilege applicationPrivilege1 = applicationPrivilegeService
                .findApplicationPrivilegeByApplicationPrivilegeName(
                updatedApplicationPrivilegeName).get();
        assertTrue(applicationPrivilege1.getApplicationPrivilegeName().equals(updatedApplicationPrivilegeName));
        assertTrue(applicationPrivilege1.getPath().equals(updatedPath));
        assertTrue(applicationPrivilege1.getMethod().equals(updatedMethod));
        assertTrue(applicationPrivilege1.getAccess().equals(updatedAccess));
    }
    @Test
    @Order(6)
    void deleteApplicationPrivilegeByApplicationPrivilegeNameTest() throws SQLException {
        applicationPrivilegeService
                .deleteApplicationPrivilege(
                        updatedApplicationPrivilege.getId());
    }

    @Test
    @Order(8)
    void jpaTest() {

        try {
            List<Long> applicationPrivilegeIds = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                ApplicationPrivilege applicationPrivilege = new ApplicationPrivilege();
                applicationPrivilege.setApplicationPrivilegeName("ApplicationPrivilege " + i); // Setting unique names for each applicationPrivilege
                applicationPrivilege.setAccess("" + 50000 + (i * 1000)); //  applicationPrivilege_data for each applicationPrivilege
                applicationPrivilege.setMethod("applicationPrivilegeCreateDateTime");
                applicationPrivilege.setPath("12234234fds");
                applicationPrivilege = applicationPrivilegeService.saveApplicationPrivilege(applicationPrivilege);
                applicationPrivilegeIds.add(applicationPrivilege.getId());
                System.out.println(applicationPrivilege.getApplicationPrivilegeName() + ":" + applicationPrivilege.getId());
            }

            // Read Operation: Print all applicationPrivileges
            List<ApplicationPrivilege> allApplicationPrivileges = applicationPrivilegeService.getApplicationPrivileges();
            System.out.println("All ApplicationPrivileges:");
            for (ApplicationPrivilege applicationPrivilege : allApplicationPrivileges) {
                System.out.println(applicationPrivilege.getApplicationPrivilegeName() + " - " + applicationPrivilege.getAccess());
            }

            Optional<ApplicationPrivilege> applicationPrivilegeByName = applicationPrivilegeService
                    .findApplicationPrivilegeByApplicationPrivilegeName("ApplicationPrivilege 1");
            System.out.println("applicationPrivilegeByName:" + applicationPrivilegeByName.get().getApplicationPrivilegeName());

            // Update Operation: Update two applicationPrivileges
            for(Long id : applicationPrivilegeIds) {
                Optional<ApplicationPrivilege> applicationPrivilege = applicationPrivilegeService.findApplicationPrivilegeById(id);
                applicationPrivilege.get().setAccess(applicationPrivilege.get().getAccess() + 5000);
                applicationPrivilegeService.updateApplicationPrivilege(applicationPrivilege.get());
            }



            // Read Operation: Print updated applicationPrivileges
            List<ApplicationPrivilege> updatedApplicationPrivileges = applicationPrivilegeService.findApplicationPrivilegeByIds(applicationPrivilegeIds);
            System.out.println("\nUpdated ApplicationPrivileges:");
            for (ApplicationPrivilege applicationPrivilege : updatedApplicationPrivileges) {
                System.out.println(applicationPrivilege.getApplicationPrivilegeName() + " - " + applicationPrivilege.getAccess());
            }

            // Delete Operation
            for (ApplicationPrivilege applicationPrivilege : updatedApplicationPrivileges) {
                applicationPrivilegeService.deleteApplicationPrivilege(applicationPrivilege.getId());
            }
        } finally {
        }
    }
}
