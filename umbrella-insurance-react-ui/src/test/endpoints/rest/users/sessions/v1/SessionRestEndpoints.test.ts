import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId } from "../../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { SessionCreateRestRequest } from "../../../../../../endpoints/rest/users/sessions/v1/SessionCreateRestRequest";
import { callCreateSessionRestEndpoints, callReadSessionRestEndpointsBySessionId, callDeleteSessionRestEndpointsBySessionId, callUpdateSessionRestEndpoints } from "../../../../../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { Device } from "../../../../../../models/devices/v1/Device";
import { Person } from "../../../../../../models/people/v1/Person";
import { Session } from "../../../../../../models/users/sessions/v1/Session";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var sessionId: number | undefined; 
var env: string = "TEST";
describe.skip('session endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    jest.setTimeout(60000);
    var sessionCode: string = "11";
    var startDateTime = "2022-11-11T11:11:11Z";
    var endDateTime = "2020-11-11T11:11:11Z";
    var minutesToExpire:number = 2;
    var updatedSessionCode: string = "22";
    var updatedStartDateTime = "2022-11-11T11:11:55Z";
    var updatedEndDateTime = "2022-11-11T20:33:11Z";
    var updatedMinutesToExpire: number = 6;
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

    var updatedSession: Session = new Session();
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

    updatedSession.sessionCode = updatedSessionCode;
    updatedSession.startDateTime = updatedStartDateTime;
    updatedSession.endDateTime = updatedEndDateTime;
    updatedSession.minutesToExpire = updatedMinutesToExpire;

    var password: string = "5";
    var sessionCreateRestRequest: SessionCreateRestRequest = new SessionCreateRestRequest();
    sessionCreateRestRequest.username = username;
    sessionCreateRestRequest.password = password;

    test('call create session', async () => {
        jest.setTimeout(60000);
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
        updatedSession.user = user;
        
        var createDeviceResponse = await callCreateDeviceRestEndpoints(device, env, domain);
        expect(createDeviceResponse.createdDateTime).toBe(device.createdDateTime);
        expect(createDeviceResponse.deviceName).toBe(device.deviceName);
        expect(createDeviceResponse.ipAddress).toBe(device.ipAddress);
        expect(createDeviceResponse.userAgent).toBe(device.userAgent);
        device.id = createDeviceResponse.id;
        session.device = device;
        updatedSession.device = device;

        var createSessionResponse = await callCreateSessionRestEndpoints(
            sessionCreateRestRequest, env, domain);
        expect(createSessionResponse.endDateTime).toBe(session.endDateTime);
        expect(createSessionResponse.minutesToExpire).toBe(session.minutesToExpire);
        expect(createSessionResponse.sessionCode).toBe(session.sessionCode);
        expect(createSessionResponse.startDateTime).toBe(session.startDateTime);
        expect(createSessionResponse.user).toBe(session.user);
        session.id = createSessionResponse.id;
        sessionId = createSessionResponse.id;
        updatedSession.id = sessionId
    });

    test('call read session', async () => {
        var sessions: Session[] | undefined = await callReadSessionRestEndpointsBySessionId(sessionId || 1, env, domain) || [];
        expect(sessions[0].endDateTime).toBe(session.endDateTime);
        expect(sessions[0].minutesToExpire).toBe(session.minutesToExpire);
        expect(sessions[0].sessionCode).toBe(session.sessionCode);
        expect(sessions[0].startDateTime).toBe(session.startDateTime);
        expect(sessions[0].user).toBe(session.user);

    });

    test('call update session', async () => {
        var sessionResponse: Session[] = await callUpdateSessionRestEndpoints(
            updatedSession, env, domain);
        expect(sessionResponse[0].endDateTime).toBe(updatedSession.endDateTime);
        expect(sessionResponse[0].minutesToExpire).toBe(updatedSession.minutesToExpire);
        expect(sessionResponse[0].sessionCode).toBe(updatedSession.sessionCode);
        expect(sessionResponse[0].startDateTime).toBe(updatedSession.startDateTime);
        expect(sessionResponse[0].user).toBe(updatedSession.user);

    });

    test('call delete session', async () => {
        var deleteSessionResponse: boolean = await callDeleteSessionRestEndpointsBySessionId(sessionId || 1, env, domain);
        expect(true).toBe(deleteSessionResponse);

        var deleteDeviceResponse = await callDeleteDeviceRestEndpointsByDeviceId(device.id || 1, env, domain);
        expect(deleteDeviceResponse).toBe(true);

        //var deleteUserResponse = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(deleteUserResponse).toBe(true);

        var deletePersonResponse = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(deletePersonResponse).toBe(true);
    });

});