import { callCreateCityRestEndpoints, callReadCityRestEndpointsByCityId, callDeleteCityRestEndpointsByCityId, callUpdateCityRestEndpoints } from "../../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { City } from "../../../../../../models/geographies/cities/v1/City";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('city endpoint tests', () => {
    var cityId: number | undefined; 
    var city: City = new City();
    city.cityName = "1";

    var updatedCity: City = new City();
    updatedCity.cityName = "2";

    var domain: string = "http://localhost:8080";

    test('call create city', async () => {
        var cityResponse: City = await callCreateCityRestEndpoints(
            city, env, domain);
        cityId = cityResponse.id;
        expect(city.cityName).toBe(cityResponse.cityName);
    });

    test('call read city', async () => {
        var citys: City[] | undefined = await callReadCityRestEndpointsByCityId(
            cityId || 1, env, domain) || [];
        expect(citys[0].cityName).toBe(city.cityName);
    });

    test('call update city', async () => {
        var cityResponse: City[] = await callUpdateCityRestEndpoints(
            updatedCity, env, domain);
        expect(updatedCity.cityName).toBe(cityResponse[0].cityName);
    });

    test('call delete city', async () => {
        var deleteCityResponse: boolean = await callDeleteCityRestEndpointsByCityId(
            cityId || 1, env, domain);
        expect(true).toBe(deleteCityResponse);
    });
});