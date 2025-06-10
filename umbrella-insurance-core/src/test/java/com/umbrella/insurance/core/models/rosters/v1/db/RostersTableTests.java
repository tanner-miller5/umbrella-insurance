package com.umbrella.insurance.core.models.rosters.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa.LevelOfCompetitionService;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.rewards.v1.db.jpa.RewardService;
import com.umbrella.insurance.core.models.rosters.v1.db.jpa.RosterService;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
import com.umbrella.insurance.core.models.teamMemberTypes.v1.db.jpa.TeamMemberTypeService;
import com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa.TeamTypeService;
import com.umbrella.insurance.core.models.teams.v1.db.jpa.TeamService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.umbrella.insurance.core.models.entities.TeamMemberType;
import com.umbrella.insurance.core.models.entities.Team;
import com.umbrella.insurance.core.models.entities.TeamType;
import com.umbrella.insurance.core.models.entities.Season;
import com.umbrella.insurance.core.models.entities.LevelOfCompetition;
import com.umbrella.insurance.core.models.entities.GameType;
import com.umbrella.insurance.core.models.entities.StreetAddress;
import com.umbrella.insurance.core.models.entities.City;
import com.umbrella.insurance.core.models.entities.State;
import com.umbrella.insurance.core.models.entities.ZipCode;
import com.umbrella.insurance.core.models.entities.Country;
import com.umbrella.insurance.core.models.entities.Location;
import com.umbrella.insurance.core.models.entities.Person;
import com.umbrella.insurance.core.models.entities.Roster;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RostersTableTests {

    private static String rosterName = "1";

    private static String updatedRosterName = "2";
    private static TeamMemberType teamMemberType = new TeamMemberType();
    private static Team team = new Team();
    private static TeamType teamType = new TeamType();
    private static Season season = new Season();
    private static Season firstSeason = new Season();
    private static Season lastSeason = new Season();
    private static LevelOfCompetition levelOfCompetition = new LevelOfCompetition();
    private static GameType gameType = new GameType();
    private static StreetAddress streetAddress = new StreetAddress();
    private static City city = new City();
    private static State state = new State();
    private static ZipCode zipCode = new ZipCode();
    private static Country country = new Country();
    private static Location location = new Location();
    private static Person person = new Person();
    private static Roster roster = new Roster();
    private static Roster updatedRoster = new Roster();
    static {
        roster.setRosterName(rosterName);

        teamMemberType.setTeamMemberTypeName("123");

        gameType.setGameTypeName("2");

        levelOfCompetition.setLevelOfCompetitionName("pro");

        firstSeason.setEndDate(Date.valueOf("1111-11-11"));
        firstSeason.setStartDate(Date.valueOf("2000-10-10"));
        firstSeason.setSeasonName("3");

        lastSeason.setEndDate(Date.valueOf("1115-11-11"));
        lastSeason.setStartDate(Date.valueOf("2001-10-10"));
        lastSeason.setSeasonName("4");

        season.setEndDate(Date.valueOf("1116-11-11"));
        season.setStartDate(Date.valueOf("2002-10-10"));
        season.setSeasonName("6");

        team.setLogoName("logo");
        team.setTeamName("team");
        team.setLogoImage(new byte[1]);

        streetAddress.setStreetAddressLine1("1");
        streetAddress.setStreetAddressLine2("2");
        city.setCityName("3");
        state.setStateName("4");
        zipCode.setZipCodeValue("5");
        country.setCountryName("6");
        location.setLocationName("name");

        teamType.setTeamTypeName("5");

        person.setDateOfBirth(Date.valueOf("1111-11-11"));
        person.setSsn("1");
        person.setSurname("2");
        person.setMiddleName("3");
        person.setFirstName("4");

        updatedRoster.setRosterName(updatedRosterName);

    }

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private TeamTypeService teamTypeService;

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
    private TeamMemberTypeService teamMemberTypeService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private LevelOfCompetitionService levelOfCompetitionService;

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private RosterService rosterService;

    @Autowired
    private PersonService personService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ZipCodeService zipCodeService;


    @Test
    @Order(2)
    void insertRosterTest() throws SQLException {
        teamMemberType = teamMemberTypeService.saveTeamMemberType(teamMemberType);
        roster.setTeamMemberType(teamMemberType);
        updatedRoster.setTeamMemberType(teamMemberType);

        person = personService.savePerson(person);
        roster.setPerson(person);
        updatedRoster.setPerson(person);

        gameType = gameTypeService.saveGameType(gameType);
        team.setGameType(gameType);

        levelOfCompetition = levelOfCompetitionService.saveLevelOfCompetition(levelOfCompetition);
        team.setLevelOfCompetition(levelOfCompetition);

        season = seasonService.saveSeason(season);
        roster.setSeason(season);
        updatedRoster.setSeason(season);

        firstSeason = seasonService.saveSeason(firstSeason);
        team.setFirstSeason(firstSeason);

        lastSeason = seasonService.saveSeason(lastSeason);
        team.setLastSeason(lastSeason);

        streetAddress = streetAddressService.saveStreetAddress(streetAddress);
        location.setStreetAddress(streetAddress);

        city = cityService.saveCity(city);
        location.setCity(city);

        state = stateService.saveState(state);
        location.setState(state);

        zipCode = zipCodeService.saveZipCode(zipCode);
        location.setZipCode(zipCode);

        country = countryService.saveCountry(country);
        location.setCountry(country);

        location = locationService.saveLocation(location);
        team.setLocation(location);

        teamType = teamTypeService.saveTeamType(teamType);
        team.setTeamType(teamType);

        team = teamService.saveTeam(team);
        roster.setTeam(team);
        updatedRoster.setTeam(team);


        roster = rosterService.saveRoster(roster);
        updatedRoster.setId(roster.getId());
    }
    @Test
    @Order(3)
    void selectRosterByRosterNameTest() throws SQLException {
        Roster roster1 = rosterService.getRosterByRosterName(rosterName).get();
        assertTrue(roster1.getRosterName().equals(rosterName));
        assertTrue(roster1.getSeason().getId().equals(season.getId()));
        assertTrue(roster1.getTeam().getId().equals(team.getId()));
        assertTrue(roster1.getPerson().getId().equals(person.getId()));
        assertTrue(roster1.getTeamMemberType().getId().equals(
                teamMemberType.getId()));
    }
    @Test
    @Order(4)
    void updateRosterByRosterNameTest() throws SQLException {
        updatedRoster = rosterService.updateRoster(
                updatedRoster);
    }
    @Test
    @Order(5)
    void selectUpdatedRosterByRosterNameTest() throws SQLException {
        Roster roster1 = rosterService.getRosterByRosterName(updatedRosterName).get();
        assertTrue(roster1.getRosterName().equals(updatedRosterName));
        assertTrue(roster1.getSeason().getId().equals(season.getId()));
        assertTrue(roster1.getTeam().getId().equals(team.getId()));
        assertTrue(roster1.getPerson().getId().equals(person.getId()));
        assertTrue(roster1.getTeamMemberType().getId().equals(teamMemberType.getId()));
    }
    @Test
    @Order(6)
    void deleteRosterByRosterNameTest() throws SQLException {
        rosterService.deleteRoster(updatedRoster.getId());

        teamMemberTypeService.deleteTeamMemberType(
                teamMemberType.getId());

        personService.deletePerson(person.getId());

        teamService.deleteTeam(
                team.getId());

        gameTypeService.deleteGameType(
                gameType.getId());

        levelOfCompetitionService.deleteLevelOfCompetition(
                levelOfCompetition.getId());

        streetAddressService.deleteStreetAddress(
                streetAddress.getId());

        cityService.deleteCity(
                city.getId());

        stateService.deleteState(
                state.getId());

        zipCodeService.deleteZipCode(
                zipCode.getId());

        countryService.deleteCountry(
                country.getId());

        locationService.deleteLocation(
                location.getId());

        seasonService.deleteSeason(
                season.getId());

        seasonService.deleteSeason(
                firstSeason.getId());

        seasonService.deleteSeason(
                lastSeason.getId());

        teamTypeService.deleteTeamType(
                teamType.getId());

    }

}
