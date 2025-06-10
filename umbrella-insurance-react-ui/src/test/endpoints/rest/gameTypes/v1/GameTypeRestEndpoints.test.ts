import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId, callReadGameTypeRestEndpointsByGameTypeId, callUpdateGameTypeRestEndpoints } from "../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { GameType } from "../../../../../models/gameTypes/v1/GameType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('gameType endpoint tests', () => {
    var gameTypeId: number | undefined; 
    var gameType: GameType = new GameType();
    gameType.gameTypeName = "1";

    var updatedGameType: GameType = new GameType();
    updatedGameType.gameTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create gameType', async () => {
        var gameTypeResponse: GameType = await callCreateGameTypeRestEndpoints(
            gameType, env, domain);
        gameTypeId = gameTypeResponse.id;
        expect(gameType.gameTypeName).toBe(gameTypeResponse.gameTypeName);
    });

    test('call read gameType', async () => {
        var gameTypes: GameType[] | undefined = await callReadGameTypeRestEndpointsByGameTypeId(
            gameTypeId || 1, env, domain) || [];
        expect(gameTypes[0].gameTypeName).toBe(gameType.gameTypeName);
    });

    test('call update gameType', async () => {
        var gameTypeResponse: GameType[] = await callUpdateGameTypeRestEndpoints(
            updatedGameType, env, domain);
        expect(updatedGameType.gameTypeName).toBe(gameTypeResponse[0].gameTypeName);
    });

    test('call delete gameType', async () => {
        var deleteGameTypeResponse: boolean = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameTypeId || 1, env, domain);
        expect(true).toBe(deleteGameTypeResponse);
    });
});