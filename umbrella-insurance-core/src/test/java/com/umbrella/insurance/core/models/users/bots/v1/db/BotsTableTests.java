package com.umbrella.insurance.core.models.users.bots.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa.SpecialtyService;
import com.umbrella.insurance.core.models.people.analysts.v1.db.jpa.AnalystService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.users.bots.v1.db.jpa.BotService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BotsTableTests {
    private static String botName = "123456";
    private static Timestamp createdDateTime = Timestamp.valueOf("2022-11-11 12:12:12");
    private static Boolean isDeleted = true;
    private static Boolean isDisabled = true;
    private static Timestamp deletedDateTime = Timestamp.valueOf("2022-11-11 12:12:12");
    private static Double amountFunded = 3.0;
    private static String updatedBotName = "12345";
    private static Timestamp updatedCreatedDateTime = Timestamp.valueOf("2018-10-11 12:59:12");
    private static Boolean updatedIsDeleted = false;
    private static Boolean updatedIsDisabled = false;
    private static Timestamp updatedDeletedDateTime = Timestamp.valueOf("2012-11-01 12:12:12");
    private static Double updatedAmountFunded = 1000.00;
    private static String emailAddress = "1";
    private static String phoneNumber = "2";
    private static String username = "3";
    private static Boolean isPhoneNumberVerified = false;
    private static Boolean isEmailAddressVerified = false;
    private static Boolean isAuthAppVerified = false;
    private static String unitName = "1234e";
    private static Unit unit = new Unit();
    private static Specialty specialty = new Specialty();
    private static Person person = new Person();
    private static Analyst analyst = new Analyst();
    private static User user = new User();
    private static Bot bot = new Bot();
    private static Bot updatedBot = new Bot();
    static {
        unit.setUnitName(unitName);

        specialty.setSpecialtyName("test");

        person.setSsn("123");
        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSurname("last");
        person.setMiddleName("middle");
        person.setFirstName("first");

        user.setCreatedDateTime(createdDateTime);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setIsEmailAddressVerified(isEmailAddressVerified);
        user.setIsPhoneNumberVerified(isPhoneNumberVerified);
        user.setIsAuthAppVerified(isAuthAppVerified);

        bot.setBotName(botName);
        bot.setCreatedDateTime(createdDateTime);
        bot.setIsDeleted(isDeleted);
        bot.setIsDisabled(isDisabled);
        bot.setDeletedDateTime(deletedDateTime);
        bot.setAmountFunded(amountFunded);

        updatedBot.setBotName(updatedBotName);
        updatedBot.setCreatedDateTime(updatedCreatedDateTime);
        updatedBot.setIsDeleted(updatedIsDeleted);
        updatedBot.setIsDisabled(updatedIsDisabled);
        updatedBot.setDeletedDateTime(updatedDeletedDateTime);
        updatedBot.setAmountFunded(updatedAmountFunded);
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
    private BotService botService;

    @Autowired
    private PersonService personService;

    @Autowired
    private AnalystService analystService;

    @Autowired
    private UserService userService;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private UnitService unitService;

    @Test
    @Order(2)
    void insertBotTest() throws SQLException {
        person = personService.savePerson(person);
        user.setPerson(person);
        analyst.setPerson(person);

        user = userService.saveUser(user);
        bot.setUser(user);
        updatedBot.setUser(user);

        specialty = specialtyService.saveSpecialty(
                specialty);
        specialty = specialtyService.saveSpecialty(specialty);

        analyst = analystService.saveAnalyst(analyst);
        bot.setAnalyst(analyst);
        updatedBot.setAnalyst(analyst);

        unit = unitService.saveUnit(unit);
        bot.setUnit(unit);
        updatedBot.setUnit(unit);

        bot = botService.saveBot(bot);
        updatedBot.setId(bot.getId());
    }
    @Test
    @Order(3)
    void selectBotByBotNameTest() throws SQLException {
        Bot bot1 = botService.getBotByBotName(botName).get();
        assertTrue(bot1.getBotName().equals(botName));
        assertTrue(bot1.getUser().getId().equals(bot.getUser().getId()));
        assertTrue(bot1.getAnalyst().getId().equals(bot.getAnalyst().getId()));
        assertTrue(bot1.getCreatedDateTime().equals(createdDateTime));
        assertTrue(bot1.getIsDeleted().equals(isDeleted));
        assertTrue(bot1.getIsDisabled().equals(isDisabled));
        assertTrue(bot1.getDeletedDateTime().equals(deletedDateTime));
        assertTrue(bot1.getAmountFunded().equals(amountFunded));
        assertTrue(bot1.getUnit().getId().equals(bot.getUnit().getId()));
    }
    @Test
    @Order(4)
    void updateBotByBotNameTest() throws SQLException {
        updatedBot = botService.updateBot(updatedBot);
    }
    @Test
    @Order(5)
    void selectUpdatedBotByBotNameTest() throws SQLException {
        Bot bot1 = botService.getBotByBotName(updatedBotName).get();
        assertTrue(bot1.getBotName().equals(updatedBotName));
        assertTrue(bot1.getUser().getId().equals(updatedBot.getUser().getId()));
        assertTrue(bot1.getAnalyst().getId().equals(updatedBot.getAnalyst().getId()));
        assertTrue(bot1.getCreatedDateTime().equals(updatedCreatedDateTime));
        assertTrue(bot1.getIsDeleted().equals(updatedIsDeleted));
        assertTrue(bot1.getIsDisabled().equals(updatedIsDisabled));
        assertTrue(bot1.getDeletedDateTime().equals(updatedDeletedDateTime));
        assertTrue(bot1.getAmountFunded().equals(updatedAmountFunded));
        assertTrue(bot1.getUnit().getId().equals(updatedBot.getUnit().getId()));
    }
    @Test
    @Order(6)
    void deleteBotByBotNameTest() throws SQLException {
        botService.deleteBot(updatedBot.getId());

        userService.deleteUser(user.getId());

        analystService.deleteAnalyst(analyst.getId());

        personService.deletePerson(person.getId());

        specialtyService.deleteSpecialty(
                specialty.getId());

        unitService.deleteUnit(unit.getId());
    }

}
