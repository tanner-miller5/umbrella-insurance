import { callCreateSeasonTypeRestEndpoints, callReadSeasonTypeRestEndpointsBySeasonTypeId, callDeleteSeasonTypeRestEndpointsBySeasonTypeId, callUpdateSeasonTypeRestEndpoints } from "../../../../../../endpoints/rest/seasons/seasonTypes/v1/SeasonTypeRestEndpoints";
import { SeasonType } from "../../../../../../models/seasons/seasonTypes/v1/SeasonType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";

describe.skip('seasonType endpoint tests', () => {
    var seasonTypeId: number | undefined; 
    var seasonType: SeasonType = new SeasonType();
    seasonType.seasonTypeName = "1";

    var updatedSeasonType: SeasonType = new SeasonType();
    updatedSeasonType.seasonTypeName = "2";
    var domain: string = "http://localhost:8080";

    test('call create seasonType', async () => {
        var seasonTypeResponse: SeasonType = await callCreateSeasonTypeRestEndpoints(
            seasonType, env, domain);
        seasonTypeId = seasonTypeResponse.id;
        expect(seasonType.seasonTypeName).toBe(seasonTypeResponse.seasonTypeName);
    });

    test('call read seasonType', async () => {
        var seasonTypes: SeasonType[] | undefined = await callReadSeasonTypeRestEndpointsBySeasonTypeId(
            seasonTypeId || 1, env, domain) || [];
        expect(seasonTypes[0].seasonTypeName).toBe(seasonType.seasonTypeName);
    });

    test('call update seasonType', async () => {
        var seasonTypeResponse: SeasonType[] = await callUpdateSeasonTypeRestEndpoints(
            updatedSeasonType, env, domain);
        expect(updatedSeasonType.seasonTypeName).toBe(seasonTypeResponse[0].seasonTypeName);
    });

    test('call delete seasonType', async () => {
        var deleteSeasonTypeResponse: boolean = await callDeleteSeasonTypeRestEndpointsBySeasonTypeId(
            seasonTypeId || 1, env, domain);
        expect(true).toBe(deleteSeasonTypeResponse);
    });
});