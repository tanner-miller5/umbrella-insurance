import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateCityRestEndpoints, callDeleteCityRestEndpointsByCityId } from "../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { callCreateCountryRestEndpoints, callDeleteCountryRestEndpointsByCountryId } from "../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { callCreateLocationRestEndpoints, callDeleteLocationRestEndpointsByLocationId } from "../../../../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { callCreateStateRestEndpoints, callDeleteStateRestEndpointsByStateId } from "../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { callCreateStreetAddressRestEndpoints, callDeleteStreetAddressRestEndpointsByStreetAddressId } from "../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { callCreateZipCodeRestEndpoints, callDeleteZipCodeRestEndpointsByZipCodeId } from "../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { callCreateLevelOfCompetitionRestEndpoints, callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionId } from "../../../../../endpoints/rest/levelOfCompetitions/v1/LevelOfCompetitionRestEndpoints";
import { callCreateSeasonRestEndpoints, callDeleteSeasonRestEndpointsBySeasonId } from "../../../../../endpoints/rest/seasons/v1/SeasonRestEndpoints";
import { callCreateTeamTypeRestEndpoints, callDeleteTeamTypeRestEndpointsByTeamTypeId } from "../../../../../endpoints/rest/teams/teamTypes/v1/TeamTypeRestEndpoints";
import { callCreateTeamRestEndpoints, callDeleteTeamRestEndpointsByTeamId, callReadTeamRestEndpointsByTeamId, callUpdateTeamRestEndpoints } from "../../../../../endpoints/rest/teams/v1/TeamRestEndpoints";
import { GameType } from "../../../../../models/gameTypes/v1/GameType";
import { City } from "../../../../../models/geographies/cities/v1/City";
import { Country } from "../../../../../models/geographies/countries/v1/Country";
import { Location } from "../../../../../models/geographies/locations/v1/Location";
import { State } from "../../../../../models/geographies/states/v1/State";
import { StreetAddress } from "../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../../../../models/geographies/zipCodes/v1/ZipCode";
import { LevelOfCompetition } from "../../../../../models/levelOfCompetitions/v1/LevelOfCompetition";
import { Season } from "../../../../../models/seasons/v1/Season";
import { TeamType } from "../../../../../models/teams/teamTypes/v1/TeamType";
import { Team } from "../../../../../models/teams/v1/Team";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('team endpoint tests', () => {
    var teamId: number | undefined; 

    var zipCode: ZipCode = new ZipCode();
    zipCode.zipCodeValue = "1";

    var state: State = new State();
    state.stateName = "1";

    var country: Country = new Country();
    country.countryName = "1";

    var city: City = new City();
    city.cityName = "1";

    var streetAddress: StreetAddress = new StreetAddress();
    streetAddress.streetAddressLine1 = "1";
    streetAddress.streetAddressLine2 = "2";
    
    var location: Location = new Location();
    location.locationName = "1";

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
    

    var updatedTeam: Team = new Team();
    updatedTeam.teamName = "2";
    updatedTeam.logoImage = [0];
    updatedTeam.logoName = "3";

    var domain: string = "http://localhost:8080";

    test('call create team', async () => {
        var levelOfCompetitionResponse: LevelOfCompetition = await callCreateLevelOfCompetitionRestEndpoints(
            levelOfCompetition, env, domain);
        levelOfCompetition.id = levelOfCompetitionResponse.id;
        team.levelOfCompetition = levelOfCompetitionResponse;
        updatedTeam.levelOfCompetition = levelOfCompetitionResponse;
        expect(levelOfCompetition.levelOfCompetitionName).toBe(levelOfCompetitionResponse.levelOfCompetitionName);
        
        var teamTypeResponse: TeamType = await callCreateTeamTypeRestEndpoints(
            teamType, env, domain);
        teamType.id = teamTypeResponse.id;
        team.teamType = teamTypeResponse;
        updatedTeam.teamType = teamTypeResponse;
        expect(teamType.teamTypeName).toBe(teamTypeResponse.teamTypeName);
        
        var gameTypeResponse: GameType = await callCreateGameTypeRestEndpoints(
            gameType, env, domain);
        gameType.id = gameTypeResponse.id;
        team.gameType = gameType;
        updatedTeam.gameType = gameType;
        expect(gameType.gameTypeName).toBe(gameTypeResponse.gameTypeName);

        var seasonResponse: Season = await callCreateSeasonRestEndpoints(
            season, env, domain);
        season.id = seasonResponse.id;
        team.firstSeason = season;
        updatedTeam.firstSeason = season;
        expect(season.seasonName).toBe(seasonResponse.seasonName);
        expect(season.endDate).toBe(seasonResponse.endDate);
        expect(season.startDate).toBe(seasonResponse.startDate);

        var zipCodeResponse: ZipCode = await callCreateZipCodeRestEndpoints(
            zipCode, env, domain);
        zipCode.id = zipCodeResponse.id;
        location.zipCode = zipCodeResponse;
        expect(zipCode.zipCodeValue).toBe(zipCodeResponse.zipCodeValue);

        var streetAddressResponse: StreetAddress = await callCreateStreetAddressRestEndpoints(
            streetAddress, env, domain);
        streetAddress.id = streetAddressResponse.id;
        location.streetAddress = streetAddressResponse;
        expect(streetAddress.streetAddressLine1).toBe(streetAddressResponse.streetAddressLine1);
        expect(streetAddress.streetAddressLine2).toBe(streetAddressResponse.streetAddressLine2);

        var stateResponse: State = await callCreateStateRestEndpoints(
            state, env, domain);
        state.id = stateResponse.id;
        location.state = stateResponse;
        expect(state.stateName).toBe(stateResponse.stateName);

        var cityResponse: City = await callCreateCityRestEndpoints(
            city, env, domain);
        city.id = cityResponse.id;
        location.city = city;
        expect(city.cityName).toBe(cityResponse.cityName);

        var countryResponse: Country = await callCreateCountryRestEndpoints(
            country, env, domain);
        country.id = countryResponse.id;
        location.country = countryResponse;
        expect(country.countryName).toBe(countryResponse.countryName);

        var locationResponse: Location = await callCreateLocationRestEndpoints(
            location, env, domain);
        location.id = locationResponse.id;
        team.location = location;
        updatedTeam.location = location;
        expect(location.locationName).toBe(locationResponse.locationName);
        
        var teamResponse: Team = await callCreateTeamRestEndpoints(
            team, env, domain);
        teamId = teamResponse.id;
        expect(team.teamName).toBe(teamResponse.teamName);
    });

    test('call read team', async () => {
        var teams: Team[] | undefined = await callReadTeamRestEndpointsByTeamId(
            teamId || 1, env, domain) || [];
        expect(teams[0].teamName).toBe(team.teamName);
    });

    test('call update team', async () => {
        var teamResponse: Team[] = await callUpdateTeamRestEndpoints(
            updatedTeam, env, domain);
        expect(updatedTeam.teamName).toBe(teamResponse[0].teamName);
    });

    test('call delete team', async () => {
        var deleteTeamResponse: boolean = await callDeleteTeamRestEndpointsByTeamId(
            teamId || 1, env, domain);
        expect(true).toBe(deleteTeamResponse);

        var deleteSeasonResponse: boolean = await callDeleteSeasonRestEndpointsBySeasonId(
            season.id || 1, env, domain);
        expect(true).toBe(deleteSeasonResponse);

        var deleteGameTypeResponse: boolean = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameType.id || 1, env, domain);
        expect(true).toBe(deleteGameTypeResponse);

        var deleteTeamTypeResponse: boolean = await callDeleteTeamTypeRestEndpointsByTeamTypeId(
            teamType.id || 1, env, domain);
        expect(true).toBe(deleteTeamTypeResponse);

        var deleteLevelOfCompetitionResponse: boolean = await callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionId(
            levelOfCompetition.id || 1, env, domain);
        expect(true).toBe(deleteLevelOfCompetitionResponse);

        var deleteLocationResponse: boolean = await callDeleteLocationRestEndpointsByLocationId(
            location.id || 1, env, domain);
        expect(true).toBe(deleteLocationResponse);

        var deleteCityResponse: boolean = await callDeleteCityRestEndpointsByCityId(
            city.id || 1, env, domain);
        expect(true).toBe(deleteCityResponse);

        var deleteCountryResponse: boolean = await callDeleteCountryRestEndpointsByCountryId(
            country.id || 1, env, domain);
        expect(true).toBe(deleteCountryResponse);

        var deleteStateResponse: boolean = await callDeleteStateRestEndpointsByStateId(
            state.id || 1, env, domain);
        expect(true).toBe(deleteStateResponse);

        var deleteStreetAddressResponse: boolean = await callDeleteStreetAddressRestEndpointsByStreetAddressId(
            streetAddress.id || 1, env, domain);
        expect(true).toBe(deleteStreetAddressResponse);

        var deleteZipCodeResponse: boolean = await callDeleteZipCodeRestEndpointsByZipCodeId(
            zipCode.id || 1, env, domain);
        expect(true).toBe(deleteZipCodeResponse);
    });
});