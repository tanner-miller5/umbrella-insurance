package com.umbrella.insurance.core.models.groups.v1.db;

import com.umbrella.insurance.core.models.entities.Group;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.groups.v1.db.jpa.GroupService;
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
public class GroupsTableTests {
    private static String groupName = "1234";
    private static String updatedGroupName = "12345";
    private static Group group = new Group();
    private static Group updatedGroup = new Group();
    static {
        group.setGroupName(groupName);

        updatedGroup.setGroupName(updatedGroupName);
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

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.rollback(savepoint);
        Database.closeConnection(connection);
    }

    @Autowired
    private GroupService groupService;

    @Test
    @Order(2)
    void insertGroupTest() throws SQLException {
        group = groupService.saveGroup(group);
        updatedGroup.setId(group.getId());
    }
    @Test
    @Order(3)
    void selectGroupByGroupNameTest() throws SQLException {
        Group group1 = groupService.getGroupByGroupName(groupName).get();
        assertTrue(group1.getGroupName().equals(groupName));
    }
    @Test
    @Order(4)
    void updateGroupByGroupNameTest() throws SQLException {
        updatedGroup = groupService
                .updateGroup(updatedGroup);
    }
    @Test
    @Order(5)
    void selectUpdatedGroupByGroupNameTest() throws SQLException {
        Group group1 = groupService
                .getGroupByGroupName(updatedGroupName).get();
        assertTrue(group1.getGroupName().equals(updatedGroupName));
    }
    @Test
    @Order(6)
    void deleteGroupByGroupNameTest() throws SQLException {
            groupService
                    .deleteGroup(updatedGroup.getId());
    }
}
