import { callCreateCountryRestEndpoints, callReadCountryRestEndpointsByCountryId, callDeleteCountryRestEndpointsByCountryId, callUpdateCountryRestEndpoints } from "../../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { Country } from "../../../../../../models/geographies/countries/v1/Country";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('country endpoint tests', () => {
    var countryId: number | undefined; 
    var country: Country = new Country();
    country.countryName = "1";

    var updatedCountry: Country = new Country();
    updatedCountry.countryName = "2";

    var domain: string = "http://localhost:8080";

    test('call create country', async () => {
        var countryResponse: Country = await callCreateCountryRestEndpoints(
            country, env, domain);
        countryId = countryResponse.id;
        expect(country.countryName).toBe(countryResponse.countryName);
    });

    test('call read country', async () => {
        var countrys: Country[] | undefined = await callReadCountryRestEndpointsByCountryId(
            countryId || 1, env, domain) || [];
        expect(countrys[0].countryName).toBe(country.countryName);
    });

    test('call update country', async () => {
        var countryResponse: Country[] = await callUpdateCountryRestEndpoints(
            updatedCountry, env, domain);
        expect(updatedCountry.countryName).toBe(countryResponse[0].countryName);
    });

    test('call delete country', async () => {
        var deleteCountryResponse: boolean = await callDeleteCountryRestEndpointsByCountryId(
            countryId || 1, env, domain);
        expect(true).toBe(deleteCountryResponse);
    });
});