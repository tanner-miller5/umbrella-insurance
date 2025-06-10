import { useSelector } from "react-redux";
import { callCreateSentTextRestEndpoints, callReadSentTextRestEndpointsBySentTextId, callDeleteSentTextRestEndpointsBySentTextId, callUpdateSentTextRestEndpoints } from "../../../../../../endpoints/rest/communications/sentTexts/v1/SentTextRestEndpoints";
import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId } from "../../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { SessionCreateRestRequest } from "../../../../../../endpoints/rest/users/sessions/v1/SessionCreateRestRequest";
import { callCreateSessionRestEndpoints, callDeleteSessionRestEndpointsBySessionId } from "../../../../../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { SentText } from "../../../../../../models/communications/sentTexts/v1/SentText";
import { Device } from "../../../../../../models/devices/v1/Device";
import { Person } from "../../../../../../models/people/v1/Person";
import { Session } from "../../../../../../models/users/sessions/v1/Session";
import { User } from "../../../../../../models/users/v1/User";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var env: string = "TEST";
describe.skip('sentText endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var sentText: SentText = new SentText();
    var sentTextId: number | undefined;
    var recipientPhoneNumber: string = "123";
    var senderPhoneNumber: string = "123456";
    var textMessage: string = "message";
    var sentDateTime = "2023-12-12T12:00:00Z";

    var updatedRecipientPhoneNumber: string = "123";
    var updatedSenderPhoneNumber: string = "123456";
    var updatedTextMessage: string = "message";
    var updatedSentDateTime = "2024-11-11T11:00:00Z";
    var createdDateTime = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;
    var deviceName: string = "1234";
    var ipAddress: string = "2";
    var userAgent: string = "3";
    var sessionCode: string = "11";
    var startDateTime = "2022-11-11T11:11:11Z";
    var endDateTime = "2020-11-11T11:11:11Z";
    var minutesToExpire:number = 2;
    var person: Person = new Person();
    var user: User = new User();
    var device: Device = new Device();
    var session: Session = new Session();
    var sentText: SentText = new SentText();
    var updatedSentText: SentText = new SentText();
    var password: string = "5";
    var sessionCreateRestRequest: SessionCreateRestRequest = new SessionCreateRestRequest();
    sessionCreateRestRequest.username = username;
    sessionCreateRestRequest.password = password;

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

    sentText.recipientPhoneNumber = recipientPhoneNumber;
    sentText.senderPhoneNumber = senderPhoneNumber;
    sentText.session = session;
    sentText.textMessage = textMessage;
    sentText.sentDateTime = sentDateTime;

    //
    updatedSentText.recipientPhoneNumber = updatedRecipientPhoneNumber;
    updatedSentText.senderPhoneNumber = updatedSenderPhoneNumber;
    updatedSentText.session = session;
    updatedSentText.textMessage = updatedTextMessage;
    updatedSentText.sentDateTime = updatedSentDateTime;

    test('call create sentText', async () => {
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

        var createSessionResponse = await callCreateSessionRestEndpoints(
            sessionCreateRestRequest, env, domain);
        expect(createSessionResponse.endDateTime).toBe(session.endDateTime);
        expect(createSessionResponse.minutesToExpire).toBe(session.minutesToExpire);
        expect(createSessionResponse.sessionCode).toBe(session.sessionCode);
        expect(createSessionResponse.startDateTime).toBe(session.startDateTime);
        expect(createSessionResponse.user).toBe(session.user);
        session.id = createSessionResponse.id;
        sentText.session = session;
        updatedSentText.session = session;

        var sentTextResponse: SentText = await callCreateSentTextRestEndpoints(sentText, env, domain);
        expect(sentTextResponse.sentDateTime).toBe(sentDateTime);
        expect(sentTextResponse.textMessage).toBe(textMessage);
        expect(sentTextResponse.senderPhoneNumber).toBe(senderPhoneNumber);
        expect(sentTextResponse.session).toBe(session);
        expect(sentTextResponse.recipientPhoneNumber).toBe(recipientPhoneNumber);
        sentTextId = sentTextResponse.id;
    });

    test('call read sentText', async () => {
        var sentTexts: SentText[] | undefined = await callReadSentTextRestEndpointsBySentTextId(sentTextId || 1, env, domain);
        if (sentTexts) {
            expect(sentTexts[0].sentDateTime).toBe(sentDateTime);
            expect(sentTexts[0].textMessage).toBe(textMessage);
            expect(sentTexts[0].senderPhoneNumber).toBe(senderPhoneNumber);
            expect(sentTexts[0].session).toBe(session);
            expect(sentTexts[0].recipientPhoneNumber).toBe(recipientPhoneNumber);
        }
    });

    test('call update sentText', async () => {
        var sentTextResponse: SentText[] = await callUpdateSentTextRestEndpoints(
            updatedSentText, env, domain);
        expect(sentTextResponse[0].sentDateTime).toBe(updatedSentDateTime);
        expect(sentTextResponse[0].textMessage).toBe(updatedTextMessage);
        expect(sentTextResponse[0].senderPhoneNumber).toBe(updatedSenderPhoneNumber);
        expect(sentTextResponse[0].session).toBe(session);
        expect(sentTextResponse[0].recipientPhoneNumber).toBe(updatedRecipientPhoneNumber);
    });

    test('call delete sentText', async () => {
        var deleteSentTextResponse: boolean = await callDeleteSentTextRestEndpointsBySentTextId(sentTextId || 1, env, domain);
        expect(true).toBe(deleteSentTextResponse);

        var deleteSessionResponse = await callDeleteSessionRestEndpointsBySessionId(
            session.id || 1, env, domain);
        expect(deleteSessionResponse).toBe(true);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deleteDeviceResponse: boolean = await callDeleteDeviceRestEndpointsByDeviceId(device.id || 1, env, domain);
        expect(true).toBe(deleteDeviceResponse);
    });
});