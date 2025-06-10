import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateGameStatusRestEndpoints, callDeleteGameStatusRestEndpointsByGameStatusId } from "../../../../../endpoints/rest/games/gameStatuses/v1/GameStatusRestEndpoints";
import { callCreateGameRestEndpoints, callDeleteGameRestEndpointsByGameId } from "../../../../../endpoints/rest/games/v1/GameRestEndpoints";
import { callCreateCityRestEndpoints, callDeleteCityRestEndpointsByCityId } from "../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { callCreateCountryRestEndpoints, callDeleteCountryRestEndpointsByCountryId } from "../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { callCreateLocationRestEndpoints, callDeleteLocationRestEndpointsByLocationId } from "../../../../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { callCreateStateRestEndpoints, callDeleteStateRestEndpointsByStateId } from "../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { callCreateStreetAddressRestEndpoints, callDeleteStreetAddressRestEndpointsByStreetAddressId } from "../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { callCreateZipCodeRestEndpoints, callDeleteZipCodeRestEndpointsByZipCodeId } from "../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { callCreateScheduleRestEndpoints, callDeleteScheduleRestEndpointsByScheduleId, callReadScheduleRestEndpointsByScheduleId, callUpdateScheduleRestEndpoints } from "../../../../../endpoints/rest/schedules/v1/ScheduleRestEndpoints";
import { callCreateSeasonTypeRestEndpoints, callDeleteSeasonTypeRestEndpointsBySeasonTypeId } from "../../../../../endpoints/rest/seasons/seasonTypes/v1/SeasonTypeRestEndpoints";
import { callCreateSeasonRestEndpoints, callDeleteSeasonRestEndpointsBySeasonId } from "../../../../../endpoints/rest/seasons/v1/SeasonRestEndpoints";
import { GameType } from "../../../../../models/gameTypes/v1/GameType";
import { GameStatus } from "../../../../../models/games/gameStatuses/v1/GameStatus";
import { Game } from "../../../../../models/games/v1/Game";
import { City } from "../../../../../models/geographies/cities/v1/City";
import { Country } from "../../../../../models/geographies/countries/v1/Country";
import { Location } from "../../../../../models/geographies/locations/v1/Location";
import { State } from "../../../../../models/geographies/states/v1/State";
import { StreetAddress } from "../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../../../../models/geographies/zipCodes/v1/ZipCode";
import { Schedule } from "../../../../../models/schedules/v1/Schedule";
import { SeasonType } from "../../../../../models/seasons/seasonTypes/v1/SeasonType";
import { Season } from "../../../../../models/seasons/v1/Season";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('schedule endpoint tests', () => {
    var scheduleId: number | undefined; 
    
    var season: Season = new Season();
    season.seasonName = "1";
    season.endDate = "1111-11-11";
    season.startDate = "1111-11-11";

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
    game.gameName = "1";

    var updatedGame: Game = new Game();
    updatedGame.gameName = "2";

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
    
    var schedule: Schedule = new Schedule();

    var updatedSchedule: Schedule = new Schedule();

    var domain: string = "http://localhost:8080";

    test('call create schedule', async () => {
        var seasonResponse: Season = await callCreateSeasonRestEndpoints(
            season, env, domain);
        season.id = seasonResponse.id;
        schedule.season = season;
        updatedSchedule.season = season;
        expect(season.seasonName).toBe(seasonResponse.seasonName);
        expect(season.endDate).toBe(seasonResponse.endDate);
        expect(season.startDate).toBe(seasonResponse.startDate);
        
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
        updatedGame.location = location;

        var gameStatusResponse = await callCreateGameStatusRestEndpoints(gameStatus, env, domain);
        expect(gameStatusResponse.gameStatusName).toBe(gameStatus.gameStatusName);
        gameStatus.id = gameStatusResponse.id;
        game.gameStatus = gameStatus;
        updatedGame.gameStatus = gameStatus;

        var gameTypeResponse = await callCreateGameTypeRestEndpoints(gameType, env, domain);
        expect(gameTypeResponse.gameTypeName).toBe(gameType.gameTypeName);
        gameType.id = gameTypeResponse.id;
        game.gameType = gameType
        updatedGame.gameType = gameType;

        var seasonTypeResponse = await callCreateSeasonTypeRestEndpoints(seasonType, env, domain);
        expect(seasonTypeResponse.seasonTypeName).toBe(seasonType.seasonTypeName);
        seasonType.id = seasonTypeResponse.id;
        game.seasonType = seasonType;
        updatedGame.seasonType = seasonType;

        var gameResponse: Game = await callCreateGameRestEndpoints(
            game, env, domain);
        game.id = gameResponse.id;
        schedule.game = game;
        updatedSchedule.game = game;
        expect(game.gameName).toBe(gameResponse.gameName);
        expect(game.gameStatus).toBe(gameResponse.gameStatus);
        expect(game.gameType).toBe(gameResponse.gameType);
        expect(game.dateTime).toBe(gameResponse.dateTime);
        expect(game.location).toBe(gameResponse.location);
        expect(game.seasonType).toBe(gameResponse.seasonType);

        var scheduleResponse: Schedule = await callCreateScheduleRestEndpoints(
            schedule, env, domain);
        scheduleId = scheduleResponse.id;
        expect(schedule.game).toBe(scheduleResponse.game);
        expect(schedule.season).toBe(scheduleResponse.season);
    });

    test('call read schedule', async () => {
        var schedules: Schedule[] | undefined = await callReadScheduleRestEndpointsByScheduleId(
            scheduleId || 1, env, domain) || [];
        expect(schedules[0].game).toBe(schedule.game);
        expect(schedules[0].season).toBe(schedule.season);
    });

    test('call update schedule', async () => {
        var scheduleResponse: Schedule[] = await callUpdateScheduleRestEndpoints(
            updatedSchedule, env, domain);
        expect(updatedSchedule.game).toBe(scheduleResponse[0].game);
        expect(updatedSchedule.season).toBe(scheduleResponse[0].season);
    });

    test('call delete schedule', async () => {
        var deleteScheduleResponse: boolean = await callDeleteScheduleRestEndpointsByScheduleId(
            scheduleId || 1, env, domain);
        expect(true).toBe(deleteScheduleResponse);

        var deleteGameResponse: boolean = await callDeleteGameRestEndpointsByGameId(
            game.id || 1, env, domain);
        expect(true).toBe(deleteGameResponse);

        var deleteStreetAddressResponse = await callDeleteStreetAddressRestEndpointsByStreetAddressId(
            streetAddress.id || 1, env, domain);
        expect(deleteStreetAddressResponse).toBe(true);

        var deleteCityResponse = await callDeleteCityRestEndpointsByCityId(
            city.id || 1,
            env, domain);
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

        var deleteSeasonResponse: boolean = await callDeleteSeasonRestEndpointsBySeasonId(
            season.id || 1, env, domain);
        expect(true).toBe(deleteSeasonResponse);
    });
});