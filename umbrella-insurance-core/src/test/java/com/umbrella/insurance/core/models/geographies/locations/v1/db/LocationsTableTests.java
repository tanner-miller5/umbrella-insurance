package com.umbrella.insurance.core.models.geographies.locations.v1.db;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa.StreetAddressService;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa.ZipCodeService;
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
public class LocationsTableTests {
    private static String locationName = "1234";
    private static String updatedLocationName = "12345";

    private static StreetAddress streetAddress = new StreetAddress();
    private static City city = new City();
    private static State state = new State();
    private static ZipCode zipCode = new ZipCode();
    private static Country country = new Country();
    private static Location location = new Location();
    private static Location updatedLocation = new Location();
    static {
        streetAddress.setStreetAddressLine1("1");
        streetAddress.setStreetAddressLine2("2");
        city.setCityName("3");
        state.setStateName("4");
        zipCode.setZipCodeValue("5");
        country.setCountryName("6");

        location.setLocationName(locationName);

        updatedLocation.setLocationName(updatedLocationName);
    }

    @Autowired
    private StreetAddressService streetAddressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private ZipCodeService zipCodeService;

    @Autowired
    private CountryService countryService;

    private static Connection connection;
    private static Savepoint savepoint;
    @Autowired
    private LocationService locationService;

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
    @Order(7)
    void insertLocationTest() throws SQLException {
        streetAddress = streetAddressService.saveStreetAddress(streetAddress);
        location.setStreetAddress(streetAddress);
        updatedLocation.setStreetAddress(streetAddress);

        city = cityService.saveCity(city);
        location.setCity(city);
        updatedLocation.setCity(city);

        state = stateService.saveState(state);
        location.setState(state);
        updatedLocation.setState(state);

        zipCode = zipCodeService.saveZipCode(zipCode);
        location.setZipCode(zipCode);
        updatedLocation.setZipCode(zipCode);

        country = countryService.saveCountry(country);
        location.setCountry(country);
        updatedLocation.setCountry(country);

        location = locationService.saveLocation(location);
        updatedLocation.setId(location.getId());

    }
    @Test
    @Order(8)
    void selectLocationByLocationNameTest() throws SQLException {
        Location location1 = locationService
                .getLocationByLocationName(locationName).get();
        assertTrue(location1.getLocationName().equals(locationName));
        assertTrue(location1.getStreetAddress().getId().equals(streetAddress.getId()));
        assertTrue(location1.getCity().getId().equals(city.getId()));
        assertTrue(location1.getState().getId().equals(state.getId()));
        assertTrue(location1.getZipCode().getId().equals(zipCode.getId()));
        assertTrue(location1.getCountry().getId().equals(country.getId()));
    }
    @Test
    @Order(9)
    void updateLocationByLocationNameTest() throws SQLException {
        updatedLocation = locationService.updateLocation(
                updatedLocation);
    }
    @Test
    @Order(10)
    void selectUpdatedLocationByLocationNameTest() throws SQLException {
        Location location1 = locationService.getLocationByLocationName(updatedLocationName).get();
        assertTrue(location1.getLocationName().equals(updatedLocationName));
        assertTrue(location1.getStreetAddress().getId().equals(streetAddress.getId()));
        assertTrue(location1.getCity().getId().equals(city.getId()));
        assertTrue(location1.getState().getId().equals(state.getId()));
        assertTrue(location1.getZipCode().getId().equals(zipCode.getId()));
        assertTrue(location1.getCountry().getId().equals(country.getId()));
    }
    @Test
    @Order(11)
    void deleteLocationByLocationNameTest() throws SQLException {
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
    }


}
