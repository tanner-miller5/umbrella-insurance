import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateGameStatusRestEndpoints, callDeleteGameStatusRestEndpointsByGameStatusId } from "../../../../../../../endpoints/rest/games/gameStatuses/v1/GameStatusRestEndpoints";
import { callCreateGameRestEndpoints, callDeleteGameRestEndpointsByGameId } from "../../../../../../../endpoints/rest/games/v1/GameRestEndpoints";
import { callCreateCityRestEndpoints, callDeleteCityRestEndpointsByCityId } from "../../../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { callCreateCountryRestEndpoints, callDeleteCountryRestEndpointsByCountryId } from "../../../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { callCreateLocationRestEndpoints, callDeleteLocationRestEndpointsByLocationId } from "../../../../../../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { callCreateStateRestEndpoints, callDeleteStateRestEndpointsByStateId } from "../../../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { callCreateStreetAddressRestEndpoints, callDeleteStreetAddressRestEndpointsByStreetAddressId } from "../../../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { callCreateZipCodeRestEndpoints, callDeleteZipCodeRestEndpointsByZipCodeId } from "../../../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { callCreatePlayerRestEndpoints, callDeletePlayerRestEndpointsByPlayerId } from "../../../../../../../endpoints/rest/people/players/v1/PlayerRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateSeasonTypeRestEndpoints, callDeleteSeasonTypeRestEndpointsBySeasonTypeId } from "../../../../../../../endpoints/rest/seasons/seasonTypes/v1/SeasonTypeRestEndpoints";
import { callCreateFootballPlayerStatsRestEndpoints, callReadFootballPlayerStatsRestEndpointsByFootballPlayerStatsId, callDeleteFootballPlayerStatsRestEndpointsByFootballPlayerStatsId, callUpdateFootballPlayerStatsRestEndpoints } from "../../../../../../../endpoints/rest/stats/playerStats/footballPlayerStats/v1/FootballPlayerStatsRestEndpoints";
import { GameType } from "../../../../../../../models/gameTypes/v1/GameType";
import { GameStatus } from "../../../../../../../models/games/gameStatuses/v1/GameStatus";
import { Game } from "../../../../../../../models/games/v1/Game";
import { City } from "../../../../../../../models/geographies/cities/v1/City";
import { Country } from "../../../../../../../models/geographies/countries/v1/Country";
import { Location } from "../../../../../../../models/geographies/locations/v1/Location";
import { State } from "../../../../../../../models/geographies/states/v1/State";
import { StreetAddress } from "../../../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../../../../../../models/geographies/zipCodes/v1/ZipCode";
import { Player } from "../../../../../../../models/people/players/v1/Player";
import { Person } from "../../../../../../../models/people/v1/Person";
import { SeasonType } from "../../../../../../../models/seasons/seasonTypes/v1/SeasonType";
import { FootballPlayerStats } from "../../../../../../../models/stats/playerStats/footballPlayerStats/v1/FootballPlayerStats";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('footballPlayerStats endpoint tests', () => {
    var footballPlayerStatsId: number | undefined; 
    var dateTime: string = "2023-12-12T12:12:10Z";
    var gameName: string = "1";

    var streetAddress: StreetAddress = new StreetAddress();
    var city: City = new City();
    var state: State = new State();
    var zipCode: ZipCode = new ZipCode();
    var country: Country = new Country();
    var location: Location = new Location();
    var gameStatus: GameStatus = new GameStatus();
    var gameType: GameType = new GameType();
    var seasonType: SeasonType = new SeasonType();
    var game: Game = new Game();
    location.locationName = "1";
    streetAddress.streetAddressLine1 = "2";
    streetAddress.streetAddressLine2 = "3";
    city.cityName = "4";
    state.stateName = "5";
    zipCode.zipCodeValue = "6";
    country.countryName = "7";

    gameStatus.gameStatusName = "6";
    gameType.gameTypeName = "7";
    seasonType.seasonTypeName = "8";

    game.dateTime = dateTime;
    game.gameName = gameName;
    
    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1"
    var player: Player = new Player();

    var footballPlayerStats: FootballPlayerStats = new FootballPlayerStats();
    footballPlayerStats.adjustedQbr = 1;
    footballPlayerStats.averageYardsPerReception = 2;
    footballPlayerStats.averages = 3;
    footballPlayerStats.completions = 4;
    footballPlayerStats.defensiveTouchdowns = 5;
    footballPlayerStats.extraPointAttempts = 6;
    footballPlayerStats.extraPoints = 7;
    footballPlayerStats.fieldGoalPercentage = 8;
    footballPlayerStats.fumblesLost = 9;
    footballPlayerStats.fumblesRecovered = 10;
    footballPlayerStats.in20 = 11;
    footballPlayerStats.interceptionTouchdowns = 12;
    footballPlayerStats.interceptionYards = 4;
    footballPlayerStats.interceptions = 14;
    footballPlayerStats.interceptionsThrown = 15;
    footballPlayerStats.kickReturnTouchdowns = 16;
    footballPlayerStats.longestFieldGoal = 17;
    footballPlayerStats.longestKickReturnYards = 18;
    footballPlayerStats.longestPuntReturnYards = 19;
    footballPlayerStats.longestPuntYards = 20;
    footballPlayerStats.longestReception = 21;
    footballPlayerStats.longestRun = 22;
    footballPlayerStats.numberOfFieldGoals = 23;
    footballPlayerStats.numberOfKickReturns = 24;
    footballPlayerStats.numberOfPuntReturns = 25;
    footballPlayerStats.numberOfPunts = 26;
    footballPlayerStats.numberOfTimesSacked = 27;
    footballPlayerStats.numberOfYardsPerKickReturn = 28;
    footballPlayerStats.passerRating = 29;
    footballPlayerStats.passesDefended = 30;
    footballPlayerStats.passingAttempts = 31;
    footballPlayerStats.passingTouchdowns = 32;
    footballPlayerStats.passingYards = 33;
    footballPlayerStats.points = 34;
    footballPlayerStats.puntReturnTouchdowns = 35;
    footballPlayerStats.puntReturnYardsPerPuntReturn = 36;
    footballPlayerStats.puntYardsPerPunt = 37;
    footballPlayerStats.quarterbackHits = 38;
    footballPlayerStats.receivingTargets = 39;
    footballPlayerStats.receivingTouchdowns = 40;
    footballPlayerStats.rushingAttempts = 41;
    footballPlayerStats.rushingTouchdowns = 42;
    footballPlayerStats.sackedYardsLost = 43;
    footballPlayerStats.soloTackles = 44;
    footballPlayerStats.tacklesForLoss = 45;
    footballPlayerStats.totalFumbles = 46;
    footballPlayerStats.totalKickReturnYards = 47;
    footballPlayerStats.totalPuntReturnYards = 48;
    footballPlayerStats.totalPuntYards = 49;
    footballPlayerStats.totalReceivingYards = 50;
    footballPlayerStats.totalReceptions = 51;
    footballPlayerStats.totalSacks = 52;
    footballPlayerStats.totalTackles = 53;
    footballPlayerStats.touchbacks = 54;
    footballPlayerStats.yardsPerPassAttempt = 55;

    var updatedFootballPlayerStats: FootballPlayerStats = new FootballPlayerStats();
    updatedFootballPlayerStats.adjustedQbr = 101;
    updatedFootballPlayerStats.averageYardsPerReception = 102;
    updatedFootballPlayerStats.averages = 103;
    updatedFootballPlayerStats.completions = 104;
    updatedFootballPlayerStats.defensiveTouchdowns = 105;
    updatedFootballPlayerStats.extraPointAttempts = 106;
    updatedFootballPlayerStats.extraPoints = 107;
    updatedFootballPlayerStats.fieldGoalPercentage = 108;
    updatedFootballPlayerStats.fumblesLost = 109;
    updatedFootballPlayerStats.fumblesRecovered = 110;
    updatedFootballPlayerStats.in20 = 111;
    updatedFootballPlayerStats.interceptionTouchdowns = 112;
    updatedFootballPlayerStats.interceptionYards = 113;
    updatedFootballPlayerStats.interceptions = 114;
    updatedFootballPlayerStats.interceptionsThrown = 115;
    updatedFootballPlayerStats.kickReturnTouchdowns = 116;
    updatedFootballPlayerStats.longestFieldGoal = 117;
    updatedFootballPlayerStats.longestKickReturnYards = 118;
    updatedFootballPlayerStats.longestPuntReturnYards = 119;
    updatedFootballPlayerStats.longestPuntYards = 120;
    updatedFootballPlayerStats.longestReception = 121;
    updatedFootballPlayerStats.longestRun = 122;
    updatedFootballPlayerStats.numberOfFieldGoals = 123;
    updatedFootballPlayerStats.numberOfKickReturns = 124;
    updatedFootballPlayerStats.numberOfPuntReturns = 125;
    updatedFootballPlayerStats.numberOfPunts = 126;
    updatedFootballPlayerStats.numberOfTimesSacked = 127;
    updatedFootballPlayerStats.numberOfYardsPerKickReturn = 128;
    updatedFootballPlayerStats.passerRating = 129;
    updatedFootballPlayerStats.passesDefended = 130;
    updatedFootballPlayerStats.passingAttempts = 131;
    updatedFootballPlayerStats.passingTouchdowns = 132;
    updatedFootballPlayerStats.passingYards = 133;
    updatedFootballPlayerStats.points = 134;
    updatedFootballPlayerStats.puntReturnTouchdowns = 135;
    updatedFootballPlayerStats.puntReturnYardsPerPuntReturn = 136;
    updatedFootballPlayerStats.puntYardsPerPunt = 137;
    updatedFootballPlayerStats.quarterbackHits = 138;
    updatedFootballPlayerStats.receivingTargets = 139;
    updatedFootballPlayerStats.receivingTouchdowns = 140;
    updatedFootballPlayerStats.rushingAttempts = 141;
    updatedFootballPlayerStats.rushingTouchdowns = 142;
    updatedFootballPlayerStats.sackedYardsLost = 143;
    updatedFootballPlayerStats.soloTackles = 144;
    updatedFootballPlayerStats.tacklesForLoss = 145;
    updatedFootballPlayerStats.totalFumbles = 146;
    updatedFootballPlayerStats.totalKickReturnYards = 147;
    updatedFootballPlayerStats.totalPuntReturnYards = 148;
    updatedFootballPlayerStats.totalPuntYards = 149;
    updatedFootballPlayerStats.totalReceivingYards = 150;
    updatedFootballPlayerStats.totalReceptions = 151;
    updatedFootballPlayerStats.totalSacks = 152;
    updatedFootballPlayerStats.totalTackles = 153;
    updatedFootballPlayerStats.touchbacks = 154;
    updatedFootballPlayerStats.yardsPerPassAttempt = 155;

    var domain: string = "http://localhost:8080";

    test('call create footballPlayerStats', async () => {
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
        footballPlayerStats.player = player
        updatedFootballPlayerStats.player = player
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

        var createStreetAddressResponse = await callCreateStreetAddressRestEndpoints(streetAddress, env, domain);
        expect(createStreetAddressResponse.streetAddressLine1).toBe(streetAddress.streetAddressLine1);
        streetAddress.id = createStreetAddressResponse.id;
        location.streetAddress = streetAddress;

        var createCityResponse = await callCreateCityRestEndpoints(city, env, domain);
        expect(createCityResponse.cityName).toBe(city.cityName);
        city.id = createCityResponse.id;
        location.city = city;

        var createStateResponse = await callCreateStateRestEndpoints(state, env, domain);
        expect(createStateResponse.stateName).toBe(state.stateName);
        state.id = createStateResponse.id;
        location.state = state;

        var zipResponse = await callCreateZipCodeRestEndpoints(zipCode, env, domain);
        expect(zipResponse.zipCodeValue).toBe(zipCode.zipCodeValue);
        zipCode.id = zipResponse.id
        location.zipCode = zipCode;

        var countryResponse = await callCreateCountryRestEndpoints(country, env, domain);
        expect(countryResponse.countryName).toBe(country.countryName);
        country.id = countryResponse.id;
        location.country = country;

        var locationResponse = await callCreateLocationRestEndpoints(location, env, domain);
        expect(locationResponse);
        location.id = locationResponse.id;
        game.location = location;

        var gameStatusResponse = await callCreateGameStatusRestEndpoints(gameStatus, env, domain);
        expect(gameStatusResponse.gameStatusName).toBe(gameStatus.gameStatusName);
        gameStatus.id = gameStatusResponse.id;
        game.gameStatus = gameStatus;

        var gameTypeResponse = await callCreateGameTypeRestEndpoints(gameType, env, domain);
        expect(gameTypeResponse.gameTypeName).toBe(gameType.gameTypeName);
        gameType.id = gameTypeResponse.id;
        game.gameType = gameType

        var seasonTypeResponse = await callCreateSeasonTypeRestEndpoints(seasonType, env, domain);
        expect(seasonTypeResponse.seasonTypeName).toBe(seasonType.seasonTypeName);
        seasonType.id = seasonTypeResponse.id;
        game.seasonType = seasonType;

        var gameResponse: Game = await callCreateGameRestEndpoints(
            game, env, domain);
        game.id = gameResponse.id;
        footballPlayerStats.game = game;
        updatedFootballPlayerStats.game = game;
        expect(game.gameName).toBe(gameResponse.gameName);
        expect(game.gameStatus).toBe(gameResponse.gameStatus);
        expect(game.gameType).toBe(gameResponse.gameType);
        expect(game.dateTime).toBe(gameResponse.dateTime);
        expect(game.location).toBe(gameResponse.location);
        expect(game.seasonType).toBe(gameResponse.seasonType);
        
        var footballPlayerStatsResponse: FootballPlayerStats = await callCreateFootballPlayerStatsRestEndpoints(
            footballPlayerStats, env, domain);
        footballPlayerStatsId = footballPlayerStatsResponse.id;
        expect(footballPlayerStats.player).toBe(footballPlayerStatsResponse.player);
        expect(footballPlayerStats.game).toBe(footballPlayerStatsResponse.game);
        expect(footballPlayerStats.adjustedQbr).toBe(footballPlayerStatsResponse.adjustedQbr);
        expect(footballPlayerStats.averageYardsPerReception).toBe(footballPlayerStatsResponse.averageYardsPerReception);
        expect(footballPlayerStats.averages).toBe(footballPlayerStatsResponse.averages);
        expect(footballPlayerStats.completions).toBe(footballPlayerStatsResponse.completions);
        expect(footballPlayerStats.defensiveTouchdowns).toBe(footballPlayerStatsResponse.defensiveTouchdowns);
        expect(footballPlayerStats.extraPointAttempts).toBe(footballPlayerStatsResponse.extraPointAttempts);
        expect(footballPlayerStats.extraPoints).toBe(footballPlayerStatsResponse.extraPoints);
        expect(footballPlayerStats.fieldGoalPercentage).toBe(footballPlayerStatsResponse.fieldGoalPercentage);
        expect(footballPlayerStats.fumblesLost).toBe(footballPlayerStatsResponse.fumblesLost);
        expect(footballPlayerStats.fumblesRecovered).toBe(footballPlayerStatsResponse.fumblesRecovered);
        expect(footballPlayerStats.in20).toBe(footballPlayerStatsResponse.in20);
        expect(footballPlayerStats.interceptionTouchdowns).toBe(footballPlayerStatsResponse.interceptionTouchdowns);
        expect(footballPlayerStats.interceptionYards).toBe(footballPlayerStatsResponse.interceptionYards);
        expect(footballPlayerStats.interceptions).toBe(footballPlayerStatsResponse.interceptions);
        expect(footballPlayerStats.interceptionsThrown).toBe(footballPlayerStatsResponse.interceptionsThrown);
        expect(footballPlayerStats.kickReturnTouchdowns).toBe(footballPlayerStatsResponse.kickReturnTouchdowns);
        expect(footballPlayerStats.longestFieldGoal).toBe(footballPlayerStatsResponse.longestFieldGoal);
        expect(footballPlayerStats.longestKickReturnYards).toBe(footballPlayerStatsResponse.longestKickReturnYards);
        expect(footballPlayerStats.longestPuntReturnYards).toBe(footballPlayerStatsResponse.longestPuntReturnYards);
        expect(footballPlayerStats.longestPuntYards).toBe(footballPlayerStatsResponse.longestPuntYards);
        expect(footballPlayerStats.longestReception).toBe(footballPlayerStatsResponse.longestReception);
        expect(footballPlayerStats.longestRun).toBe(footballPlayerStatsResponse.longestRun);
        expect(footballPlayerStats.numberOfFieldGoals).toBe(footballPlayerStatsResponse.numberOfFieldGoals);
        expect(footballPlayerStats.numberOfKickReturns).toBe(footballPlayerStatsResponse.numberOfKickReturns);
        expect(footballPlayerStats.numberOfPuntReturns).toBe(footballPlayerStatsResponse.numberOfPuntReturns);
        expect(footballPlayerStats.numberOfPunts).toBe(footballPlayerStatsResponse.numberOfPunts);
        expect(footballPlayerStats.numberOfTimesSacked).toBe(footballPlayerStatsResponse.numberOfTimesSacked);
        expect(footballPlayerStats.numberOfYardsPerKickReturn).toBe(footballPlayerStatsResponse.numberOfYardsPerKickReturn);
        expect(footballPlayerStats.passerRating).toBe(footballPlayerStatsResponse.passerRating);
        expect(footballPlayerStats.passesDefended).toBe(footballPlayerStatsResponse.passesDefended);
        expect(footballPlayerStats.passingAttempts).toBe(footballPlayerStatsResponse.passingAttempts);
        expect(footballPlayerStats.passingTouchdowns).toBe(footballPlayerStatsResponse.passingTouchdowns);
        expect(footballPlayerStats.passingYards).toBe(footballPlayerStatsResponse.passingYards);
        expect(footballPlayerStats.points).toBe(footballPlayerStatsResponse.points);
        expect(footballPlayerStats.puntReturnTouchdowns).toBe(footballPlayerStatsResponse.puntReturnTouchdowns);
        expect(footballPlayerStats.puntReturnYardsPerPuntReturn).toBe(footballPlayerStatsResponse.puntReturnYardsPerPuntReturn);
        expect(footballPlayerStats.puntYardsPerPunt).toBe(footballPlayerStatsResponse.puntYardsPerPunt);
        expect(footballPlayerStats.quarterbackHits).toBe(footballPlayerStatsResponse.quarterbackHits);
        expect(footballPlayerStats.receivingTargets).toBe(footballPlayerStatsResponse.receivingTargets);
        expect(footballPlayerStats.receivingTouchdowns).toBe(footballPlayerStatsResponse.receivingTouchdowns);
        expect(footballPlayerStats.rushingAttempts).toBe(footballPlayerStatsResponse.rushingAttempts);
        expect(footballPlayerStats.rushingTouchdowns).toBe(footballPlayerStatsResponse.rushingTouchdowns);
        expect(footballPlayerStats.sackedYardsLost).toBe(footballPlayerStatsResponse.sackedYardsLost);
        expect(footballPlayerStats.soloTackles).toBe(footballPlayerStatsResponse.soloTackles);
        expect(footballPlayerStats.tacklesForLoss).toBe(footballPlayerStatsResponse.tacklesForLoss);
        expect(footballPlayerStats.totalFumbles).toBe(footballPlayerStatsResponse.totalFumbles);
        expect(footballPlayerStats.totalKickReturnYards).toBe(footballPlayerStatsResponse.totalKickReturnYards);
        expect(footballPlayerStats.totalPuntReturnYards).toBe(footballPlayerStatsResponse.totalPuntReturnYards);
        expect(footballPlayerStats.totalPuntYards).toBe(footballPlayerStatsResponse.totalPuntYards);
        expect(footballPlayerStats.totalReceivingYards).toBe(footballPlayerStatsResponse.totalReceivingYards);
        expect(footballPlayerStats.totalReceptions).toBe(footballPlayerStatsResponse.totalReceptions);
        expect(footballPlayerStats.totalSacks).toBe(footballPlayerStatsResponse.totalSacks);
        expect(footballPlayerStats.totalTackles).toBe(footballPlayerStatsResponse.totalTackles);
        expect(footballPlayerStats.touchbacks).toBe(footballPlayerStatsResponse.touchbacks);
        expect(footballPlayerStats.yardsPerPassAttempt).toBe(footballPlayerStatsResponse.yardsPerPassAttempt);
    });

    test('call read footballPlayerStats', async () => {
        var footballPlayerStatsResponse: FootballPlayerStats[] | undefined = await callReadFootballPlayerStatsRestEndpointsByFootballPlayerStatsId(
            footballPlayerStatsId || 1, env, domain) || [];
        expect(footballPlayerStats.player).toBe(footballPlayerStatsResponse[0].player);
        expect(footballPlayerStats.game).toBe(footballPlayerStatsResponse[0].game);
        expect(footballPlayerStats.adjustedQbr).toBe(footballPlayerStatsResponse[0].adjustedQbr);
        expect(footballPlayerStats.averageYardsPerReception).toBe(footballPlayerStatsResponse[0].averageYardsPerReception);
        expect(footballPlayerStats.averages).toBe(footballPlayerStatsResponse[0].averages);
        expect(footballPlayerStats.completions).toBe(footballPlayerStatsResponse[0].completions);
        expect(footballPlayerStats.defensiveTouchdowns).toBe(footballPlayerStatsResponse[0].defensiveTouchdowns);
        expect(footballPlayerStats.extraPointAttempts).toBe(footballPlayerStatsResponse[0].extraPointAttempts);
        expect(footballPlayerStats.extraPoints).toBe(footballPlayerStatsResponse[0].extraPoints);
        expect(footballPlayerStats.fieldGoalPercentage).toBe(footballPlayerStatsResponse[0].fieldGoalPercentage);
        expect(footballPlayerStats.fumblesLost).toBe(footballPlayerStatsResponse[0].fumblesLost);
        expect(footballPlayerStats.fumblesRecovered).toBe(footballPlayerStatsResponse[0].fumblesRecovered);
        expect(footballPlayerStats.in20).toBe(footballPlayerStatsResponse[0].in20);
        expect(footballPlayerStats.interceptionTouchdowns).toBe(footballPlayerStatsResponse[0].interceptionTouchdowns);
        expect(footballPlayerStats.interceptionYards).toBe(footballPlayerStatsResponse[0].interceptionYards);
        expect(footballPlayerStats.interceptions).toBe(footballPlayerStatsResponse[0].interceptions);
        expect(footballPlayerStats.interceptionsThrown).toBe(footballPlayerStatsResponse[0].interceptionsThrown);
        expect(footballPlayerStats.kickReturnTouchdowns).toBe(footballPlayerStatsResponse[0].kickReturnTouchdowns);
        expect(footballPlayerStats.longestFieldGoal).toBe(footballPlayerStatsResponse[0].longestFieldGoal);
        expect(footballPlayerStats.longestKickReturnYards).toBe(footballPlayerStatsResponse[0].longestKickReturnYards);
        expect(footballPlayerStats.longestPuntReturnYards).toBe(footballPlayerStatsResponse[0].longestPuntReturnYards);
        expect(footballPlayerStats.longestPuntYards).toBe(footballPlayerStatsResponse[0].longestPuntYards);
        expect(footballPlayerStats.longestReception).toBe(footballPlayerStatsResponse[0].longestReception);
        expect(footballPlayerStats.longestRun).toBe(footballPlayerStatsResponse[0].longestRun);
        expect(footballPlayerStats.numberOfFieldGoals).toBe(footballPlayerStatsResponse[0].numberOfFieldGoals);
        expect(footballPlayerStats.numberOfKickReturns).toBe(footballPlayerStatsResponse[0].numberOfKickReturns);
        expect(footballPlayerStats.numberOfPuntReturns).toBe(footballPlayerStatsResponse[0].numberOfPuntReturns);
        expect(footballPlayerStats.numberOfPunts).toBe(footballPlayerStatsResponse[0].numberOfPunts);
        expect(footballPlayerStats.numberOfTimesSacked).toBe(footballPlayerStatsResponse[0].numberOfTimesSacked);
        expect(footballPlayerStats.numberOfYardsPerKickReturn).toBe(footballPlayerStatsResponse[0].numberOfYardsPerKickReturn);
        expect(footballPlayerStats.passerRating).toBe(footballPlayerStatsResponse[0].passerRating);
        expect(footballPlayerStats.passesDefended).toBe(footballPlayerStatsResponse[0].passesDefended);
        expect(footballPlayerStats.passingAttempts).toBe(footballPlayerStatsResponse[0].passingAttempts);
        expect(footballPlayerStats.passingTouchdowns).toBe(footballPlayerStatsResponse[0].passingTouchdowns);
        expect(footballPlayerStats.passingYards).toBe(footballPlayerStatsResponse[0].passingYards);
        expect(footballPlayerStats.points).toBe(footballPlayerStatsResponse[0].points);
        expect(footballPlayerStats.puntReturnTouchdowns).toBe(footballPlayerStatsResponse[0].puntReturnTouchdowns);
        expect(footballPlayerStats.puntReturnYardsPerPuntReturn).toBe(footballPlayerStatsResponse[0].puntReturnYardsPerPuntReturn);
        expect(footballPlayerStats.puntYardsPerPunt).toBe(footballPlayerStatsResponse[0].puntYardsPerPunt);
        expect(footballPlayerStats.quarterbackHits).toBe(footballPlayerStatsResponse[0].quarterbackHits);
        expect(footballPlayerStats.receivingTargets).toBe(footballPlayerStatsResponse[0].receivingTargets);
        expect(footballPlayerStats.receivingTouchdowns).toBe(footballPlayerStatsResponse[0].receivingTouchdowns);
        expect(footballPlayerStats.rushingAttempts).toBe(footballPlayerStatsResponse[0].rushingAttempts);
        expect(footballPlayerStats.rushingTouchdowns).toBe(footballPlayerStatsResponse[0].rushingTouchdowns);
        expect(footballPlayerStats.sackedYardsLost).toBe(footballPlayerStatsResponse[0].sackedYardsLost);
        expect(footballPlayerStats.soloTackles).toBe(footballPlayerStatsResponse[0].soloTackles);
        expect(footballPlayerStats.tacklesForLoss).toBe(footballPlayerStatsResponse[0].tacklesForLoss);
        expect(footballPlayerStats.totalFumbles).toBe(footballPlayerStatsResponse[0].totalFumbles);
        expect(footballPlayerStats.totalKickReturnYards).toBe(footballPlayerStatsResponse[0].totalKickReturnYards);
        expect(footballPlayerStats.totalPuntReturnYards).toBe(footballPlayerStatsResponse[0].totalPuntReturnYards);
        expect(footballPlayerStats.totalPuntYards).toBe(footballPlayerStatsResponse[0].totalPuntYards);
        expect(footballPlayerStats.totalReceivingYards).toBe(footballPlayerStatsResponse[0].totalReceivingYards);
        expect(footballPlayerStats.totalReceptions).toBe(footballPlayerStatsResponse[0].totalReceptions);
        expect(footballPlayerStats.totalSacks).toBe(footballPlayerStatsResponse[0].totalSacks);
        expect(footballPlayerStats.totalTackles).toBe(footballPlayerStatsResponse[0].totalTackles);
        expect(footballPlayerStats.touchbacks).toBe(footballPlayerStatsResponse[0].touchbacks);
        expect(footballPlayerStats.yardsPerPassAttempt).toBe(footballPlayerStatsResponse[0].yardsPerPassAttempt);
    });

    test('call update footballPlayerStats', async () => {
        var footballPlayerStatsResponse: FootballPlayerStats[] = await callUpdateFootballPlayerStatsRestEndpoints(
            updatedFootballPlayerStats, env, domain);
        expect(updatedFootballPlayerStats.player).toBe(footballPlayerStatsResponse[0].player);
        expect(updatedFootballPlayerStats.game).toBe(footballPlayerStatsResponse[0].game);
        expect(updatedFootballPlayerStats.adjustedQbr).toBe(footballPlayerStatsResponse[0].adjustedQbr);
        expect(updatedFootballPlayerStats.averageYardsPerReception).toBe(footballPlayerStatsResponse[0].averageYardsPerReception);
        expect(updatedFootballPlayerStats.averages).toBe(footballPlayerStatsResponse[0].averages);
        expect(updatedFootballPlayerStats.completions).toBe(footballPlayerStatsResponse[0].completions);
        expect(updatedFootballPlayerStats.defensiveTouchdowns).toBe(footballPlayerStatsResponse[0].defensiveTouchdowns);
        expect(updatedFootballPlayerStats.extraPointAttempts).toBe(footballPlayerStatsResponse[0].extraPointAttempts);
        expect(updatedFootballPlayerStats.extraPoints).toBe(footballPlayerStatsResponse[0].extraPoints);
        expect(updatedFootballPlayerStats.fieldGoalPercentage).toBe(footballPlayerStatsResponse[0].fieldGoalPercentage);
        expect(updatedFootballPlayerStats.fumblesLost).toBe(footballPlayerStatsResponse[0].fumblesLost);
        expect(updatedFootballPlayerStats.fumblesRecovered).toBe(footballPlayerStatsResponse[0].fumblesRecovered);
        expect(updatedFootballPlayerStats.in20).toBe(footballPlayerStatsResponse[0].in20);
        expect(updatedFootballPlayerStats.interceptionTouchdowns).toBe(footballPlayerStatsResponse[0].interceptionTouchdowns);
        expect(updatedFootballPlayerStats.interceptionYards).toBe(footballPlayerStatsResponse[0].interceptionYards);
        expect(updatedFootballPlayerStats.interceptions).toBe(footballPlayerStatsResponse[0].interceptions);
        expect(updatedFootballPlayerStats.interceptionsThrown).toBe(footballPlayerStatsResponse[0].interceptionsThrown);
        expect(updatedFootballPlayerStats.kickReturnTouchdowns).toBe(footballPlayerStatsResponse[0].kickReturnTouchdowns);
        expect(updatedFootballPlayerStats.longestFieldGoal).toBe(footballPlayerStatsResponse[0].longestFieldGoal);
        expect(updatedFootballPlayerStats.longestKickReturnYards).toBe(footballPlayerStatsResponse[0].longestKickReturnYards);
        expect(updatedFootballPlayerStats.longestPuntReturnYards).toBe(footballPlayerStatsResponse[0].longestPuntReturnYards);
        expect(updatedFootballPlayerStats.longestPuntYards).toBe(footballPlayerStatsResponse[0].longestPuntYards);
        expect(updatedFootballPlayerStats.longestReception).toBe(footballPlayerStatsResponse[0].longestReception);
        expect(updatedFootballPlayerStats.longestRun).toBe(footballPlayerStatsResponse[0].longestRun);
        expect(updatedFootballPlayerStats.numberOfFieldGoals).toBe(footballPlayerStatsResponse[0].numberOfFieldGoals);
        expect(updatedFootballPlayerStats.numberOfKickReturns).toBe(footballPlayerStatsResponse[0].numberOfKickReturns);
        expect(updatedFootballPlayerStats.numberOfPuntReturns).toBe(footballPlayerStatsResponse[0].numberOfPuntReturns);
        expect(updatedFootballPlayerStats.numberOfPunts).toBe(footballPlayerStatsResponse[0].numberOfPunts);
        expect(updatedFootballPlayerStats.numberOfTimesSacked).toBe(footballPlayerStatsResponse[0].numberOfTimesSacked);
        expect(updatedFootballPlayerStats.numberOfYardsPerKickReturn).toBe(footballPlayerStatsResponse[0].numberOfYardsPerKickReturn);
        expect(updatedFootballPlayerStats.passerRating).toBe(footballPlayerStatsResponse[0].passerRating);
        expect(updatedFootballPlayerStats.passesDefended).toBe(footballPlayerStatsResponse[0].passesDefended);
        expect(updatedFootballPlayerStats.passingAttempts).toBe(footballPlayerStatsResponse[0].passingAttempts);
        expect(updatedFootballPlayerStats.passingTouchdowns).toBe(footballPlayerStatsResponse[0].passingTouchdowns);
        expect(updatedFootballPlayerStats.passingYards).toBe(footballPlayerStatsResponse[0].passingYards);
        expect(updatedFootballPlayerStats.points).toBe(footballPlayerStatsResponse[0].points);
        expect(updatedFootballPlayerStats.puntReturnTouchdowns).toBe(footballPlayerStatsResponse[0].puntReturnTouchdowns);
        expect(updatedFootballPlayerStats.puntReturnYardsPerPuntReturn).toBe(footballPlayerStatsResponse[0].puntReturnYardsPerPuntReturn);
        expect(updatedFootballPlayerStats.puntYardsPerPunt).toBe(footballPlayerStatsResponse[0].puntYardsPerPunt);
        expect(updatedFootballPlayerStats.quarterbackHits).toBe(footballPlayerStatsResponse[0].quarterbackHits);
        expect(updatedFootballPlayerStats.receivingTargets).toBe(footballPlayerStatsResponse[0].receivingTargets);
        expect(updatedFootballPlayerStats.receivingTouchdowns).toBe(footballPlayerStatsResponse[0].receivingTouchdowns);
        expect(updatedFootballPlayerStats.rushingAttempts).toBe(footballPlayerStatsResponse[0].rushingAttempts);
        expect(updatedFootballPlayerStats.rushingTouchdowns).toBe(footballPlayerStatsResponse[0].rushingTouchdowns);
        expect(updatedFootballPlayerStats.sackedYardsLost).toBe(footballPlayerStatsResponse[0].sackedYardsLost);
        expect(updatedFootballPlayerStats.soloTackles).toBe(footballPlayerStatsResponse[0].soloTackles);
        expect(updatedFootballPlayerStats.tacklesForLoss).toBe(footballPlayerStatsResponse[0].tacklesForLoss);
        expect(updatedFootballPlayerStats.totalFumbles).toBe(footballPlayerStatsResponse[0].totalFumbles);
        expect(updatedFootballPlayerStats.totalKickReturnYards).toBe(footballPlayerStatsResponse[0].totalKickReturnYards);
        expect(updatedFootballPlayerStats.totalPuntReturnYards).toBe(footballPlayerStatsResponse[0].totalPuntReturnYards);
        expect(updatedFootballPlayerStats.totalPuntYards).toBe(footballPlayerStatsResponse[0].totalPuntYards);
        expect(updatedFootballPlayerStats.totalReceivingYards).toBe(footballPlayerStatsResponse[0].totalReceivingYards);
        expect(updatedFootballPlayerStats.totalReceptions).toBe(footballPlayerStatsResponse[0].totalReceptions);
        expect(updatedFootballPlayerStats.totalSacks).toBe(footballPlayerStatsResponse[0].totalSacks);
        expect(updatedFootballPlayerStats.totalTackles).toBe(footballPlayerStatsResponse[0].totalTackles);
        expect(updatedFootballPlayerStats.touchbacks).toBe(footballPlayerStatsResponse[0].touchbacks);
        expect(updatedFootballPlayerStats.yardsPerPassAttempt).toBe(footballPlayerStatsResponse[0].yardsPerPassAttempt);
    });

    test('call delete footballPlayerStats', async () => {
        var deleteFootballPlayerStatsResponse: boolean = await callDeleteFootballPlayerStatsRestEndpointsByFootballPlayerStatsId(
            footballPlayerStatsId || 1, env, domain);
        expect(true).toBe(deleteFootballPlayerStatsResponse);

        var deleteGameResponse: boolean = await callDeleteGameRestEndpointsByGameId(
            game.id || 1, env, domain);
        expect(true).toBe(deleteGameResponse);

        var deleteStreetAddressResponse = await callDeleteStreetAddressRestEndpointsByStreetAddressId(
            streetAddress.id || 1, env, domain);
        expect(deleteStreetAddressResponse).toBe(true);

        var deleteCityResponse = await callDeleteCityRestEndpointsByCityId(
            city.id || 1, env, domain);
        expect(deleteCityResponse).toBe(true);

        var deleteStateResponse = await callDeleteStateRestEndpointsByStateId(
            state.id || 1, env, domain);
        expect(deleteStateResponse).toBe(true);

        var deleteZipCodeRespones = await callDeleteZipCodeRestEndpointsByZipCodeId(
            zipCode.id || 1, env, domain);
        expect(deleteZipCodeRespones).toBe(deleteZipCodeRespones);

        var deleteCountryResponse = await callDeleteCountryRestEndpointsByCountryId(
            country.id || 1, env, domain);
        expect(deleteCountryResponse).toBe(true);

        var deleteLocationResponse = await callDeleteLocationRestEndpointsByLocationId(
            location.id || 1, env, domain);
        expect(deleteLocationResponse).toBe(true);

        var deleteGameStatusResponse = await callDeleteGameStatusRestEndpointsByGameStatusId(
            gameStatus.id || 1, env, domain);
        expect(deleteGameStatusResponse).toBe(true);

        var deleteGameTypeResponse = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameType.id || 1, env, domain);
        expect(deleteGameTypeResponse).toBe(true);

        var deleteSeasonTypeResponse = await callDeleteSeasonTypeRestEndpointsBySeasonTypeId(
            seasonType.id || 1, env, domain);
        expect(deleteSeasonTypeResponse).toBe(true);

        var deletePlayerResponse: boolean = await callDeletePlayerRestEndpointsByPlayerId(
            player.id || 1, env, domain);
        expect(true).toBe(deletePlayerResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});