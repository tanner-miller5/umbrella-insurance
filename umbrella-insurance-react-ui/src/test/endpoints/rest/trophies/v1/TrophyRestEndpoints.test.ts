import { callCreateTrophyRestEndpoints, callDeleteTrophyRestEndpointsByTrophyId, callReadTrophyRestEndpointsByTrophyId, callUpdateTrophyRestEndpoints } from "../../../../../endpoints/rest/trophies/v1/TrophyRestEndpoints";
import { Trophy } from "../../../../../models/trophies/v1/Trophy";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('trophy endpoint tests', () => {
    var trophyId: number | undefined; 
    var trophy: Trophy = new Trophy();
    trophy.trophyName = "1";

    var updatedTrophy: Trophy = new Trophy();
    updatedTrophy.trophyName = "2";

    var domain: string = "http://localhost:8080";

    test('call create trophy', async () => {
        var trophyResponse: Trophy = await callCreateTrophyRestEndpoints(
            trophy, env, domain);
        trophyId = trophyResponse.id;
        expect(trophy.trophyName).toBe(trophyResponse.trophyName);
    });

    test('call read trophy', async () => {
        var trophys: Trophy[] | undefined = await callReadTrophyRestEndpointsByTrophyId(
            trophyId || 1, env, domain) || [];
        expect(trophys[0].trophyName).toBe(trophy.trophyName);
    });

    test('call update trophy', async () => {
        var trophyResponse: Trophy[] = await callUpdateTrophyRestEndpoints(
            updatedTrophy, env, domain);
        expect(updatedTrophy.trophyName).toBe(trophyResponse[0].trophyName);
    });

    test('call delete trophy', async () => {
        var deleteTrophyResponse: boolean = await callDeleteTrophyRestEndpointsByTrophyId(
            trophyId || 1, env, domain);
        expect(true).toBe(deleteTrophyResponse);
    });
});