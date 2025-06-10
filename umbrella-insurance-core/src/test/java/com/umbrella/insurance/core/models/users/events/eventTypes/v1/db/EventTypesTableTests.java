package com.umbrella.insurance.core.models.users.events.eventTypes.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.EventType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.jpa.EventTypeService;
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
public class EventTypesTableTests {
    private static String eventTypeName = "1234";
    private static String updatedEventTypeName = "12345";
    private static EventType eventType = new EventType();
    private static EventType updatedEventType = new EventType();
    static {
        eventType.setEventTypeName(eventTypeName);

        updatedEventType.setEventTypeName(updatedEventTypeName);
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
    private EventTypeService eventTypeService;

    @Test
    @Order(2)
    void insertEventTypeTest() throws SQLException {
        eventType = eventTypeService.saveEventType(eventType);
        updatedEventType.setId(eventType.getId());
    }
    @Test
    @Order(3)
    void selectEventTypeByEventTypeNameTest() throws SQLException {
        EventType eventType1 = eventTypeService
                .getEventTypeByEventTypeName(eventTypeName).get();
        assertTrue(eventType1.getEventTypeName().equals(eventTypeName));
    }
    @Test
    @Order(4)
    void updateEventTypeByEventTypeNameTest() throws SQLException {
        updatedEventType = eventTypeService.updateEventType(updatedEventType);
    }
    @Test
    @Order(5)
    void selectUpdatedEventTypeByEventTypeNameTest() throws SQLException {
        EventType eventType1 = eventTypeService
                .getEventTypeByEventTypeName(updatedEventTypeName).get();
        assertTrue(eventType1.getEventTypeName().equals(updatedEventTypeName));
    }
    @Test
    @Order(6)
    void deleteEventTypeByEventTypeNameTest() throws SQLException {
        eventTypeService.deleteEventType(updatedEventType.getId());
    }

}
