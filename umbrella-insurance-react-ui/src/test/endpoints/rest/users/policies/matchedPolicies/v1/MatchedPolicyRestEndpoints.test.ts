import { useSelector } from "react-redux";
import { RootState } from "../../../../../../../redux/store/Store";
import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId } from "../../../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { callCreateCityRestEndpoints, callDeleteCityRestEndpointsByCityId } from "../../../../../../../endpoints/rest/geographies/cities/v1/CityRestEndpoints";
import { callCreateCountryRestEndpoints, callDeleteCountryRestEndpointsByCountryId } from "../../../../../../../endpoints/rest/geographies/countries/v1/CountryRestEndpoints";
import { callCreateLocationRestEndpoints, callDeleteLocationRestEndpointsByLocationId } from "../../../../../../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { callCreateStateRestEndpoints, callDeleteStateRestEndpointsByStateId } from "../../../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { callCreateStreetAddressRestEndpoints, callDeleteStreetAddressRestEndpointsByStreetAddressId } from "../../../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { callCreateZipCodeRestEndpoints, callDeleteZipCodeRestEndpointsByZipCodeId } from "../../../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreatePerilRestEndpoints, callDeletePerilRestEndpointsByPerilId } from "../../../../../../../endpoints/rest/perils/v1/PerilRestEndpoints";
import { callCreateUnitRestEndpoints, callDeleteUnitRestEndpointsByUnitId } from "../../../../../../../endpoints/rest/units/v1/UnitRestEndpoints";
import { callCreateFeeRestEndpoints, callDeleteFeeRestEndpointsByFeeId } from "../../../../../../../endpoints/rest/users/fees/v1/FeeRestEndpoints";
import { callCreateMatchedPolicyStateRestEndpoints, callDeleteMatchedPolicyStateRestEndpointsByMatchedPolicyStateId } from "../../../../../../../endpoints/rest/users/policies/matchedPolicies/matchedPolicyStates/v1/MatchedPolicyStateRestEndpoints";
import { callCreateMatchedPolicyRestEndpoints, callReadMatchedPolicyRestEndpointsByMatchedPolicyId, callDeleteMatchedPolicyRestEndpointsByMatchedPolicyId, callUpdateMatchedPolicyRestEndpoints } from "../../../../../../../endpoints/rest/users/policies/matchedPolicies/v1/MatchedPolicyRestEndpoints";
import { callCreatePendingPolicyStateRestEndpoints, callDeletePendingPolicyStateRestEndpointsByPendingPolicyStateId } from "../../../../../../../endpoints/rest/users/policies/pendingPolicies/pendingPolicyStates/v1/PendingPolicyStateRestEndpoints";
import { callCreatePendingPolicyRestEndpoints, callDeletePendingPolicyRestEndpointsByPendingPolicyId } from "../../../../../../../endpoints/rest/users/policies/pendingPolicies/v1/PendingPolicyRestEndpoints";
import { SessionCreateRestRequest } from "../../../../../../../endpoints/rest/users/sessions/v1/SessionCreateRestRequest";
import { callCreateSessionRestEndpoints, callDeleteSessionRestEndpointsBySessionId } from "../../../../../../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { Device } from "../../../../../../../models/devices/v1/Device";
import { City } from "../../../../../../../models/geographies/cities/v1/City";
import { Country } from "../../../../../../../models/geographies/countries/v1/Country";
import { Location } from "../../../../../../../models/geographies/locations/v1/Location";
import { State } from "../../../../../../../models/geographies/states/v1/State";
import { StreetAddress } from "../../../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../../../../../../models/geographies/zipCodes/v1/ZipCode";
import { Person } from "../../../../../../../models/people/v1/Person";
import { Peril } from "../../../../../../../models/perils/v1/Peril";
import { Unit } from "../../../../../../../models/units/v1/Unit";
import { Fee } from "../../../../../../../models/users/fees/v1/Fee";
import { MatchedPolicyState } from "../../../../../../../models/users/policies/matchedPolicies/matchedPolicyStates/v1/MatchedPolicyState";
import { MatchedPolicy } from "../../../../../../../models/users/policies/matchedPolicies/v1/MatchedPolicy";
import { PendingPolicyState } from "../../../../../../../models/users/policies/pendingPolicies/pendingPolicyStates/v1/PendingPolicyState";
import { PendingPolicy } from "../../../../../../../models/users/policies/pendingPolicies/v1/PendingPolicy";
import { Session } from "../../../../../../../models/users/sessions/v1/Session";
import { User } from "../../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('matchedPolicy endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var matchedPolicyId: number | undefined; 

    var peril: Peril = new Peril();
    peril.perilName = "1";

    var pendingPolicyState: PendingPolicyState = new PendingPolicyState();
    pendingPolicyState.pendingPolicyStateName = "1";

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

    var unit: Unit = new Unit();
    unit.unitName = "1";

    var fee: Fee = new Fee();
    fee.feeName = "1";
    fee.feePercent = 1;
    fee.fixedFee = 1;

    var pendingPolicy: PendingPolicy = new PendingPolicy();
    pendingPolicy.pendingPolicyName = "1";
    pendingPolicy.canceledDateTime = "1111-11-11T11:11:11Z";
    pendingPolicy.coverageAmount = 1;
    pendingPolicy.createdDateTime = "1111-11-11T11:11:11Z";
    pendingPolicy.endDate = "1111-11-11";
    pendingPolicy.impliedProbability = 1;
    pendingPolicy.premiumAmount = 1;
    pendingPolicy.startDate = "1112-11-11";

    var matchedPolicyState: MatchedPolicyState = new MatchedPolicyState();
    matchedPolicyState.matchedPolicyStateName = "1";

    var matchedPolicy: MatchedPolicy = new MatchedPolicy();
    matchedPolicy.coverageAmount = 1;
    matchedPolicy.createdDateTime = "1111-11-11T11:11:11Z";
    matchedPolicy.impliedProbability = 1;
    matchedPolicy.premium = 1;

    var updatedMatchedPolicy: MatchedPolicy = new MatchedPolicy();
    updatedMatchedPolicy.coverageAmount = 1;
    updatedMatchedPolicy.createdDateTime = "1111-11-11T11:11:11Z";
    updatedMatchedPolicy.impliedProbability = 1;
    updatedMatchedPolicy.premium = 1;

    var password: string = "5";
    var sessionCreateRestRequest: SessionCreateRestRequest = new SessionCreateRestRequest();
    sessionCreateRestRequest.username = username;
    sessionCreateRestRequest.password = password;

    test('call create matchedPolicy', async () => {
        var perilResponse: Peril = await callCreatePerilRestEndpoints(
            peril, env, domain);
        peril.id = perilResponse.id;
        pendingPolicy.peril = peril;
        expect(peril.perilName).toBe(perilResponse.perilName);

        var pendingPolicyStateResponse: PendingPolicyState = await callCreatePendingPolicyStateRestEndpoints(
            pendingPolicyState, env, domain);
        pendingPolicyState.id = pendingPolicyStateResponse.id;
        pendingPolicy.pendingPolicyState = pendingPolicyState;
        expect(pendingPolicyState.pendingPolicyStateName).toBe(pendingPolicyStateResponse.pendingPolicyStateName);

        var createPersonResponse = await callCreatePersonRestEndpoints(person, env, domain);
        expect(createPersonResponse.dateOfBirth).toBe(person.dateOfBirth);
        expect(createPersonResponse.firstName).toBe(person.firstName);
        expect(createPersonResponse.middleName).toBe(person.middleName);
        expect(createPersonResponse.surname).toBe(person.surname);
        expect(createPersonResponse.ssn).toBe(person.ssn);
        person.id = createPersonResponse.id
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

        var createSessionResponse = await callCreateSessionRestEndpoints(
            sessionCreateRestRequest, env, domain);
        expect(createSessionResponse.endDateTime).toBe(session.endDateTime);
        expect(createSessionResponse.minutesToExpire).toBe(session.minutesToExpire);
        expect(createSessionResponse.sessionCode).toBe(session.sessionCode);
        expect(createSessionResponse.startDateTime).toBe(session.startDateTime);
        expect(createSessionResponse.user).toBe(session.user);
        session.id = createSessionResponse.id;
        pendingPolicy.session = session;

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
        pendingPolicy.location = location;
        expect(location.locationName).toBe(locationResponse.locationName);

        var unitResponse: Unit = await callCreateUnitRestEndpoints(
            unit, env, domain);
        unit = unitResponse;
        fee.unit = unitResponse;
        pendingPolicy.unit = unit;
        expect(unit.unitName).toBe(unitResponse.unitName);

        var feeResponse: Fee = await callCreateFeeRestEndpoints(
            fee, env, domain);
        fee.id = feeResponse.id;
        pendingPolicy.fee = fee;
        matchedPolicy.insuredFee = fee;
        updatedMatchedPolicy.insuredFee = fee;
        matchedPolicy.insurerFee = fee;
        updatedMatchedPolicy.insurerFee = fee;
        expect(fee.feeName).toBe(feeResponse.feeName);
        expect(fee.feePercent).toBe(feeResponse.feePercent);
        expect(fee.fixedFee).toBe(feeResponse.fixedFee);
        expect(fee.unit).toBe(feeResponse.unit);

        var pendingPolicyResponse: PendingPolicy = await callCreatePendingPolicyRestEndpoints(
            sessionCode, pendingPolicy, env, domain);
        pendingPolicy.id = pendingPolicyResponse.id;
        matchedPolicy.pendingInsuredPolicy = pendingPolicy;
        updatedMatchedPolicy.pendingInsuredPolicy = pendingPolicy;
        matchedPolicy.pendingInsurerPolicy = pendingPolicy;
        updatedMatchedPolicy.pendingInsurerPolicy = pendingPolicy;
        expect(pendingPolicy.pendingPolicyName).toBe(pendingPolicyResponse.pendingPolicyName);
        expect(pendingPolicy.canceledDateTime).toBe(pendingPolicyResponse.canceledDateTime);
        expect(pendingPolicy.coverageAmount).toBe(pendingPolicyResponse.coverageAmount);
        expect(pendingPolicy.createdDateTime).toBe(pendingPolicyResponse.createdDateTime);
        expect(pendingPolicy.endDate).toBe(pendingPolicyResponse.endDate);
        expect(pendingPolicy.impliedProbability).toBe(pendingPolicyResponse.impliedProbability);
        expect(pendingPolicy.premiumAmount).toBe(pendingPolicyResponse.premiumAmount);
        expect(pendingPolicy.startDate).toBe(pendingPolicyResponse.startDate);
        expect(pendingPolicy.accountBalanceCanceledEscrowTransaction).toBe(pendingPolicyResponse.accountBalanceCanceledEscrowTransaction);
        expect(pendingPolicy.accountBalanceEscrowTransaction).toBe(pendingPolicyResponse.accountBalanceEscrowTransaction);
        expect(pendingPolicy.fee).toBe(pendingPolicyResponse.fee);
        expect(pendingPolicy.location).toBe(pendingPolicyResponse.location);
        expect(pendingPolicy.orderType).toBe(pendingPolicyResponse.orderType);
        expect(pendingPolicy.originalPendingPolicy).toBe(pendingPolicyResponse.originalPendingPolicy);
        expect(pendingPolicy.pendingPolicyState).toBe(pendingPolicyResponse.pendingPolicyState);
        expect(pendingPolicy.pendingPolicyType).toBe(pendingPolicyResponse.pendingPolicyType);
        expect(pendingPolicy.peril).toBe(pendingPolicyResponse.peril);
        expect(pendingPolicy.session).toBe(pendingPolicyResponse.session);
        expect(pendingPolicy.splitPendingPolicy1).toBe(pendingPolicyResponse.splitPendingPolicy1);
        expect(pendingPolicy.splitPendingPolicy2).toBe(pendingPolicyResponse.splitPendingPolicy2);
        expect(pendingPolicy.unit).toBe(pendingPolicyResponse.unit);

        var matchedPolicyStateResponse: MatchedPolicyState = await callCreateMatchedPolicyStateRestEndpoints(
            matchedPolicyState, env, domain);
        matchedPolicyState.id = matchedPolicyStateResponse.id;
        matchedPolicy.matchedPolicyState = matchedPolicyState;
        updatedMatchedPolicy.matchedPolicyState = matchedPolicyState;
        expect(matchedPolicyState.matchedPolicyStateName).toBe(matchedPolicyStateResponse.matchedPolicyStateName);

        var matchedPolicyResponse: MatchedPolicy = await callCreateMatchedPolicyRestEndpoints(
            matchedPolicy, env, domain);
        matchedPolicyId = matchedPolicyResponse.id;
        expect(matchedPolicy.coverageAmount).toBe(matchedPolicyResponse.coverageAmount);
        expect(matchedPolicy.createdDateTime).toBe(matchedPolicyResponse.createdDateTime);
        expect(matchedPolicy.impliedProbability).toBe(matchedPolicyResponse.impliedProbability);
        expect(matchedPolicy.premium).toBe(matchedPolicyResponse.premium);
        expect(matchedPolicy.insuredFee).toBe(matchedPolicyResponse.insuredFee);
        expect(matchedPolicy.insurerFee).toBe(matchedPolicyResponse.insurerFee);
        expect(matchedPolicy.matchedPolicyState).toBe(matchedPolicyResponse.matchedPolicyState);
        expect(matchedPolicy.pendingInsuredPolicy).toBe(matchedPolicyResponse.pendingInsuredPolicy);
        expect(matchedPolicy.pendingInsurerPolicy).toBe(matchedPolicyResponse.pendingInsurerPolicy);

    });

    test('call read matchedPolicy', async () => {
        var matchedPolicyResponse: MatchedPolicy[] | undefined = await callReadMatchedPolicyRestEndpointsByMatchedPolicyId(
            matchedPolicyId || 1, env, domain) || [];
        expect(matchedPolicy.coverageAmount).toBe(matchedPolicyResponse[0].coverageAmount);
        expect(matchedPolicy.createdDateTime).toBe(matchedPolicyResponse[0].createdDateTime);
        expect(matchedPolicy.impliedProbability).toBe(matchedPolicyResponse[0].impliedProbability);
        expect(matchedPolicy.premium).toBe(matchedPolicyResponse[0].premium);
        expect(matchedPolicy.insuredFee).toBe(matchedPolicyResponse[0].insuredFee);
        expect(matchedPolicy.insurerFee).toBe(matchedPolicyResponse[0].insurerFee);
        expect(matchedPolicy.matchedPolicyState).toBe(matchedPolicyResponse[0].matchedPolicyState);
        expect(matchedPolicy.pendingInsuredPolicy).toBe(matchedPolicyResponse[0].pendingInsuredPolicy);
        expect(matchedPolicy.pendingInsurerPolicy).toBe(matchedPolicyResponse[0].pendingInsurerPolicy);
    });

    test('call update matchedPolicy', async () => {
        var matchedPolicyResponse: MatchedPolicy[] = await callUpdateMatchedPolicyRestEndpoints(
            updatedMatchedPolicy, env, domain);
            expect(updatedMatchedPolicy.coverageAmount).toBe(matchedPolicyResponse[0].coverageAmount);
            expect(updatedMatchedPolicy.createdDateTime).toBe(matchedPolicyResponse[0].createdDateTime);
            expect(updatedMatchedPolicy.impliedProbability).toBe(matchedPolicyResponse[0].impliedProbability);
            expect(updatedMatchedPolicy.premium).toBe(matchedPolicyResponse[0].premium);
            expect(updatedMatchedPolicy.insuredFee).toBe(matchedPolicyResponse[0].insuredFee);
            expect(updatedMatchedPolicy.insurerFee).toBe(matchedPolicyResponse[0].insurerFee);
            expect(updatedMatchedPolicy.matchedPolicyState).toBe(matchedPolicyResponse[0].matchedPolicyState);
            expect(updatedMatchedPolicy.pendingInsuredPolicy).toBe(matchedPolicyResponse[0].pendingInsuredPolicy);
            expect(updatedMatchedPolicy.pendingInsurerPolicy).toBe(matchedPolicyResponse[0].pendingInsurerPolicy);
    });

    test('call delete matchedPolicy', async () => {
        var deleteMatchedPolicyResponse: boolean = await callDeleteMatchedPolicyRestEndpointsByMatchedPolicyId(
            matchedPolicyId || 1, env, domain);
        expect(true).toBe(deleteMatchedPolicyResponse);

        var deleteMatchedPolicyStateResponse: boolean = await callDeleteMatchedPolicyStateRestEndpointsByMatchedPolicyStateId(
            matchedPolicyState.id || 1, env, domain);
        expect(true).toBe(deleteMatchedPolicyStateResponse);

        var deletePendingPolicyResponse: boolean = await callDeletePendingPolicyRestEndpointsByPendingPolicyId(
            pendingPolicy.id || 1, env, domain);
        expect(true).toBe(deletePendingPolicyResponse);

        var deleteFeeResponse: boolean = await callDeleteFeeRestEndpointsByFeeId(
            fee.id || 1, env, domain);
        expect(true).toBe(deleteFeeResponse);

        var deleteUnitResponse: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unit.id || 1, env, domain);
        expect(true).toBe(deleteUnitResponse);

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

        var deleteSessionResponse: boolean = await callDeleteSessionRestEndpointsBySessionId(
            session.id || 1, env, domain);
        expect(true).toBe(deleteSessionResponse);

        var deleteDeviceResponse = await callDeleteDeviceRestEndpointsByDeviceId(
            device.id || 1, env, domain);
        expect(deleteDeviceResponse).toBe(true);

        //var deleteUserResponse = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(deleteUserResponse).toBe(true);

        var deletePersonResponse = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(deletePersonResponse).toBe(true);

        var deletePendingPolicyStateResponse: boolean = await callDeletePendingPolicyStateRestEndpointsByPendingPolicyStateId(
            pendingPolicyState.id || 1, env, domain);
        expect(true).toBe(deletePendingPolicyStateResponse);

        var deletePerilResponse: boolean = await callDeletePerilRestEndpointsByPerilId(
            peril.id || 1, env, domain);
        expect(true).toBe(deletePerilResponse);
    });
});