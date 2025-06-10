import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreatePlayerRestEndpoints, callDeletePlayerRestEndpointsByPlayerId } from "../../../../../../endpoints/rest/people/players/v1/PlayerRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreatePlayerRecordRestEndpoints, callReadPlayerRecordRestEndpointsByPlayerRecordId, callDeletePlayerRecordRestEndpointsByPlayerRecordId, callUpdatePlayerRecordRestEndpoints } from "../../../../../../endpoints/rest/records/playerRecords/v1/PlayerRecordRestEndpoints";
import { callCreateRecordRestEndpoints, callDeleteRecordRestEndpointsByRecordId } from "../../../../../../endpoints/rest/records/v1/RecordRestEndpoints";
import { callCreateSeasonRestEndpoints, callDeleteSeasonRestEndpointsBySeasonId } from "../../../../../../endpoints/rest/seasons/v1/SeasonRestEndpoints";
import { GameType } from "../../../../../../models/gameTypes/v1/GameType";
import { Player } from "../../../../../../models/people/players/v1/Player";
import { Person } from "../../../../../../models/people/v1/Person";
import { PlayerRecord } from "../../../../../../models/records/playerRecords/v1/PlayerRecord";
import { Record } from "../../../../../../models/records/v1/Record";
import { Season } from "../../../../../../models/seasons/v1/Season";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('player endpoint tests', () => {
    var playerRecordId: number | undefined; 

    var gameType: GameType = new GameType();
    var season: Season = new Season();
    var record: Record = new Record();

    var person: Person = new Person();

    var player: Player = new Player();
    var gameType: GameType = new GameType();
    var season: Season = new Season();
    season.seasonName = "1";
    season.endDate = "1111-11-11";
    season.startDate = "1111-11-11";

    var playerRecord: PlayerRecord = new PlayerRecord();

    var updatedPlayerRecord: PlayerRecord = new PlayerRecord();

    gameType.gameTypeName = "1";

    record.losses = 1;
    record.ties = 2;
    record.wins = 3;
    record.recordName = "1";

    gameType.gameTypeName = "2";

    season.endDate = "1116-11-11";
    season.startDate = "2002-10-10";
    season.seasonName = "6";

    player.playerPosition = "11";
    player.birthPlace = "12";
    player.experience = "10";
    player.jerseyNumber = "00";
    player.playerStatus = "12";
    player.draftInfo = "13";
    player.height = "14";
    player.college = "15";
    player.weight = "16";

    person.dateOfBirth = "1111-11-11";
    person.ssn = "1";
    person.surname = "2";
    person.middleName = "3";
    person.firstName = "4";

    var domain: string = "http://localhost:8080";

    test('call create player record', async () => {
        var seasonResponse: Season = await callCreateSeasonRestEndpoints(
            season, env, domain);
        season.id = seasonResponse.id;
        playerRecord.season = season;
        updatedPlayerRecord.season = season;
        expect(season.seasonName).toBe(seasonResponse.seasonName);
        expect(season.endDate).toBe(seasonResponse.endDate);
        expect(season.startDate).toBe(seasonResponse.startDate);

        var gameTypeResponse: GameType = await callCreateGameTypeRestEndpoints(
            gameType, env, domain);
        gameType.id = gameTypeResponse.id;
        player.gameType = gameType;
        expect(gameType.gameTypeName).toBe(gameTypeResponse.gameTypeName);

        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        person.id = personResponse.id;
        player.person = person;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);

        var playerResponse: Player = await callCreatePlayerRestEndpoints(
            player, env, domain);
        player.id = playerResponse.id;
        player.id = player.id;
        updatedPlayerRecord.player = player;
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
        
        var recordResponse: Record = await callCreateRecordRestEndpoints(
            record, env, domain);
        record.id = recordResponse.id;
        playerRecord.record = record;
        updatedPlayerRecord.record = record;
        expect(record.recordName).toBe(recordResponse.recordName);
        expect(record.losses).toBe(recordResponse.losses);
        expect(record.ties).toBe(recordResponse.ties);
        expect(record.wins).toBe(recordResponse.wins);


        var playerRecordResponse: PlayerRecord = await callCreatePlayerRecordRestEndpoints(
            playerRecord, env, domain);
        playerRecordId = playerRecordResponse.id;
        expect(playerRecord.player).toBe(playerRecordResponse.player);
        expect(playerRecord.record).toBe(playerRecordResponse.record);
        expect(playerRecord.season).toBe(playerRecordResponse.season);
    });

    test('call read player record', async () => {
        var playerRecords: PlayerRecord[] | undefined = await callReadPlayerRecordRestEndpointsByPlayerRecordId(
            playerRecordId || 1, env, domain) || [];
        expect(playerRecords[0].player).toBe(playerRecord.player);
        expect(playerRecords[0].record).toBe(playerRecord.record);
        expect(playerRecords[0].season).toBe(playerRecord.season);
    });

    test('call update player record', async () => {
        var playerRecordResponse: PlayerRecord[] = await callUpdatePlayerRecordRestEndpoints(
            updatedPlayerRecord, env, domain);
        expect(updatedPlayerRecord.player).toBe(playerRecordResponse[0].player);
        expect(updatedPlayerRecord.record).toBe(playerRecordResponse[0].record);
        expect(updatedPlayerRecord.season).toBe(playerRecordResponse[0].season);
    });

    test('call delete player record', async () => {
        var deletePlayerRecordResponse: boolean = await callDeletePlayerRecordRestEndpointsByPlayerRecordId(
            playerRecordId || 1, env, domain);
        expect(true).toBe(deletePlayerRecordResponse);

        var deleteRecordResponse: boolean = await callDeleteRecordRestEndpointsByRecordId(
            record.id || 1, env, domain);
        expect(true).toBe(deleteRecordResponse);

        var deletePlayerResponse: boolean = await callDeletePlayerRestEndpointsByPlayerId(
            player.id || 1, env, domain);
        expect(true).toBe(deletePlayerResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);

        var deleteGameTypeResponse: boolean = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameType.id || 1, env, domain);
        expect(true).toBe(deleteGameTypeResponse);

        var deleteSeasonResponse: boolean = await callDeleteSeasonRestEndpointsBySeasonId(
            season.id || 1, env, domain);
        expect(true).toBe(deleteSeasonResponse);
    });
});