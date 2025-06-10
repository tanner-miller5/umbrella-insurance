import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateGameStatusRestEndpoints, callDeleteGameStatusRestEndpointsByGameStatusId } from "../../../../../../../endpoints/rest/games/gameStatuses/v1/GameStatusRestEndpoints";
import { callCreateGameRestEndpoints, callDeleteGameRestEndpointsByGameId } from "../../../../../../../endpoints/rest/games/v1/GameRestEndpoints";
import { callCreateCityRestEndpoints, callDeleteCityRestEndpointsByCityId } from "../../../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { callCreateCountryRestEndpoints, callDeleteCountryRestEndpointsByCountryId } from "../../../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { callCreateLocationRestEndpoints, callDeleteLocationRestEndpointsByLocationId } from "../../../../../../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { callCreateStateRestEndpoints, callDeleteStateRestEndpointsByStateId } from "../../../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { callCreateStreetAddressRestEndpoints, callDeleteStreetAddressRestEndpointsByStreetAddressId } from "../../../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { callCreateZipCodeRestEndpoints, callDeleteZipCodeRestEndpointsByZipCodeId } from "../../../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { callCreateLevelOfCompetitionRestEndpoints, callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionId } from "../../../../../../../endpoints/rest/levelOfCompetitions/v1/LevelOfCompetitionRestEndpoints";
import { callCreateSeasonTypeRestEndpoints, callDeleteSeasonTypeRestEndpointsBySeasonTypeId } from "../../../../../../../endpoints/rest/seasons/seasonTypes/v1/SeasonTypeRestEndpoints";
import { callCreateSeasonRestEndpoints, callDeleteSeasonRestEndpointsBySeasonId } from "../../../../../../../endpoints/rest/seasons/v1/SeasonRestEndpoints";
import { callCreateFootballTeamStatsRestEndpoints, callReadFootballTeamStatsRestEndpointsByFootballTeamStatsId, callDeleteFootballTeamStatsRestEndpointsByFootballTeamStatsId, callUpdateFootballTeamStatsRestEndpoints } from "../../../../../../../endpoints/rest/stats/teamStats/footballTeamStats/v1/FootballTeamStatsRestEndpoints";
import { callCreateTeamTypeRestEndpoints, callDeleteTeamTypeRestEndpointsByTeamTypeId } from "../../../../../../../endpoints/rest/teams/teamTypes/v1/TeamTypeRestEndpoints";
import { callCreateTeamRestEndpoints, callDeleteTeamRestEndpointsByTeamId } from "../../../../../../../endpoints/rest/teams/v1/TeamRestEndpoints";
import { GameType } from "../../../../../../../models/gameTypes/v1/GameType";
import { GameStatus } from "../../../../../../../models/games/gameStatuses/v1/GameStatus";
import { Game } from "../../../../../../../models/games/v1/Game";
import { City } from "../../../../../../../models/geographies/cities/v1/City";
import { Country } from "../../../../../../../models/geographies/countries/v1/Country";
import { Location } from "../../../../../../../models/geographies/locations/v1/Location";
import { State } from "../../../../../../../models/geographies/states/v1/State";
import { StreetAddress } from "../../../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../../../../../../models/geographies/zipCodes/v1/ZipCode";
import { LevelOfCompetition } from "../../../../../../../models/levelOfCompetitions/v1/LevelOfCompetition";
import { SeasonType } from "../../../../../../../models/seasons/seasonTypes/v1/SeasonType";
import { Season } from "../../../../../../../models/seasons/v1/Season";
import { FootballTeamStats } from "../../../../../../../models/stats/teamStats/footballTeamStats/v1/FootballTeamStats";
import { TeamType } from "../../../../../../../models/teams/teamTypes/v1/TeamType";
import { Team } from "../../../../../../../models/teams/v1/Team";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('footballTeamStats endpoint tests', () => {
    var footballTeamStatsId: number | undefined; 

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

    var levelOfCompetition: LevelOfCompetition = new LevelOfCompetition();
    levelOfCompetition.levelOfCompetitionName = "1";

    var teamType: TeamType = new TeamType();
    teamType.teamTypeName = "1";

    var gameType: GameType = new GameType();
    gameType.gameTypeName = "1";

    var season: Season = new Season();
    season.seasonName = "1";
    season.endDate = "1111-11-11";
    season.startDate = "1111-11-11";
    

    var team: Team = new Team();
    team.teamName = "1";
    team.logoImage = [1];
    team.logoName = "2";
    
    var footballTeamStats: FootballTeamStats = new FootballTeamStats();
    footballTeamStats.adjustedQbr = 1
    footballTeamStats.averageYardsPerReception = 2;
    footballTeamStats.averages = 3;
    footballTeamStats.completions = 4;
    footballTeamStats.defensiveTouchdowns = 5;
    footballTeamStats.extraPointAttempts = 6;
    footballTeamStats.extraPoints = 7;
    footballTeamStats.fieldGoalPercentage = 8;
    footballTeamStats.fumblesLost = 9;
    footballTeamStats.fumblesRecovered = 10;
    footballTeamStats.in20 = 11;
    footballTeamStats.interceptionTouchdowns = 12;
    footballTeamStats.interceptionYards = 13;
    footballTeamStats.interceptions = 14;
    footballTeamStats.interceptionsThrown = 15;
    footballTeamStats.kickReturnTouchdowns = 16;
    footballTeamStats.longestFieldGoal = 17;
    footballTeamStats.longestKickReturnYards = 18;
    footballTeamStats.longestPuntReturnYards = 19;
    footballTeamStats.longestPuntYards = 20;
    footballTeamStats.longestReception = 21;
    footballTeamStats.longestRun = 22;
    footballTeamStats.numberOfFieldGoals = 23;
    footballTeamStats.numberOfKickReturns = 24;
    footballTeamStats.numberOfPuntReturns = 25;
    footballTeamStats.numberOfPunts = 26;
    footballTeamStats.numberOfTimesSacked = 27;
    footballTeamStats.numberOfYardsPerKickReturn = 28;
    footballTeamStats.passesDefended = 30;
    footballTeamStats.passingAttempts = 31;
    footballTeamStats.passingTouchdowns = 32;
    footballTeamStats.passingYards = 33;
    footballTeamStats.points = 34;
    footballTeamStats.puntReturnTouchdowns = 35;
    footballTeamStats.puntReturnYardsPerPuntReturn = 36;
    footballTeamStats.puntYardsPerPunt = 37;
    footballTeamStats.quarterbackHits = 38;
    footballTeamStats.receivingTargets = 39;
    footballTeamStats.receivingTouchdowns = 40;
    footballTeamStats.rushingAttempts = 41;
    footballTeamStats.rushingTouchdowns = 42;
    footballTeamStats.sackedYardsLost = 43;
    footballTeamStats.soloTackles = 44;
    footballTeamStats.tacklesForLoss = 45;
    footballTeamStats.totalFumbles = 46;
    footballTeamStats.totalKickReturnYards = 47;
    footballTeamStats.totalPuntReturnYards = 48;
    footballTeamStats.totalPuntYards = 49;
    footballTeamStats.totalReceivingYards = 50;
    footballTeamStats.totalReceptions = 51;
    footballTeamStats.totalSacks = 52;
    footballTeamStats.totalTackles = 53;
    footballTeamStats.touchbacks = 54;
    footballTeamStats.yardsPerPassAttempt = 55;

    var updatedFootballTeamStats: FootballTeamStats = new FootballTeamStats();
    updatedFootballTeamStats.adjustedQbr = 101;
    updatedFootballTeamStats.averageYardsPerReception = 102;
    updatedFootballTeamStats.averages = 103;
    updatedFootballTeamStats.completions = 104;
    updatedFootballTeamStats.defensiveTouchdowns = 105;
    updatedFootballTeamStats.extraPointAttempts = 106;
    updatedFootballTeamStats.extraPoints = 107;
    updatedFootballTeamStats.fieldGoalPercentage = 108;
    updatedFootballTeamStats.fumblesLost = 109;
    updatedFootballTeamStats.fumblesRecovered = 110;
    updatedFootballTeamStats.in20 = 111;
    updatedFootballTeamStats.interceptionTouchdowns = 112;
    updatedFootballTeamStats.interceptionYards = 113;
    updatedFootballTeamStats.interceptions = 114;
    updatedFootballTeamStats.interceptionsThrown = 115;
    updatedFootballTeamStats.kickReturnTouchdowns = 116;
    updatedFootballTeamStats.longestFieldGoal = 117;
    updatedFootballTeamStats.longestKickReturnYards = 118;
    updatedFootballTeamStats.longestPuntReturnYards = 119;
    updatedFootballTeamStats.longestPuntYards = 120;
    updatedFootballTeamStats.longestReception = 121;
    updatedFootballTeamStats.longestRun = 122;
    updatedFootballTeamStats.numberOfFieldGoals = 123;
    updatedFootballTeamStats.numberOfKickReturns = 124;
    updatedFootballTeamStats.numberOfPuntReturns = 125;
    updatedFootballTeamStats.numberOfPunts = 126;
    updatedFootballTeamStats.numberOfTimesSacked = 127;
    updatedFootballTeamStats.numberOfYardsPerKickReturn = 128;
    updatedFootballTeamStats.passesDefended = 130;
    updatedFootballTeamStats.passingAttempts = 131;
    updatedFootballTeamStats.passingTouchdowns = 132;
    updatedFootballTeamStats.passingYards = 133;
    updatedFootballTeamStats.points = 134;
    updatedFootballTeamStats.puntReturnTouchdowns = 135;
    updatedFootballTeamStats.puntReturnYardsPerPuntReturn = 136;
    updatedFootballTeamStats.puntYardsPerPunt = 137;
    updatedFootballTeamStats.quarterbackHits = 138;
    updatedFootballTeamStats.receivingTargets = 139;
    updatedFootballTeamStats.receivingTouchdowns = 140;
    updatedFootballTeamStats.rushingAttempts = 141;
    updatedFootballTeamStats.rushingTouchdowns = 142;
    updatedFootballTeamStats.sackedYardsLost = 143;
    updatedFootballTeamStats.soloTackles = 144;
    updatedFootballTeamStats.tacklesForLoss = 145;
    updatedFootballTeamStats.totalFumbles = 146;
    updatedFootballTeamStats.totalKickReturnYards = 147;
    updatedFootballTeamStats.totalPuntReturnYards = 148;
    updatedFootballTeamStats.totalPuntYards = 149;
    updatedFootballTeamStats.totalReceivingYards = 150;
    updatedFootballTeamStats.totalReceptions = 151;
    updatedFootballTeamStats.totalSacks = 152;
    updatedFootballTeamStats.totalTackles = 153;
    updatedFootballTeamStats.touchbacks = 154;
    updatedFootballTeamStats.yardsPerPassAttempt = 155;

    var domain: string = "http://localhost:8080";

    test('call create footballTeamStats', async () => {
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
        zipCode.id = zipResponse.id;
        location.zipCode = zipCode;

        var countryResponse = await callCreateCountryRestEndpoints(country, env, domain);
        expect(countryResponse.countryName).toBe(country.countryName);
        country.id = countryResponse.id;
        location.country = country;

        var locationResponse = await callCreateLocationRestEndpoints(location, env, domain);
        expect(locationResponse);
        location.id = locationResponse.id;
        game.location = location;
        team.location = location;

        var gameStatusResponse = await callCreateGameStatusRestEndpoints(gameStatus, env, domain);
        expect(gameStatusResponse.gameStatusName).toBe(gameStatus.gameStatusName);
        gameStatus.id = gameStatusResponse.id;
        game.gameStatus = gameStatus;

        var gameTypeResponse = await callCreateGameTypeRestEndpoints(gameType, env, domain);
        expect(gameTypeResponse.gameTypeName).toBe(gameType.gameTypeName);
        gameType.id = gameTypeResponse.id;
        game.gameType = gameType;
        team.gameType = gameType;

        var seasonTypeResponse = await callCreateSeasonTypeRestEndpoints(seasonType, env, domain);
        expect(seasonTypeResponse.seasonTypeName).toBe(seasonType.seasonTypeName);
        seasonType.id = seasonTypeResponse.id;
        game.seasonType = seasonType;

        var gameResponse: Game = await callCreateGameRestEndpoints(
            game, env, domain);
        game.id = gameResponse.id;
        footballTeamStats.game = game;
        updatedFootballTeamStats.game = game;
        expect(game.gameName).toBe(gameResponse.gameName);
        expect(game.gameStatus).toBe(gameResponse.gameStatus);
        expect(game.gameType).toBe(gameResponse.gameType);
        expect(game.dateTime).toBe(gameResponse.dateTime);
        expect(game.location).toBe(gameResponse.location);
        expect(game.seasonType).toBe(gameResponse.seasonType);

        var levelOfCompetitionResponse: LevelOfCompetition = await callCreateLevelOfCompetitionRestEndpoints(
            levelOfCompetition, env, domain);
        levelOfCompetition.id = levelOfCompetitionResponse.id;
        team.levelOfCompetition = levelOfCompetitionResponse;
        expect(levelOfCompetition.levelOfCompetitionName).toBe(levelOfCompetitionResponse.levelOfCompetitionName);
        
        var teamTypeResponse: TeamType = await callCreateTeamTypeRestEndpoints(
            teamType, env, domain);
        teamType.id = teamTypeResponse.id;
        team.teamType = teamTypeResponse;
        expect(teamType.teamTypeName).toBe(teamTypeResponse.teamTypeName);

        var seasonResponse: Season = await callCreateSeasonRestEndpoints(
            season, env, domain);
        season.id = seasonResponse.id;
        team.firstSeason = season;
        expect(season.seasonName).toBe(seasonResponse.seasonName);
        expect(season.endDate).toBe(seasonResponse.endDate);
        expect(season.startDate).toBe(seasonResponse.startDate);

        var teamResponse: Team = await callCreateTeamRestEndpoints(
            team, env, domain);
        team.id = teamResponse.id;
        footballTeamStats.team = team;
        updatedFootballTeamStats.team = team;
        expect(team.teamName).toBe(teamResponse.teamName);

        var footballTeamStatsResponse: FootballTeamStats = await callCreateFootballTeamStatsRestEndpoints(
            footballTeamStats, env, domain);
        footballTeamStatsId = footballTeamStatsResponse.id;
        expect(footballTeamStats.team).toBe(footballTeamStatsResponse.team);
        expect(footballTeamStats.game).toBe(footballTeamStatsResponse.game);
        expect(footballTeamStats.adjustedQbr).toBe(footballTeamStatsResponse.adjustedQbr);
        expect(footballTeamStats.averageYardsPerReception).toBe(footballTeamStatsResponse.averageYardsPerReception);
        expect(footballTeamStats.averages).toBe(footballTeamStatsResponse.averages);
        expect(footballTeamStats.completions).toBe(footballTeamStatsResponse.completions);
        expect(footballTeamStats.defensiveTouchdowns).toBe(footballTeamStatsResponse.defensiveTouchdowns);
        expect(footballTeamStats.extraPointAttempts).toBe(footballTeamStatsResponse.extraPointAttempts);
        expect(footballTeamStats.extraPoints).toBe(footballTeamStatsResponse.extraPoints);
        expect(footballTeamStats.fieldGoalPercentage).toBe(footballTeamStatsResponse.fieldGoalPercentage);
        expect(footballTeamStats.fumblesLost).toBe(footballTeamStatsResponse.fumblesLost);
        expect(footballTeamStats.fumblesRecovered).toBe(footballTeamStatsResponse.fumblesRecovered);
        expect(footballTeamStats.in20).toBe(footballTeamStatsResponse.in20);
        expect(footballTeamStats.interceptionTouchdowns).toBe(footballTeamStatsResponse.interceptionTouchdowns);
        expect(footballTeamStats.interceptionYards).toBe(footballTeamStatsResponse.interceptionYards);
        expect(footballTeamStats.interceptions).toBe(footballTeamStatsResponse.interceptions);
        expect(footballTeamStats.interceptionsThrown).toBe(footballTeamStatsResponse.interceptionsThrown);
        expect(footballTeamStats.kickReturnTouchdowns).toBe(footballTeamStatsResponse.kickReturnTouchdowns);
        expect(footballTeamStats.longestFieldGoal).toBe(footballTeamStatsResponse.longestFieldGoal);
        expect(footballTeamStats.longestKickReturnYards).toBe(footballTeamStatsResponse.longestKickReturnYards);
        expect(footballTeamStats.longestPuntReturnYards).toBe(footballTeamStatsResponse.longestPuntReturnYards);
        expect(footballTeamStats.longestPuntYards).toBe(footballTeamStatsResponse.longestPuntYards);
        expect(footballTeamStats.longestReception).toBe(footballTeamStatsResponse.longestReception);
        expect(footballTeamStats.longestRun).toBe(footballTeamStatsResponse.longestRun);
        expect(footballTeamStats.numberOfFieldGoals).toBe(footballTeamStatsResponse.numberOfFieldGoals);
        expect(footballTeamStats.numberOfKickReturns).toBe(footballTeamStatsResponse.numberOfKickReturns);
        expect(footballTeamStats.numberOfPuntReturns).toBe(footballTeamStatsResponse.numberOfPuntReturns);
        expect(footballTeamStats.numberOfPunts).toBe(footballTeamStatsResponse.numberOfPunts);
        expect(footballTeamStats.numberOfTimesSacked).toBe(footballTeamStatsResponse.numberOfTimesSacked);
        expect(footballTeamStats.numberOfYardsPerKickReturn).toBe(footballTeamStatsResponse.numberOfYardsPerKickReturn);
        expect(footballTeamStats.passesDefended).toBe(footballTeamStatsResponse.passesDefended);
        expect(footballTeamStats.passingAttempts).toBe(footballTeamStatsResponse.passingAttempts);
        expect(footballTeamStats.passingTouchdowns).toBe(footballTeamStatsResponse.passingTouchdowns);
        expect(footballTeamStats.passingYards).toBe(footballTeamStatsResponse.passingYards);
        expect(footballTeamStats.points).toBe(footballTeamStatsResponse.points);
        expect(footballTeamStats.puntReturnTouchdowns).toBe(footballTeamStatsResponse.puntReturnTouchdowns);
        expect(footballTeamStats.puntReturnYardsPerPuntReturn).toBe(footballTeamStatsResponse.puntReturnYardsPerPuntReturn);
        expect(footballTeamStats.puntYardsPerPunt).toBe(footballTeamStatsResponse.puntYardsPerPunt);
        expect(footballTeamStats.quarterbackHits).toBe(footballTeamStatsResponse.quarterbackHits);
        expect(footballTeamStats.receivingTargets).toBe(footballTeamStatsResponse.receivingTargets);
        expect(footballTeamStats.receivingTouchdowns).toBe(footballTeamStatsResponse.receivingTouchdowns);
        expect(footballTeamStats.rushingAttempts).toBe(footballTeamStatsResponse.rushingAttempts);
        expect(footballTeamStats.rushingTouchdowns).toBe(footballTeamStatsResponse.rushingTouchdowns);
        expect(footballTeamStats.sackedYardsLost).toBe(footballTeamStatsResponse.sackedYardsLost);
        expect(footballTeamStats.soloTackles).toBe(footballTeamStatsResponse.soloTackles);
        expect(footballTeamStats.tacklesForLoss).toBe(footballTeamStatsResponse.tacklesForLoss);
        expect(footballTeamStats.totalFumbles).toBe(footballTeamStatsResponse.totalFumbles);
        expect(footballTeamStats.totalKickReturnYards).toBe(footballTeamStatsResponse.totalKickReturnYards);
        expect(footballTeamStats.totalPuntReturnYards).toBe(footballTeamStatsResponse.totalPuntReturnYards);
        expect(footballTeamStats.totalPuntYards).toBe(footballTeamStatsResponse.totalPuntYards);
        expect(footballTeamStats.totalReceivingYards).toBe(footballTeamStatsResponse.totalReceivingYards);
        expect(footballTeamStats.totalReceptions).toBe(footballTeamStatsResponse.totalReceptions);
        expect(footballTeamStats.totalSacks).toBe(footballTeamStatsResponse.totalSacks);
        expect(footballTeamStats.totalTackles).toBe(footballTeamStatsResponse.totalTackles);
        expect(footballTeamStats.touchbacks).toBe(footballTeamStatsResponse.touchbacks);
        expect(footballTeamStats.yardsPerPassAttempt).toBe(footballTeamStatsResponse.yardsPerPassAttempt);
    });

    test('call read footballTeamStats', async () => {
        var footballTeamStatsResponse: FootballTeamStats[] | undefined = await callReadFootballTeamStatsRestEndpointsByFootballTeamStatsId(
            footballTeamStatsId || 1, env, domain) || [];
            expect(footballTeamStats.team).toBe(footballTeamStatsResponse[0].team);
            expect(footballTeamStats.game).toBe(footballTeamStatsResponse[0].game);
            expect(footballTeamStats.adjustedQbr).toBe(footballTeamStatsResponse[0].adjustedQbr);
            expect(footballTeamStats.averageYardsPerReception).toBe(footballTeamStatsResponse[0].averageYardsPerReception);
            expect(footballTeamStats.averages).toBe(footballTeamStatsResponse[0].averages);
            expect(footballTeamStats.completions).toBe(footballTeamStatsResponse[0].completions);
            expect(footballTeamStats.defensiveTouchdowns).toBe(footballTeamStatsResponse[0].defensiveTouchdowns);
            expect(footballTeamStats.extraPointAttempts).toBe(footballTeamStatsResponse[0].extraPointAttempts);
            expect(footballTeamStats.extraPoints).toBe(footballTeamStatsResponse[0].extraPoints);
            expect(footballTeamStats.fieldGoalPercentage).toBe(footballTeamStatsResponse[0].fieldGoalPercentage);
            expect(footballTeamStats.fumblesLost).toBe(footballTeamStatsResponse[0].fumblesLost);
            expect(footballTeamStats.fumblesRecovered).toBe(footballTeamStatsResponse[0].fumblesRecovered);
            expect(footballTeamStats.in20).toBe(footballTeamStatsResponse[0].in20);
            expect(footballTeamStats.interceptionTouchdowns).toBe(footballTeamStatsResponse[0].interceptionTouchdowns);
            expect(footballTeamStats.interceptionYards).toBe(footballTeamStatsResponse[0].interceptionYards);
            expect(footballTeamStats.interceptions).toBe(footballTeamStatsResponse[0].interceptions);
            expect(footballTeamStats.interceptionsThrown).toBe(footballTeamStatsResponse[0].interceptionsThrown);
            expect(footballTeamStats.kickReturnTouchdowns).toBe(footballTeamStatsResponse[0].kickReturnTouchdowns);
            expect(footballTeamStats.longestFieldGoal).toBe(footballTeamStatsResponse[0].longestFieldGoal);
            expect(footballTeamStats.longestKickReturnYards).toBe(footballTeamStatsResponse[0].longestKickReturnYards);
            expect(footballTeamStats.longestPuntReturnYards).toBe(footballTeamStatsResponse[0].longestPuntReturnYards);
            expect(footballTeamStats.longestPuntYards).toBe(footballTeamStatsResponse[0].longestPuntYards);
            expect(footballTeamStats.longestReception).toBe(footballTeamStatsResponse[0].longestReception);
            expect(footballTeamStats.longestRun).toBe(footballTeamStatsResponse[0].longestRun);
            expect(footballTeamStats.numberOfFieldGoals).toBe(footballTeamStatsResponse[0].numberOfFieldGoals);
            expect(footballTeamStats.numberOfKickReturns).toBe(footballTeamStatsResponse[0].numberOfKickReturns);
            expect(footballTeamStats.numberOfPuntReturns).toBe(footballTeamStatsResponse[0].numberOfPuntReturns);
            expect(footballTeamStats.numberOfPunts).toBe(footballTeamStatsResponse[0].numberOfPunts);
            expect(footballTeamStats.numberOfTimesSacked).toBe(footballTeamStatsResponse[0].numberOfTimesSacked);
            expect(footballTeamStats.numberOfYardsPerKickReturn).toBe(footballTeamStatsResponse[0].numberOfYardsPerKickReturn);
            expect(footballTeamStats.passesDefended).toBe(footballTeamStatsResponse[0].passesDefended);
            expect(footballTeamStats.passingAttempts).toBe(footballTeamStatsResponse[0].passingAttempts);
            expect(footballTeamStats.passingTouchdowns).toBe(footballTeamStatsResponse[0].passingTouchdowns);
            expect(footballTeamStats.passingYards).toBe(footballTeamStatsResponse[0].passingYards);
            expect(footballTeamStats.points).toBe(footballTeamStatsResponse[0].points);
            expect(footballTeamStats.puntReturnTouchdowns).toBe(footballTeamStatsResponse[0].puntReturnTouchdowns);
            expect(footballTeamStats.puntReturnYardsPerPuntReturn).toBe(footballTeamStatsResponse[0].puntReturnYardsPerPuntReturn);
            expect(footballTeamStats.puntYardsPerPunt).toBe(footballTeamStatsResponse[0].puntYardsPerPunt);
            expect(footballTeamStats.quarterbackHits).toBe(footballTeamStatsResponse[0].quarterbackHits);
            expect(footballTeamStats.receivingTargets).toBe(footballTeamStatsResponse[0].receivingTargets);
            expect(footballTeamStats.receivingTouchdowns).toBe(footballTeamStatsResponse[0].receivingTouchdowns);
            expect(footballTeamStats.rushingAttempts).toBe(footballTeamStatsResponse[0].rushingAttempts);
            expect(footballTeamStats.rushingTouchdowns).toBe(footballTeamStatsResponse[0].rushingTouchdowns);
            expect(footballTeamStats.sackedYardsLost).toBe(footballTeamStatsResponse[0].sackedYardsLost);
            expect(footballTeamStats.soloTackles).toBe(footballTeamStatsResponse[0].soloTackles);
            expect(footballTeamStats.tacklesForLoss).toBe(footballTeamStatsResponse[0].tacklesForLoss);
            expect(footballTeamStats.totalFumbles).toBe(footballTeamStatsResponse[0].totalFumbles);
            expect(footballTeamStats.totalKickReturnYards).toBe(footballTeamStatsResponse[0].totalKickReturnYards);
            expect(footballTeamStats.totalPuntReturnYards).toBe(footballTeamStatsResponse[0].totalPuntReturnYards);
            expect(footballTeamStats.totalPuntYards).toBe(footballTeamStatsResponse[0].totalPuntYards);
            expect(footballTeamStats.totalReceivingYards).toBe(footballTeamStatsResponse[0].totalReceivingYards);
            expect(footballTeamStats.totalReceptions).toBe(footballTeamStatsResponse[0].totalReceptions);
            expect(footballTeamStats.totalSacks).toBe(footballTeamStatsResponse[0].totalSacks);
            expect(footballTeamStats.totalTackles).toBe(footballTeamStatsResponse[0].totalTackles);
            expect(footballTeamStats.touchbacks).toBe(footballTeamStatsResponse[0].touchbacks);
            expect(footballTeamStats.yardsPerPassAttempt).toBe(footballTeamStatsResponse[0].yardsPerPassAttempt);
    });

    test('call update footballTeamStats', async () => {
        var footballTeamStatsResponse: FootballTeamStats[] = await callUpdateFootballTeamStatsRestEndpoints(
            updatedFootballTeamStats, env, domain);
            expect(updatedFootballTeamStats.team).toBe(footballTeamStatsResponse[0].team);
            expect(updatedFootballTeamStats.game).toBe(footballTeamStatsResponse[0].game);
            expect(updatedFootballTeamStats.adjustedQbr).toBe(footballTeamStatsResponse[0].adjustedQbr);
            expect(updatedFootballTeamStats.averageYardsPerReception).toBe(footballTeamStatsResponse[0].averageYardsPerReception);
            expect(updatedFootballTeamStats.averages).toBe(footballTeamStatsResponse[0].averages);
            expect(updatedFootballTeamStats.completions).toBe(footballTeamStatsResponse[0].completions);
            expect(updatedFootballTeamStats.defensiveTouchdowns).toBe(footballTeamStatsResponse[0].defensiveTouchdowns);
            expect(updatedFootballTeamStats.extraPointAttempts).toBe(footballTeamStatsResponse[0].extraPointAttempts);
            expect(updatedFootballTeamStats.extraPoints).toBe(footballTeamStatsResponse[0].extraPoints);
            expect(updatedFootballTeamStats.fieldGoalPercentage).toBe(footballTeamStatsResponse[0].fieldGoalPercentage);
            expect(updatedFootballTeamStats.fumblesLost).toBe(footballTeamStatsResponse[0].fumblesLost);
            expect(updatedFootballTeamStats.fumblesRecovered).toBe(footballTeamStatsResponse[0].fumblesRecovered);
            expect(updatedFootballTeamStats.in20).toBe(footballTeamStatsResponse[0].in20);
            expect(updatedFootballTeamStats.interceptionTouchdowns).toBe(footballTeamStatsResponse[0].interceptionTouchdowns);
            expect(updatedFootballTeamStats.interceptionYards).toBe(footballTeamStatsResponse[0].interceptionYards);
            expect(updatedFootballTeamStats.interceptions).toBe(footballTeamStatsResponse[0].interceptions);
            expect(updatedFootballTeamStats.interceptionsThrown).toBe(footballTeamStatsResponse[0].interceptionsThrown);
            expect(updatedFootballTeamStats.kickReturnTouchdowns).toBe(footballTeamStatsResponse[0].kickReturnTouchdowns);
            expect(updatedFootballTeamStats.longestFieldGoal).toBe(footballTeamStatsResponse[0].longestFieldGoal);
            expect(updatedFootballTeamStats.longestKickReturnYards).toBe(footballTeamStatsResponse[0].longestKickReturnYards);
            expect(updatedFootballTeamStats.longestPuntReturnYards).toBe(footballTeamStatsResponse[0].longestPuntReturnYards);
            expect(updatedFootballTeamStats.longestPuntYards).toBe(footballTeamStatsResponse[0].longestPuntYards);
            expect(updatedFootballTeamStats.longestReception).toBe(footballTeamStatsResponse[0].longestReception);
            expect(updatedFootballTeamStats.longestRun).toBe(footballTeamStatsResponse[0].longestRun);
            expect(updatedFootballTeamStats.numberOfFieldGoals).toBe(footballTeamStatsResponse[0].numberOfFieldGoals);
            expect(updatedFootballTeamStats.numberOfKickReturns).toBe(footballTeamStatsResponse[0].numberOfKickReturns);
            expect(updatedFootballTeamStats.numberOfPuntReturns).toBe(footballTeamStatsResponse[0].numberOfPuntReturns);
            expect(updatedFootballTeamStats.numberOfPunts).toBe(footballTeamStatsResponse[0].numberOfPunts);
            expect(updatedFootballTeamStats.numberOfTimesSacked).toBe(footballTeamStatsResponse[0].numberOfTimesSacked);
            expect(updatedFootballTeamStats.numberOfYardsPerKickReturn).toBe(footballTeamStatsResponse[0].numberOfYardsPerKickReturn);
            expect(updatedFootballTeamStats.passesDefended).toBe(footballTeamStatsResponse[0].passesDefended);
            expect(updatedFootballTeamStats.passingAttempts).toBe(footballTeamStatsResponse[0].passingAttempts);
            expect(updatedFootballTeamStats.passingTouchdowns).toBe(footballTeamStatsResponse[0].passingTouchdowns);
            expect(updatedFootballTeamStats.passingYards).toBe(footballTeamStatsResponse[0].passingYards);
            expect(updatedFootballTeamStats.points).toBe(footballTeamStatsResponse[0].points);
            expect(updatedFootballTeamStats.puntReturnTouchdowns).toBe(footballTeamStatsResponse[0].puntReturnTouchdowns);
            expect(updatedFootballTeamStats.puntReturnYardsPerPuntReturn).toBe(footballTeamStatsResponse[0].puntReturnYardsPerPuntReturn);
            expect(updatedFootballTeamStats.puntYardsPerPunt).toBe(footballTeamStatsResponse[0].puntYardsPerPunt);
            expect(updatedFootballTeamStats.quarterbackHits).toBe(footballTeamStatsResponse[0].quarterbackHits);
            expect(updatedFootballTeamStats.receivingTargets).toBe(footballTeamStatsResponse[0].receivingTargets);
            expect(updatedFootballTeamStats.receivingTouchdowns).toBe(footballTeamStatsResponse[0].receivingTouchdowns);
            expect(updatedFootballTeamStats.rushingAttempts).toBe(footballTeamStatsResponse[0].rushingAttempts);
            expect(updatedFootballTeamStats.rushingTouchdowns).toBe(footballTeamStatsResponse[0].rushingTouchdowns);
            expect(updatedFootballTeamStats.sackedYardsLost).toBe(footballTeamStatsResponse[0].sackedYardsLost);
            expect(updatedFootballTeamStats.soloTackles).toBe(footballTeamStatsResponse[0].soloTackles);
            expect(updatedFootballTeamStats.tacklesForLoss).toBe(footballTeamStatsResponse[0].tacklesForLoss);
            expect(updatedFootballTeamStats.totalFumbles).toBe(footballTeamStatsResponse[0].totalFumbles);
            expect(updatedFootballTeamStats.totalKickReturnYards).toBe(footballTeamStatsResponse[0].totalKickReturnYards);
            expect(updatedFootballTeamStats.totalPuntReturnYards).toBe(footballTeamStatsResponse[0].totalPuntReturnYards);
            expect(updatedFootballTeamStats.totalPuntYards).toBe(footballTeamStatsResponse[0].totalPuntYards);
            expect(updatedFootballTeamStats.totalReceivingYards).toBe(footballTeamStatsResponse[0].totalReceivingYards);
            expect(updatedFootballTeamStats.totalReceptions).toBe(footballTeamStatsResponse[0].totalReceptions);
            expect(updatedFootballTeamStats.totalSacks).toBe(footballTeamStatsResponse[0].totalSacks);
            expect(updatedFootballTeamStats.totalTackles).toBe(footballTeamStatsResponse[0].totalTackles);
            expect(updatedFootballTeamStats.touchbacks).toBe(footballTeamStatsResponse[0].touchbacks);
            expect(updatedFootballTeamStats.yardsPerPassAttempt).toBe(footballTeamStatsResponse[0].yardsPerPassAttempt);
    });

    test('call delete footballTeamStats', async () => {
        var deleteFootballTeamStatsResponse: boolean = await callDeleteFootballTeamStatsRestEndpointsByFootballTeamStatsId(
            footballTeamStatsId || 1, env, domain);
        expect(true).toBe(deleteFootballTeamStatsResponse);

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

        var deleteTeamResponse: boolean = await callDeleteTeamRestEndpointsByTeamId(
            team.id || 1, env, domain);
        expect(true).toBe(deleteTeamResponse);

        var deleteSeasonResponse: boolean = await callDeleteSeasonRestEndpointsBySeasonId(
            season.id || 1, env, domain);
        expect(true).toBe(deleteSeasonResponse);

        var deleteTeamTypeResponse: boolean = await callDeleteTeamTypeRestEndpointsByTeamTypeId(
            teamType.id || 1, env, domain);
        expect(true).toBe(deleteTeamTypeResponse);

        var deleteLevelOfCompetitionResponse: boolean = await callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionId(
            levelOfCompetition.id || 1, env, domain);
        expect(true).toBe(deleteLevelOfCompetitionResponse);
    });
});