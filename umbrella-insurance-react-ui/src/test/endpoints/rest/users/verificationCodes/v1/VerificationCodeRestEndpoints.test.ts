import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId } from "../../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { SessionCreateRestRequest } from "../../../../../../endpoints/rest/users/sessions/v1/SessionCreateRestRequest";
import { callCreateSessionRestEndpoints, callDeleteSessionRestEndpointsBySessionId } from "../../../../../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { callCreateVerificationCodeRestEndpoints, callReadVerificationCodeRestEndpointsByVerificationCodeId, callDeleteVerificationCodeRestEndpointsByVerificationCodeId, callUpdateVerificationCodeRestEndpoints } from "../../../../../../endpoints/rest/users/verificationCodes/v1/VerificationCodeRestEndpoints";
import { callCreateVerificationMethodRestEndpoints, callDeleteVerificationMethodRestEndpointsByVerificationMethodId } from "../../../../../../endpoints/rest/users/verificationCodes/verificationMethods/v1/VerificationMethodRestEndpoints";
import { Device } from "../../../../../../models/devices/v1/Device";
import { Person } from "../../../../../../models/people/v1/Person";
import { Session } from "../../../../../../models/users/sessions/v1/Session";
import { User } from "../../../../../../models/users/v1/User";
import { VerificationCode } from "../../../../../../models/users/verificationCodes/v1/VerificationCode";
import { VerificationMethod } from "../../../../../../models/users/verificationCodes/verificationMethods/v1/VerificationMethod";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('verificationCode endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var verificationCodeId: number | undefined; 

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

    var verificationMethod: VerificationMethod = new VerificationMethod();
    verificationMethod.verificationMethodName = "1";

    var verificationCode: VerificationCode = new VerificationCode();
    verificationCode.currentAttempt = 1;
    verificationCode.expirationDateTime = "1111-11-11T11:11:11Z";
    verificationCode.isVerified = false;
    verificationCode.maxAttempts = 1;
    verificationCode.minutesToVerify = 12;
    verificationCode.verificationCode = "1";
    verificationCode.verifiedDateTime = "1111-11-11T11:11:11Z";

    var updatedVerificationCode: VerificationCode = new VerificationCode();
    updatedVerificationCode.currentAttempt = 1;
    updatedVerificationCode.expirationDateTime = "1111-11-11T11:11:11Z";
    updatedVerificationCode.isVerified = false;
    updatedVerificationCode.maxAttempts = 1;
    updatedVerificationCode.minutesToVerify = 12;
    updatedVerificationCode.verificationCode = "1";
    updatedVerificationCode.verifiedDateTime = "1111-11-11T11:11:11Z";

    var password: string = "5";
    var sessionCreateRestRequest: SessionCreateRestRequest = new SessionCreateRestRequest();
    sessionCreateRestRequest.username = username;
    sessionCreateRestRequest.password = password;

    test('call create verificationCode', async () => {
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
        verificationCode.session = session;
        updatedVerificationCode.session = session;

        var verificationMethodResponse: VerificationMethod = await callCreateVerificationMethodRestEndpoints(
            verificationMethod, env, domain);
        verificationMethod.id = verificationMethodResponse.id;
        verificationCode.verificationMethod = verificationMethod;
        updatedVerificationCode.verificationMethod = verificationMethod;
        expect(verificationMethod.verificationMethodName).toBe(verificationMethodResponse.verificationMethodName);

        var verificationCodeResponse: VerificationCode = await callCreateVerificationCodeRestEndpoints(
            verificationCode, env, domain);
        verificationCodeId = verificationCodeResponse.id;
        expect(verificationCode.currentAttempt).toBe(verificationCodeResponse.currentAttempt);
        expect(verificationCode.expirationDateTime).toBe(verificationCodeResponse.expirationDateTime);
        expect(verificationCode.isVerified).toBe(verificationCodeResponse.isVerified);
        expect(verificationCode.maxAttempts).toBe(verificationCodeResponse.maxAttempts);
        expect(verificationCode.minutesToVerify).toBe(verificationCodeResponse.minutesToVerify);
        expect(verificationCode.verificationCode).toBe(verificationCodeResponse.verificationCode);
        expect(verificationCode.verifiedDateTime).toBe(verificationCodeResponse.verifiedDateTime);
        expect(verificationCode.verificationMethod).toBe(verificationCodeResponse.verificationMethod);
    });

    test('call read verificationCode', async () => {
        var verificationCodeResponse: VerificationCode[] | undefined = await callReadVerificationCodeRestEndpointsByVerificationCodeId(
            verificationCodeId || 1, env, domain) || [];
            expect(verificationCode.currentAttempt).toBe(verificationCodeResponse[0].currentAttempt);
            expect(verificationCode.expirationDateTime).toBe(verificationCodeResponse[0].expirationDateTime);
            expect(verificationCode.isVerified).toBe(verificationCodeResponse[0].isVerified);
            expect(verificationCode.maxAttempts).toBe(verificationCodeResponse[0].maxAttempts);
            expect(verificationCode.minutesToVerify).toBe(verificationCodeResponse[0].minutesToVerify);
            expect(verificationCode.verificationCode).toBe(verificationCodeResponse[0].verificationCode);
            expect(verificationCode.verifiedDateTime).toBe(verificationCodeResponse[0].verifiedDateTime);
            expect(verificationCode.verificationMethod).toBe(verificationCodeResponse[0].verificationMethod);
    });

    test('call update verificationCode', async () => {
        var verificationCodeResponse: VerificationCode[] = await callUpdateVerificationCodeRestEndpoints(
            updatedVerificationCode, env, domain);
            expect(updatedVerificationCode.currentAttempt).toBe(verificationCodeResponse[0].currentAttempt);
            expect(updatedVerificationCode.expirationDateTime).toBe(verificationCodeResponse[0].expirationDateTime);
            expect(updatedVerificationCode.isVerified).toBe(verificationCodeResponse[0].isVerified);
            expect(updatedVerificationCode.maxAttempts).toBe(verificationCodeResponse[0].maxAttempts);
            expect(updatedVerificationCode.minutesToVerify).toBe(verificationCodeResponse[0].minutesToVerify);
            expect(updatedVerificationCode.verificationCode).toBe(verificationCodeResponse[0].verificationCode);
            expect(updatedVerificationCode.verifiedDateTime).toBe(verificationCodeResponse[0].verifiedDateTime);
            expect(updatedVerificationCode.verificationMethod).toBe(verificationCodeResponse[0].verificationMethod);
    });

    test('call delete verificationCode', async () => {
        var deleteVerificationCodeResponse: boolean = await callDeleteVerificationCodeRestEndpointsByVerificationCodeId(
            verificationCodeId || 1, env, domain);
        expect(true).toBe(deleteVerificationCodeResponse);

        var deleteVerificationMethodResponse: boolean = await callDeleteVerificationMethodRestEndpointsByVerificationMethodId(
            verificationMethod.id || 1, env, domain);
        expect(true).toBe(deleteVerificationMethodResponse);

        var deleteSessionResponse: boolean = await callDeleteSessionRestEndpointsBySessionId(
            session.id || 1, env, domain);
        expect(true).toBe(deleteSessionResponse);

        var deleteDeviceResponse = await callDeleteDeviceRestEndpointsByDeviceId(device.id || 1, env, domain);
        expect(deleteDeviceResponse).toBe(true);

        //var deleteUserResponse = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(deleteUserResponse).toBe(true);

        var deletePersonResponse = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(deletePersonResponse).toBe(true);
    });
});