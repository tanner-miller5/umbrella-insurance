import { callCreateSeasonRestEndpoints, callDeleteSeasonRestEndpointsBySeasonId, callReadSeasonRestEndpointsBySeasonId, callUpdateSeasonRestEndpoints } from "../../../../../endpoints/rest/seasons/v1/SeasonRestEndpoints";
import { Season } from "../../../../../models/seasons/v1/Season";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('season endpoint tests', () => {
    var seasonId: number | undefined; 
    var season: Season = new Season();
    season.seasonName = "1";
    season.endDate = "1111-11-11";
    season.startDate = "1111-11-11";

    var updatedSeason: Season = new Season();
    updatedSeason.seasonName = "2";
    updatedSeason.endDate = "1111-11-11";
    updatedSeason.startDate = "1111-11-11";

    var domain: string = "http://localhost:8080";

    test('call create season', async () => {
        var seasonResponse: Season = await callCreateSeasonRestEndpoints(
            season, env, domain);
        seasonId = seasonResponse.id;
        expect(season.seasonName).toBe(seasonResponse.seasonName);
        expect(season.endDate).toBe(seasonResponse.endDate);
        expect(season.startDate).toBe(seasonResponse.startDate);
    });

    test('call read season', async () => {
        var seasons: Season[] | undefined = await callReadSeasonRestEndpointsBySeasonId(
            seasonId || 1, env, domain) || [];
        expect(seasons[0].seasonName).toBe(season.seasonName);
        expect(seasons[0].endDate).toBe(season.endDate);
        expect(seasons[0].startDate).toBe(season.startDate);
    });

    test('call update season', async () => {
        var seasonResponse: Season[] = await callUpdateSeasonRestEndpoints(
            updatedSeason, env, domain);
        expect(updatedSeason.seasonName).toBe(seasonResponse[0].seasonName);
        expect(updatedSeason.endDate).toBe(seasonResponse[0].endDate);
        expect(updatedSeason.startDate).toBe(seasonResponse[0].startDate);
    });

    test('call delete season', async () => {
        var deleteSeasonResponse: boolean = await callDeleteSeasonRestEndpointsBySeasonId(
            seasonId || 1, env, domain);
        expect(true).toBe(deleteSeasonResponse);
    });
});