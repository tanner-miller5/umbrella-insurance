import { callCreateGameStatusRestEndpoints, callReadGameStatusRestEndpointsByGameStatusId, callDeleteGameStatusRestEndpointsByGameStatusId, callUpdateGameStatusRestEndpoints } from "../../../../../../endpoints/rest/games/gameStatuses/v1/GameStatusRestEndpoints";
import { GameStatus } from "../../../../../../models/games/gameStatuses/v1/GameStatus";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('gameStatus endpoint tests', () => {
    var gameStatusId: number | undefined; 
    var gameStatus: GameStatus = new GameStatus();
    gameStatus.gameStatusName = "1";

    var updatedGameStatus: GameStatus = new GameStatus();
    updatedGameStatus.gameStatusName = "2";

    var domain: string = "http://localhost:8080";

    test('call create gameStatus', async () => {
        var gameStatusResponse: GameStatus = await callCreateGameStatusRestEndpoints(
            gameStatus, env, domain);
        gameStatusId = gameStatusResponse.id;
        expect(gameStatus.gameStatusName).toBe(gameStatusResponse.gameStatusName);
    });

    test('call read gameStatus', async () => {
        var gameStatuss: GameStatus[] | undefined = await callReadGameStatusRestEndpointsByGameStatusId(
            gameStatusId || 1, env, domain) || [];
        expect(gameStatuss[0].gameStatusName).toBe(gameStatus.gameStatusName);
    });

    test('call update gameStatus', async () => {
        var gameStatusResponse: GameStatus[] = await callUpdateGameStatusRestEndpoints(
            updatedGameStatus, env, domain);
        expect(updatedGameStatus.gameStatusName).toBe(gameStatusResponse[0].gameStatusName);
    });

    test('call delete gameStatus', async () => {
        var deleteGameStatusResponse: boolean = await callDeleteGameStatusRestEndpointsByGameStatusId(
            gameStatusId || 1, env, domain);
        expect(true).toBe(deleteGameStatusResponse);
    });
});