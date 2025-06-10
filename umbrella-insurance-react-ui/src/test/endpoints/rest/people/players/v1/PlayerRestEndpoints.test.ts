import { callCreatePlayerRestEndpoints, callReadPlayerRestEndpointsByPlayerId, callDeletePlayerRestEndpointsByPlayerId, callUpdatePlayerRestEndpoints } from "../../../../../../endpoints/rest/people/players/v1/PlayerRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { Player } from "../../../../../../models/people/players/v1/Player";
import { GameType } from "../../../../../../models/gameTypes/v1/GameType";
import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('player endpoint tests', () => {
    var playerId: number | undefined; 
    var gameType: GameType = new GameType();
    gameType.gameTypeName = "1";
    
    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1"
    var player: Player = new Player();

    var updatedPlayer: Player = new Player();

    var domain: string = "http://localhost:8080";


    test('call create player', async () => {
        var gameTypeResponse: GameType = await callCreateGameTypeRestEndpoints(
            gameType, env, domain);
        gameType.id = gameTypeResponse.id;
        player.gameType = gameType;
        updatedPlayer.gameType = gameType;
        expect(gameType.gameTypeName).toBe(gameTypeResponse.gameTypeName);

        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        person.id = personResponse.id;
        player.person = person;
        updatedPlayer.person = person;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);

        var playerResponse: Player = await callCreatePlayerRestEndpoints(
            player, env, domain);
        playerId = playerResponse.id;
        expect(player.birthPlace).toBe(playerResponse.birthPlace);
        expect(player.college).toBe(playerResponse.college);
        expect(player.draftInfo).toBe(playerResponse.draftInfo);
        expect(player.experience).toBe(playerResponse.experience);
        expect(player.gameType).toBe(playerResponse.gameType);
        expect(player.height).toBe(playerResponse.height);
        expect(player.jerseyNumber).toBe(playerResponse.jerseyNumber);
        expect(player.person).toBe(playerResponse.person);
        expect(player.playerPosition).toBe(playerResponse.playerPosition);
        expect(player.playerStatus).toBe(playerResponse.playerStatus);
        expect(player.weight).toBe(playerResponse.weight);
    });

    test('call read player', async () => {
        var players: Player[] | undefined = await callReadPlayerRestEndpointsByPlayerId(
            playerId || 1, env, domain) || [];
            expect(player.birthPlace).toBe(players[0].birthPlace);
            expect(player.college).toBe(players[0].college);
            expect(player.draftInfo).toBe(players[0].draftInfo);
            expect(player.experience).toBe(players[0].experience);
            expect(player.gameType).toBe(players[0].gameType);
            expect(player.height).toBe(players[0].height);
            expect(player.jerseyNumber).toBe(players[0].jerseyNumber);
            expect(player.person).toBe(players[0].person);
            expect(player.playerPosition).toBe(players[0].playerPosition);
            expect(player.playerStatus).toBe(players[0].playerStatus);
            expect(player.weight).toBe(players[0].weight);
    });

    test('call update player', async () => {
        var playerResponse: Player[] = await callUpdatePlayerRestEndpoints(
            updatedPlayer, env, domain);
            expect(player.birthPlace).toBe(playerResponse[0].birthPlace);
            expect(player.college).toBe(playerResponse[0].college);
            expect(player.draftInfo).toBe(playerResponse[0].draftInfo);
            expect(player.experience).toBe(playerResponse[0].experience);
            expect(player.gameType).toBe(playerResponse[0].gameType);
            expect(player.height).toBe(playerResponse[0].height);
            expect(player.jerseyNumber).toBe(playerResponse[0].jerseyNumber);
            expect(player.person).toBe(playerResponse[0].person);
            expect(player.playerPosition).toBe(playerResponse[0].playerPosition);
            expect(player.playerStatus).toBe(playerResponse[0].playerStatus);
            expect(player.weight).toBe(playerResponse[0].weight);
    });

    test('call delete player', async () => {
        var deletePlayerResponse: boolean = await callDeletePlayerRestEndpointsByPlayerId(
            playerId || 1, env, domain);
        expect(true).toBe(deletePlayerResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);

        var deleteGameTypeResponse: boolean = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameType.id || 1, env, domain);
        expect(true).toBe(deleteGameTypeResponse);
    });
});