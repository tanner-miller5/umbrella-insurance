import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId } from "../../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { SessionCreateRestRequest } from "../../../../../../endpoints/rest/users/sessions/v1/SessionCreateRestRequest";
import { callCreateSessionRestEndpoints, callDeleteSessionRestEndpointsBySessionId } from "../../../../../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { callCreateTotpRestEndpoints, callReadTotpRestEndpointsByTotpId, callDeleteTotpRestEndpointsByTotpId, callUpdateTotpRestEndpoints } from "../../../../../../endpoints/rest/users/totps/v1/TotpRestEndpoints";
import { Device } from "../../../../../../models/devices/v1/Device";
import { Person } from "../../../../../../models/people/v1/Person";
import { Session } from "../../../../../../models/users/sessions/v1/Session";
import { TOTP as Totp } from "../../../../../../models/users/totps/v1/Totp";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('totps', () => {
var domain: string = "http://localhost:8080";
    
    var totpId: number | undefined; 

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
    var password: string = "5";
    var person: Person = new Person();
    var user: User = new User();
    var device: Device = new Device();
    var sessionCreateRestRequest: SessionCreateRestRequest = new SessionCreateRestRequest();

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

    sessionCreateRestRequest.username = username;
    sessionCreateRestRequest.password = password;
    
    var totp: Totp = new Totp();
    totp.totpCode = "1";

    var updatedTotp: Totp = new Totp();
    updatedTotp.totpCode = "2";

    test('call create totp', async () => {
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
        updatedSession.device = device

        var createSessionResponse = await callCreateSessionRestEndpoints(
            sessionCreateRestRequest, env, domain);
        expect(createSessionResponse.endDateTime).toBe(session.endDateTime);
        expect(createSessionResponse.minutesToExpire).toBe(session.minutesToExpire);
        expect(createSessionResponse.sessionCode).toBe(session.sessionCode);
        expect(createSessionResponse.startDateTime).toBe(session.startDateTime);
        expect(createSessionResponse.user).toBe(session.user);
        session.id = createSessionResponse.id;
        totp.session = session;
        updatedTotp.session = session;

        var totpResponse: Totp = await callCreateTotpRestEndpoints(
            totp, env, domain);
        totpId = totpResponse.id;
        expect(totp.totpCode).toBe(totpResponse.totpCode);
        expect(totp.session).toBe(totpResponse.session);
    });

    test('call read totp', async () => {
        var totps: Totp[] | undefined = await callReadTotpRestEndpointsByTotpId(
            totpId || 1, env, domain) || [];
        expect(totps[0].totpCode).toBe(totp.totpCode);
        expect(totps[0].session).toBe(totp.session);
    });

    test('call update totp', async () => {
        var totpResponse: Totp[] = await callUpdateTotpRestEndpoints(
            updatedTotp, env, domain);
        expect(updatedTotp.totpCode).toBe(totpResponse[0].totpCode);
        expect(updatedTotp.session).toBe(totpResponse[0].session);
    });

    test('call delete totp', async () => {
        var deleteTotpResponse: boolean = await callDeleteTotpRestEndpointsByTotpId(
            totpId || 1, env, domain);
        expect(true).toBe(deleteTotpResponse);

        var deleteSessionResponse: boolean = await callDeleteSessionRestEndpointsBySessionId(session.id || 1, env, domain);
        expect(true).toBe(deleteSessionResponse);

        var deleteDeviceResponse = await callDeleteDeviceRestEndpointsByDeviceId(device.id || 1, env, domain);
        expect(deleteDeviceResponse).toBe(true);

        //var deleteUserResponse = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(deleteUserResponse).toBe(true);

        var deletePersonResponse = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(deletePersonResponse).toBe(true);
    });
});