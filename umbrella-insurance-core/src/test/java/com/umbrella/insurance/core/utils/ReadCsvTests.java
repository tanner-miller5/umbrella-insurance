package com.umbrella.insurance.core.utils;

import com.umbrella.insurance.core.models.entities.City;
import com.umbrella.insurance.core.models.entities.Location;
import com.umbrella.insurance.core.models.entities.State;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa.LocationService;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;


@SpringBootTest
public class ReadCsvTests {

    @Autowired
    private LocationService locationService;
    @Autowired
    private CityService cityService;
    @Autowired
    private StateService stateService;

    @Test
    public void readCsv() {
        String filePath = "src/test/resources/cities.csv";
        String line;
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                String cityName = values[0].replaceAll("[\"]", "");
                String type = values[2].replaceAll("[\"]", "");
                if(type.equals("County")) {
                    System.out.println("County");
                    continue;
                }
                String stateName = values[5].replaceAll("[\"]", "");
                City city = new City();
                city.setCityName(cityName);
                Optional<City> cityT = cityService.getCityByCityName(cityName);
                Location location = new Location();
                if(cityT.isEmpty()) {
                    city = cityService.saveCity(city);
                    location.setCity(city);
                } else {
                    location.setCity(cityT.get());
                }
                Optional<State> stateT = stateService.getStateByStateName(stateName);
                State state = new State();
                state.setStateName(stateName);
                if(stateT.isEmpty()) {
                    state = stateService.saveState(state);
                    location.setState(state);
                } else {
                    location.setState(stateT.get());
                }
                String locationName = cityName+","+stateName;
                location.setLocationName(locationName);
                Optional<Location> locationT = locationService.getLocationByLocationName(locationName);
                if(locationT.isEmpty()) {
                    location = locationService.saveLocation(location);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
