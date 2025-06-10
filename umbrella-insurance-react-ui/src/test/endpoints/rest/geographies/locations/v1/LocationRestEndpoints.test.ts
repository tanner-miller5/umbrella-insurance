import { callCreateCityRestEndpoints, callDeleteCityRestEndpointsByCityId } from "../../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { callCreateCountryRestEndpoints, callDeleteCountryRestEndpointsByCountryId } from "../../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { callCreateLocationRestEndpoints, callReadLocationRestEndpointsByLocationId, callDeleteLocationRestEndpointsByLocationId, callUpdateLocationRestEndpoints } from "../../../../../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { callCreateStateRestEndpoints, callDeleteStateRestEndpointsByStateId } from "../../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { callCreateStreetAddressRestEndpoints, callDeleteStreetAddressRestEndpointsByStreetAddressId } from "../../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { callCreateZipCodeRestEndpoints, callDeleteZipCodeRestEndpointsByZipCodeId } from "../../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { City } from "../../../../../../models/geographies/cities/v1/City";
import { Country } from "../../../../../../models/geographies/countries/v1/Country";
import { Location } from "../../../../../../models/geographies/locations/v1/Location";
import { State } from "../../../../../../models/geographies/states/v1/State";
import { StreetAddress } from "../../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../../../../../models/geographies/zipCodes/v1/ZipCode";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('location endpoint tests', () => {
    var locationId: number | undefined; 

    var zipCode: ZipCode = new ZipCode();
    zipCode.zipCodeValue = "1";

    var state: State = new State();
    state.stateName = "1";

    var country: Country = new Country();
    country.countryName = "1";

    var city: City = new City();
    city.cityName = "1";

    var streetAddress: StreetAddress = new StreetAddress();
    streetAddress.streetAddressLine1 = "1";
    streetAddress.streetAddressLine2 = "2";
    
    var location: Location = new Location();
    location.locationName = "1";

    var updatedLocation: Location = new Location();
    updatedLocation.locationName = "2";

    var domain: string = "http://localhost:8080";

    test('call create location', async () => {
        var zipCodeResponse: ZipCode = await callCreateZipCodeRestEndpoints(
            zipCode, env, domain);
        zipCode.id = zipCodeResponse.id;
        location.zipCode = zipCodeResponse;
        updatedLocation.zipCode = zipCodeResponse;
        expect(zipCode.zipCodeValue).toBe(zipCodeResponse.zipCodeValue);

        var streetAddressResponse: StreetAddress = await callCreateStreetAddressRestEndpoints(
            streetAddress, env, domain);
        streetAddress.id = streetAddressResponse.id;
        location.streetAddress = streetAddressResponse;
        updatedLocation.streetAddress = streetAddressResponse;
        expect(streetAddress.streetAddressLine1).toBe(streetAddressResponse.streetAddressLine1);
        expect(streetAddress.streetAddressLine2).toBe(streetAddressResponse.streetAddressLine2);

        var stateResponse: State = await callCreateStateRestEndpoints(
            state, env, domain);
        state.id = stateResponse.id;
        location.state = stateResponse;
        updatedLocation.state = stateResponse;
        expect(state.stateName).toBe(stateResponse.stateName);

        var cityResponse: City = await callCreateCityRestEndpoints(
            city, env, domain);
        city.id = cityResponse.id;
        location.city = city;
        updatedLocation.city = city;
        expect(city.cityName).toBe(cityResponse.cityName);

        var countryResponse: Country = await callCreateCountryRestEndpoints(
            country, env, domain);
        country.id = countryResponse.id;
        location.country = countryResponse;
        updatedLocation.country = countryResponse;
        expect(country.countryName).toBe(countryResponse.countryName);

        var locationResponse: Location = await callCreateLocationRestEndpoints(
            location, env, domain);
        locationId = locationResponse.id;
        expect(location.locationName).toBe(locationResponse.locationName);
    });

    test('call read location', async () => {
        var locations: Location[] | undefined = await callReadLocationRestEndpointsByLocationId(
            locationId || 1, env, domain) || [];
        expect(locations[0].locationName).toBe(location.locationName);
    });

    test('call update location', async () => {
        var locationResponse: Location[] = await callUpdateLocationRestEndpoints(
            updatedLocation, env, domain);
        expect(updatedLocation.locationName).toBe(locationResponse[0].locationName);
    });

    test('call delete location', async () => {
        var deleteLocationResponse: boolean = await callDeleteLocationRestEndpointsByLocationId(
            locationId || 1, env, domain);
        expect(true).toBe(deleteLocationResponse);

        var deleteCityResponse: boolean = await callDeleteCityRestEndpointsByCityId(
            city.id || 1, env, domain);
        expect(true).toBe(deleteCityResponse);

        var deleteCountryResponse: boolean = await callDeleteCountryRestEndpointsByCountryId(
            country.id || 1, env, domain);
        expect(true).toBe(deleteCountryResponse);

        var deleteStateResponse: boolean = await callDeleteStateRestEndpointsByStateId(
            state.id || 1, env, domain);
        expect(true).toBe(deleteStateResponse);

        var deleteStreetAddressResponse: boolean = await callDeleteStreetAddressRestEndpointsByStreetAddressId(
            streetAddress.id || 1, env, domain);
        expect(true).toBe(deleteStreetAddressResponse);

        var deleteZipCodeResponse: boolean = await callDeleteZipCodeRestEndpointsByZipCodeId(
            zipCode.id || 1, env, domain);
        expect(true).toBe(deleteZipCodeResponse);
    });
});