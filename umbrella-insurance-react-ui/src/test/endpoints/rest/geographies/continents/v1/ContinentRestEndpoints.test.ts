import { callCreateContinentRestEndpoints, callReadContinentRestEndpointsByContinentId, callDeleteContinentRestEndpointsByContinentId, callUpdateContinentRestEndpoints } from "../../../../../../endpoints/rest/geographies/continents/v1/ContinentRestEndpoints";
import { Continent } from "../../../../../../models/geographies/continents/v1/Continent";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('continent endpoint tests', () => {
    var continentId: number | undefined; 
    var continent: Continent = new Continent();
    continent.continentName = "1";

    var updatedContinent: Continent = new Continent();
    updatedContinent.continentName = "2";

    var domain: string = "http://localhost:8080";

    test('call create continent', async () => {
        var continentResponse: Continent = await callCreateContinentRestEndpoints(
            continent, env, domain);
        continentId = continentResponse.id;
        expect(continent.continentName).toBe(continentResponse.continentName);
    });

    test('call read continent', async () => {
        var continents: Continent[] | undefined = await callReadContinentRestEndpointsByContinentId(
            continentId || 1, env, domain) || [];
        expect(continents[0].continentName).toBe(continent.continentName);
    });

    test('call update continent', async () => {
        var continentResponse: Continent[] = await callUpdateContinentRestEndpoints(
            updatedContinent, env, domain);
        expect(updatedContinent.continentName).toBe(continentResponse[0].continentName);
    });

    test('call delete continent', async () => {
        var deleteContinentResponse: boolean = await callDeleteContinentRestEndpointsByContinentId(
            continentId || 1, env, domain);
        expect(true).toBe(deleteContinentResponse);
    });
});