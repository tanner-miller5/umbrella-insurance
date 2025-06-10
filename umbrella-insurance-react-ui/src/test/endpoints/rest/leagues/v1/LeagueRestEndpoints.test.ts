import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateLeagueRestEndpoints, callDeleteLeagueRestEndpointsByLeagueId, callReadLeagueRestEndpointsByLeagueId, callUpdateLeagueRestEndpoints } from "../../../../../endpoints/rest/leagues/v1/LeagueRestEndpoints";
import { GameType } from "../../../../../models/gameTypes/v1/GameType";
import { League } from "../../../../../models/leagues/v1/League";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('league endpoint tests', () => {
    var leagueId: number | undefined; 
    var gameTypeName = "1";
    var gameType: GameType = new GameType();
    gameType.gameTypeName = gameTypeName;
    var league: League = new League();
    league.leagueName = "1";

    var updatedLeague: League = new League();
    updatedLeague.leagueName = "2";

    var domain: string = "http://localhost:8080";

    test('call create league', async () => {
        var gameTypeResponse: GameType = await callCreateGameTypeRestEndpoints(
            gameType, env, domain);
        gameType.id = gameTypeResponse.id;
        expect(gameType.gameTypeName).toBe(gameTypeResponse.gameTypeName);
        league.gameType = gameTypeResponse;
        updatedLeague.gameType = gameTypeResponse;

        var leagueResponse: League = await callCreateLeagueRestEndpoints(
            league, env, domain);
        leagueId = leagueResponse.id;
        expect(league.leagueName).toBe(leagueResponse.leagueName);
    });

    test('call read league', async () => {
        var leagues: League[] | undefined = await callReadLeagueRestEndpointsByLeagueId(
            leagueId || 1, env, domain) || [];
        expect(leagues[0].leagueName).toBe(league.leagueName);
    });

    test('call update league', async () => {
        var leagueResponse: League[] = await callUpdateLeagueRestEndpoints(
            updatedLeague, env, domain);
        expect(updatedLeague.leagueName).toBe(leagueResponse[0].leagueName);
    });

    test('call delete league', async () => {
        var deleteLeagueResponse: boolean = await callDeleteLeagueRestEndpointsByLeagueId(
            leagueId || 1, env, domain);
        expect(true).toBe(deleteLeagueResponse);

        var deleteGameTypeResponse: boolean = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameType.id || 1, env, domain);
        expect(true).toBe(deleteGameTypeResponse);
    });
});