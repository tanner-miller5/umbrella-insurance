import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId } from "../../../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { callCreateGameStatusRestEndpoints, callDeleteGameStatusRestEndpointsByGameStatusId } from "../../../../../../../endpoints/rest/games/gameStatuses/v1/GameStatusRestEndpoints";
import { callCreateGameRestEndpoints, callDeleteGameRestEndpointsByGameId } from "../../../../../../../endpoints/rest/games/v1/GameRestEndpoints";
import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateCityRestEndpoints, callDeleteCityRestEndpointsByCityId } from "../../../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { callCreateCountryRestEndpoints, callDeleteCountryRestEndpointsByCountryId } from "../../../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { callCreateLocationRestEndpoints, callDeleteLocationRestEndpointsByLocationId } from "../../../../../../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { callCreateStateRestEndpoints, callDeleteStateRestEndpointsByStateId } from "../../../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { callCreateStreetAddressRestEndpoints, callDeleteStreetAddressRestEndpointsByStreetAddressId } from "../../../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { callCreateZipCodeRestEndpoints, callDeleteZipCodeRestEndpointsByZipCodeId } from "../../../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { callCreateOrderTypeRestEndpoints, callDeleteOrderTypeRestEndpointsByOrderTypeId } from "../../../../../../../endpoints/rest/orderTypes/v1/OrderTypeRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateSeasonTypeRestEndpoints, callDeleteSeasonTypeRestEndpointsBySeasonTypeId } from "../../../../../../../endpoints/rest/seasons/seasonTypes/v1/SeasonTypeRestEndpoints";
import { callCreateUnitRestEndpoints, callDeleteUnitRestEndpointsByUnitId } from "../../../../../../../endpoints/rest/units/v1/UnitRestEndpoints";
import { callCreateBetTypeRestEndpoints, callDeleteBetTypeRestEndpointsByBetTypeId } from "../../../../../../../endpoints/rest/users/bets/betTypes/v1/BetTypeRestEndpoints";
import { callCreatePendingBetStateRestEndpoints, callDeletePendingBetStateRestEndpointsByPendingBetStateId } from "../../../../../../../endpoints/rest/users/bets/pendingBets/pendingBetStates/v1/PendingBetStateRestEndpoints";
import { callCreatePendingBetRestEndpoints, callReadPendingBetRestEndpointsByPendingBetId, callDeletePendingBetRestEndpointsByPendingBetId, callUpdatePendingBetRestEndpoints } from "../../../../../../../endpoints/rest/users/bets/pendingBets/v1/PendingBetRestEndpoints";
import { callCreateFeeRestEndpoints, callDeleteFeeRestEndpointsByFeeId } from "../../../../../../../endpoints/rest/users/fees/v1/FeeRestEndpoints";
import { SessionCreateRestRequest } from "../../../../../../../endpoints/rest/users/sessions/v1/SessionCreateRestRequest";
import { callCreateSessionRestEndpoints, callDeleteSessionRestEndpointsBySessionId } from "../../../../../../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { Device } from "../../../../../../../models/devices/v1/Device";
import { GameStatus } from "../../../../../../../models/games/gameStatuses/v1/GameStatus";
import { Game } from "../../../../../../../models/games/v1/Game";
import { GameType } from "../../../../../../../models/gameTypes/v1/GameType";
import { City } from "../../../../../../../models/geographies/cities/v1/City";
import { Country } from "../../../../../../../models/geographies/countries/v1/Country";
import { Location } from "../../../../../../../models/geographies/locations/v1/Location";
import { State } from "../../../../../../../models/geographies/states/v1/State";
import { StreetAddress } from "../../../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../../../../../../models/geographies/zipCodes/v1/ZipCode";
import { OrderType } from "../../../../../../../models/orderTypes/v1/OrderType";
import { Person } from "../../../../../../../models/people/v1/Person";
import { SeasonType } from "../../../../../../../models/seasons/seasonTypes/v1/SeasonType";
import { Unit } from "../../../../../../../models/units/v1/Unit";
import { BetType } from "../../../../../../../models/users/bets/betTypes/v1/BetType";
import { PendingBetState } from "../../../../../../../models/users/bets/pendingBets/pendingBetStates/v1/PendingBetState";
import { PendingBet } from "../../../../../../../models/users/bets/pendingBets/v1/PendingBet";
import { Fee } from "../../../../../../../models/users/fees/v1/Fee";
import { Session } from "../../../../../../../models/users/sessions/v1/Session";
import { User } from "../../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('pendingBet endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var pendingBetId: number | undefined; 

    var fee: Fee = new Fee();
    fee.feeName = "1";
    fee.feePercent = 1;
    fee.fixedFee = 1;

    var orderType: OrderType = new OrderType();
    orderType.orderTypeName = "1";

    var pendingBetState: PendingBetState = new PendingBetState();
    pendingBetState.pendingBetStateName = "1";

    var unit: Unit = new Unit();
    unit.unitName = "1";

    var sessionCode: string = "11";
    var startDateTime = "2022-11-11T11:11:11Z";
    var endDateTime = "2020-11-11T11:11:11Z";
    var minutesToExpire:number = 2;
    var createdDateTime = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;
    var deviceName: string = "1234";
    var ipAddress: string = "2";
    var userAgent: string = "3";
    var person: Person = new Person();
    var user: User = new User();
    var device: Device = new Device();

    var session: Session = new Session();

    person.ssn = "123";
    person.dateOfBirth = "1111-11-11";
    person.surname = "last";
    person.middleName = "middle";
    person.firstName = "first";

    user.createdDateTime = createdDateTime;
    user.emailAddress = emailAddress;
    user.phoneNumber = phoneNumber;
    user.username = username;
    user.isEmailAddressVerified = isEmailAddressVerified;
    user.isPhoneNumberVerified = isPhoneNumberVerified;

    device.deviceName = deviceName;
    device.ipAddress = ipAddress;
    device.userAgent = userAgent;
    device.createdDateTime = createdDateTime;

    session.sessionCode = sessionCode;
    session.startDateTime = startDateTime;
    session.endDateTime = endDateTime;
    session.minutesToExpire = minutesToExpire;


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

    game.dateTime = dateTime;
    game.gameName = gameName;

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

    var betType: BetType = new BetType();
    betType.betTypeName = "1";

    var pendingBet: PendingBet = new PendingBet();
    pendingBet.pendingBetName = "1";
    pendingBet.createdDateTime = "1111-11-11T11:11:11Z";
    pendingBet.isBotBet = false;
    pendingBet.isCharityBet = false;
    pendingBet.maximumWagerAmount = 100;
    pendingBet.minimumOdds = 2;
    pendingBet.pendingBetName = "1";

    var updatedPendingBet: PendingBet = new PendingBet();
    updatedPendingBet.pendingBetName = "2";
    updatedPendingBet.createdDateTime = "1111-11-11T11:11:11Z";
    updatedPendingBet.isBotBet = false;
    updatedPendingBet.isCharityBet = false;
    updatedPendingBet.maximumWagerAmount = 100;
    updatedPendingBet.minimumOdds = 2;
    updatedPendingBet.pendingBetName = "1";

    var password: string = "5";
    var sessionCreateRestRequest: SessionCreateRestRequest = new SessionCreateRestRequest();
    sessionCreateRestRequest.username = username;
    sessionCreateRestRequest.password = password;

    test('call create pendingBet', async () => {
        var orderTypeResponse: OrderType = await callCreateOrderTypeRestEndpoints(
            orderType, env, domain);
        orderType.id = orderTypeResponse.id;
        pendingBet.orderType = orderType;
        updatedPendingBet.orderType = orderType;
        expect(orderType.orderTypeName).toBe(orderTypeResponse.orderTypeName);

        var pendingBetStateResponse: PendingBetState = await callCreatePendingBetStateRestEndpoints(
            pendingBetState, env, domain);
        pendingBetState.id = pendingBetStateResponse.id;
        pendingBet.pendingBetState = pendingBetState;
        updatedPendingBet.pendingBetState = pendingBetState;
        expect(pendingBetState.pendingBetStateName).toBe(pendingBetStateResponse.pendingBetStateName);

        var unitResponse: Unit = await callCreateUnitRestEndpoints(
            unit, env, domain);
        unit.id = unitResponse.id;
        fee.unit = unit;
        pendingBet.unit = unit;
        updatedPendingBet.unit = unit;
        expect(unit.unitName).toBe(unitResponse.unitName);

        var feeResponse: Fee = await callCreateFeeRestEndpoints(
            fee, env, domain);
        fee.id = feeResponse.id;
        pendingBet.fee = fee;
        updatedPendingBet.fee = fee;
        expect(fee.feeName).toBe(feeResponse.feeName);
        expect(fee.feePercent).toBe(feeResponse.feePercent);
        expect(fee.fixedFee).toBe(feeResponse.fixedFee);
        expect(fee.unit).toBe(feeResponse.unit);

        var createPersonResponse = await callCreatePersonRestEndpoints(person, env, domain);
        expect(createPersonResponse.dateOfBirth).toBe(person.dateOfBirth);
        expect(createPersonResponse.firstName).toBe(person.firstName);
        expect(createPersonResponse.middleName).toBe(person.middleName);
        expect(createPersonResponse.surname).toBe(person.surname);
        expect(createPersonResponse.ssn).toBe(person.ssn);
        person.id = createPersonResponse.id;
        user.person = person;

        var createUserResponse = await callCreateUserRestEndpoints(user, env, domain);
        expect(createUserResponse.emailAddress).toBe(user.emailAddress);
        expect(createUserResponse.isEmailAddressVerified).toBe(user.isEmailAddressVerified);
        expect(createUserResponse.isPhoneNumberVerified).toBe(user.isPhoneNumberVerified);
        expect(createUserResponse?.user?.person).toBe(user.person);
        expect(createUserResponse.phoneNumber).toBe(user.phoneNumber);
        expect(createUserResponse.username).toBe(user.username);
        user = createUserResponse.user || new User();
        session.user = user;
        
        var createDeviceResponse = await callCreateDeviceRestEndpoints(device, env, domain);
        expect(createDeviceResponse.createdDateTime).toBe(device.createdDateTime);
        expect(createDeviceResponse.deviceName).toBe(device.deviceName);
        expect(createDeviceResponse.ipAddress).toBe(device.ipAddress);
        expect(createDeviceResponse.userAgent).toBe(device.userAgent);
        device.id = createDeviceResponse.id;
        session.device = device;

        var createSessionResponse = await callCreateSessionRestEndpoints(sessionCreateRestRequest, env, domain);
        expect(createSessionResponse.endDateTime).toBe(session.endDateTime);
        expect(createSessionResponse.minutesToExpire).toBe(session.minutesToExpire);
        expect(createSessionResponse.sessionCode).toBe(session.sessionCode);
        expect(createSessionResponse.startDateTime).toBe(session.startDateTime);
        expect(createSessionResponse.user).toBe(session.user);
        session.id = createSessionResponse.id;

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
        pendingBet.game = game;
        updatedPendingBet.game = game;
        expect(game.gameName).toBe(gameResponse.gameName);
        expect(game.gameStatus).toBe(gameResponse.gameStatus);
        expect(game.gameType).toBe(gameResponse.gameType);
        expect(game.dateTime).toBe(gameResponse.dateTime);
        expect(game.location).toBe(gameResponse.location);
        expect(game.seasonType).toBe(gameResponse.seasonType);

        var betTypeResponse: BetType = await callCreateBetTypeRestEndpoints(
            betType, env, domain);
        betType.id = betTypeResponse.id;
        pendingBet.betType = betType;
        updatedPendingBet.betType = betType;
        expect(betType.betTypeName).toBe(betTypeResponse.betTypeName);

        var pendingBetResponse: PendingBet = await callCreatePendingBetRestEndpoints(
            pendingBet, env, domain);
        pendingBetId = pendingBetResponse.id;
        expect(pendingBet.pendingBetName).toBe(pendingBetResponse.pendingBetName);
        expect(pendingBet.accountBalanceCanceledEscrowTransaction).toBe(pendingBetResponse.accountBalanceCanceledEscrowTransaction);
        expect(pendingBet.accountBalanceEscrowTransaction).toBe(pendingBetResponse.accountBalanceEscrowTransaction);
        expect(pendingBet.betType).toBe(pendingBetResponse.betType);
        expect(pendingBet.bot).toBe(pendingBetResponse.bot);
        expect(pendingBet.canceledDateTime).toBe(pendingBetResponse.canceledDateTime);
        expect(pendingBet.charity).toBe(pendingBetResponse.charity);
        expect(pendingBet.createdDateTime).toBe(pendingBetResponse.createdDateTime);
        expect(pendingBet.fee).toBe(pendingBetResponse.fee);
        expect(pendingBet.game).toBe(pendingBetResponse.game);
        expect(pendingBet.isBotBet).toBe(pendingBetResponse.isBotBet);
        expect(pendingBet.isCharityBet).toBe(pendingBetResponse.isCharityBet);
        expect(pendingBet.maximumWagerAmount).toBe(pendingBetResponse.maximumWagerAmount);
        expect(pendingBet.minimumOdds).toBe(pendingBetResponse.minimumOdds);
        expect(pendingBet.orderType).toBe(pendingBetResponse.orderType);
        expect(pendingBet.originalPendingBet).toBe(pendingBetResponse.originalPendingBet);
        expect(pendingBet.pendingBetState).toBe(pendingBetResponse.pendingBetState);
        expect(pendingBet.pendingBetName).toBe(pendingBetResponse.pendingBetName);
        expect(pendingBet.session).toBe(pendingBetResponse.session);
        expect(pendingBet.splitPendingBet1).toBe(pendingBetResponse.splitPendingBet1);
        expect(pendingBet.splitPendingBet2).toBe(pendingBetResponse.splitPendingBet2);
        expect(pendingBet.unit).toBe(pendingBetResponse.unit);
    });

    test('call read pendingBet', async () => {
        var pendingBetResponse: PendingBet[] | undefined = await callReadPendingBetRestEndpointsByPendingBetId(
            pendingBetId || 1, env, domain) || [];
        expect(pendingBet.pendingBetName).toBe(pendingBetResponse[0].pendingBetName);
        expect(pendingBet.accountBalanceCanceledEscrowTransaction).toBe(pendingBetResponse[0].accountBalanceCanceledEscrowTransaction);
        expect(pendingBet.accountBalanceEscrowTransaction).toBe(pendingBetResponse[0].accountBalanceEscrowTransaction);
        expect(pendingBet.betType).toBe(pendingBetResponse[0].betType);
        expect(pendingBet.bot).toBe(pendingBetResponse[0].bot);
        expect(pendingBet.canceledDateTime).toBe(pendingBetResponse[0].canceledDateTime);
        expect(pendingBet.charity).toBe(pendingBetResponse[0].charity);
        expect(pendingBet.createdDateTime).toBe(pendingBetResponse[0].createdDateTime);
        expect(pendingBet.fee).toBe(pendingBetResponse[0].fee);
        expect(pendingBet.game).toBe(pendingBetResponse[0].game);
        expect(pendingBet.isBotBet).toBe(pendingBetResponse[0].isBotBet);
        expect(pendingBet.isCharityBet).toBe(pendingBetResponse[0].isCharityBet);
        expect(pendingBet.maximumWagerAmount).toBe(pendingBetResponse[0].maximumWagerAmount);
        expect(pendingBet.minimumOdds).toBe(pendingBetResponse[0].minimumOdds);
        expect(pendingBet.orderType).toBe(pendingBetResponse[0].orderType);
        expect(pendingBet.originalPendingBet).toBe(pendingBetResponse[0].originalPendingBet);
        expect(pendingBet.pendingBetState).toBe(pendingBetResponse[0].pendingBetState);
        expect(pendingBet.pendingBetName).toBe(pendingBetResponse[0].pendingBetName);
        expect(pendingBet.session).toBe(pendingBetResponse[0].session);
        expect(pendingBet.splitPendingBet1).toBe(pendingBetResponse[0].splitPendingBet1);
        expect(pendingBet.splitPendingBet2).toBe(pendingBetResponse[0].splitPendingBet2);
        expect(pendingBet.unit).toBe(pendingBetResponse[0].unit);
    });

    test('call update pendingBet', async () => {
        var pendingBetResponse: PendingBet[] = await callUpdatePendingBetRestEndpoints(
            updatedPendingBet, env, domain);
        expect(updatedPendingBet.pendingBetName).toBe(pendingBetResponse[0].pendingBetName);
        expect(updatedPendingBet.accountBalanceCanceledEscrowTransaction).toBe(pendingBetResponse[0].accountBalanceCanceledEscrowTransaction);
        expect(updatedPendingBet.accountBalanceEscrowTransaction).toBe(pendingBetResponse[0].accountBalanceEscrowTransaction);
        expect(updatedPendingBet.betType).toBe(pendingBetResponse[0].betType);
        expect(updatedPendingBet.bot).toBe(pendingBetResponse[0].bot);
        expect(updatedPendingBet.canceledDateTime).toBe(pendingBetResponse[0].canceledDateTime);
        expect(updatedPendingBet.charity).toBe(pendingBetResponse[0].charity);
        expect(updatedPendingBet.createdDateTime).toBe(pendingBetResponse[0].createdDateTime);
        expect(updatedPendingBet.fee).toBe(pendingBetResponse[0].fee);
        expect(updatedPendingBet.game).toBe(pendingBetResponse[0].game);
        expect(updatedPendingBet.isBotBet).toBe(pendingBetResponse[0].isBotBet);
        expect(updatedPendingBet.isCharityBet).toBe(pendingBetResponse[0].isCharityBet);
        expect(updatedPendingBet.maximumWagerAmount).toBe(pendingBetResponse[0].maximumWagerAmount);
        expect(updatedPendingBet.minimumOdds).toBe(pendingBetResponse[0].minimumOdds);
        expect(updatedPendingBet.orderType).toBe(pendingBetResponse[0].orderType);
        expect(updatedPendingBet.originalPendingBet).toBe(pendingBetResponse[0].originalPendingBet);
        expect(updatedPendingBet.pendingBetState).toBe(pendingBetResponse[0].pendingBetState);
        expect(updatedPendingBet.pendingBetName).toBe(pendingBetResponse[0].pendingBetName);
        expect(updatedPendingBet.session).toBe(pendingBetResponse[0].session);
        expect(updatedPendingBet.splitPendingBet1).toBe(pendingBetResponse[0].splitPendingBet1);
        expect(updatedPendingBet.splitPendingBet2).toBe(pendingBetResponse[0].splitPendingBet2);
        expect(updatedPendingBet.unit).toBe(pendingBetResponse[0].unit);
    });

    test('call delete pendingBet', async () => {
        var deletePendingBetResponse: boolean = await callDeletePendingBetRestEndpointsByPendingBetId(
            pendingBetId || 1, env, domain);
        expect(true).toBe(deletePendingBetResponse);

        var deleteBetTypeResponse: boolean = await callDeleteBetTypeRestEndpointsByBetTypeId(
            betType.id || 1, env, domain);
        expect(true).toBe(deleteBetTypeResponse);

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
            state.id || 1,
            env, domain);
        expect(deleteStateResponse).toBe(true);

        var deleteZipCodeRespones = await callDeleteZipCodeRestEndpointsByZipCodeId(
            zipCode.id || 1,
            env, domain);
        expect(deleteZipCodeRespones).toBe(deleteZipCodeRespones);

        var deleteCountryResponse = await callDeleteCountryRestEndpointsByCountryId(
            country.id || 1,
            env, domain);
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

        var deleteSessionResponse: boolean = await callDeleteSessionRestEndpointsBySessionId(session.id || 1, env, domain);
        expect(true).toBe(deleteSessionResponse);

        var deleteDeviceResponse = await callDeleteDeviceRestEndpointsByDeviceId(device.id || 1, env, domain);
        expect(deleteDeviceResponse).toBe(true);

        //var deleteUserResponse = await callDeleteUserRestEndpointsByUserId(user.userId || 1
        //expect(deleteUserResponse).toBe(true);

        var deletePersonResponse = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(deletePersonResponse).toBe(true);

        var deleteFeeResponse: boolean = await callDeleteFeeRestEndpointsByFeeId(
            fee.id || 1, env, domain);
        expect(true).toBe(deleteFeeResponse);

        var deleteUnitResponse: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unit.id || 1, env, domain);
        expect(true).toBe(deleteUnitResponse);

        var deletePendingBetStateResponse: boolean = await callDeletePendingBetStateRestEndpointsByPendingBetStateId(
            pendingBetState.id || 1, env, domain);
        expect(true).toBe(deletePendingBetStateResponse);

        var deleteOrderTypeResponse: boolean = await callDeleteOrderTypeRestEndpointsByOrderTypeId(
            orderType.id || 1, env, domain);
        expect(true).toBe(deleteOrderTypeResponse);

    });
});