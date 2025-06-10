import { useSelector } from "react-redux";
import { callCreateSentEmailRestEndpoints, callReadSentEmailRestEndpointsBySentEmailId, callDeleteSentEmailRestEndpointsBySentEmailId, callUpdateSentEmailRestEndpoints } from "../../../../../../endpoints/rest/communications/sentEmails/v1/SentEmailRestEndpoints";
import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId } from "../../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { SessionCreateRestRequest } from "../../../../../../endpoints/rest/users/sessions/v1/SessionCreateRestRequest";
import { callCreateSessionRestEndpoints, callDeleteSessionRestEndpointsBySessionId } from "../../../../../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { SentEmail } from "../../../../../../models/communications/sentEmails/v1/SentEmail";
import { Device } from "../../../../../../models/devices/v1/Device";
import { Person } from "../../../../../../models/people/v1/Person";
import { Session } from "../../../../../../models/users/sessions/v1/Session";
import { User } from "../../../../../../models/users/v1/User";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";



beforeEach((): void => {
    jest.setTimeout(60000);
});
var sentEmailId: number | undefined; 
var env: string = "TEST";
describe.skip('sentEmail endpoint tests', () => {
var domain: string = "http://localhost:8080";
    var recipientEmailAddress: string = "1";
    var senderEmailAddress: string = "2";
    var contentType: string = "3";
    var emailSubject: string = "4";
    var emailBody: string = "5";
    var sentDateTime: string = "2023-12-12T12:00:00Z";

    var updatedRecipientEmailAddress: string = "22";
    var updatedSenderEmailAddress: string = "33";
    var updatedContentType: string = "44";
    var updatedEmailSubject: string = "55";
    var updatedEmailBody: string = "66";
    var updatedSentDateTime: string = "2024-11-11T11:00:00Z";
    var createdDateTime: string = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;
    var deviceName: string = "1234";
    var ipAddress: string = "2";
    var userAgent: string = "3";
    var sessionCode: string = "11";
    var startDateTime: string = "2022-11-11T11:11:11Z";
    var endDateTime: string = "2020-11-11T11:11:11Z";
    var minutesToExpire: number = 2;
    var password: string = "5";
    var person: Person = new Person();
    var user: User = new User();
    var device: Device = new Device();
    var session: Session = new Session();

    var sentEmail: SentEmail = new SentEmail();
    var sessionCreateRestRequest: SessionCreateRestRequest = new SessionCreateRestRequest();
    sessionCreateRestRequest.username = username;
    sessionCreateRestRequest.password = password;

    var updatedSentEmail: SentEmail = new SentEmail();

    person.ssn = "123";
    person.dateOfBirth = "1111-11-11";
    person.surname = "last";
    person.middleName ="middle";
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

    sentEmail.recipientEmailAddress = recipientEmailAddress;
    sentEmail.senderEmailAddress = senderEmailAddress;
    sentEmail.contentType = contentType;
    sentEmail.emailSubject = emailSubject;
    sentEmail.emailBody = emailBody;
    sentEmail.sentDateTime = sentDateTime;

    //
    updatedSentEmail.recipientEmailAddress = updatedRecipientEmailAddress;
    updatedSentEmail.senderEmailAddress = updatedSenderEmailAddress;
    updatedSentEmail.contentType = updatedContentType;
    updatedSentEmail.emailSubject = updatedEmailSubject;
    updatedSentEmail.emailBody = updatedEmailBody;
    updatedSentEmail.sentDateTime = updatedSentDateTime;

    var domain: string = "http://localhost:8080";


    test('call create sentEmail', async () => {
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
        sentEmail.session = session;
        updatedSentEmail.session = session;

        var sentEmailResponse: SentEmail = await callCreateSentEmailRestEndpoints(sentEmail, env, domain);
        sentEmailId = sentEmailResponse.id;
        expect(sentEmail.contentType).toBe(sentEmailResponse.contentType);
        expect(sentEmail.emailBody).toBe(sentEmailResponse.emailBody);
        expect(sentEmail.emailSubject).toBe(sentEmailResponse.emailSubject);
        expect(sentEmail.recipientEmailAddress).toBe(sentEmailResponse.recipientEmailAddress);
        expect(sentEmail.senderEmailAddress).toBe(sentEmailResponse.senderEmailAddress);
        expect(sentEmail.sentDateTime).toBe(sentEmailResponse.sentDateTime);
        expect(sentEmail.session).toBe(sentEmailResponse.session);
    });

    test('call read sentEmail', async () => {
        var sentEmails: SentEmail[] | undefined = await callReadSentEmailRestEndpointsBySentEmailId(sentEmailId || 1, env, domain) || [];
        if(sentEmails) {
            expect(sentEmail.contentType).toBe(sentEmails[0].contentType);
            expect(sentEmail.emailBody).toBe(sentEmails[0].emailBody);
            expect(sentEmail.emailSubject).toBe(sentEmails[0].emailSubject);
            expect(sentEmail.recipientEmailAddress).toBe(sentEmails[0].recipientEmailAddress);
            expect(sentEmail.senderEmailAddress).toBe(sentEmails[0].senderEmailAddress);
            expect(sentEmail.sentDateTime).toBe(sentEmails[0].sentDateTime);
            expect(sentEmail.session).toBe(sentEmails[0].session);
        }
    });

    test('call update sentEmail', async () => {
        var sentEmailResponse: SentEmail[] = await callUpdateSentEmailRestEndpoints(
            updatedSentEmail, env, domain);
        if(sentEmailResponse) {
            expect(updatedSentEmail.contentType).toBe(sentEmailResponse[0].contentType);
            expect(updatedSentEmail.emailBody).toBe(sentEmailResponse[0].emailBody);
            expect(updatedSentEmail.emailSubject).toBe(sentEmailResponse[0].emailSubject);
            expect(updatedSentEmail.recipientEmailAddress).toBe(sentEmailResponse[0].recipientEmailAddress);
            expect(updatedSentEmail.senderEmailAddress).toBe(sentEmailResponse[0].senderEmailAddress);
            expect(updatedSentEmail.sentDateTime).toBe(sentEmailResponse[0].sentDateTime);
            expect(updatedSentEmail.session).toBe(sentEmailResponse[0].session);
        }

    });

    test('call delete sentEmail', async () => {
        var deleteSentEmailResponse: boolean = await callDeleteSentEmailRestEndpointsBySentEmailId(sentEmailId || 1, env, domain);
        expect(deleteSentEmailResponse).toBe(true);

        var deleteSessionResponse = await callDeleteSessionRestEndpointsBySessionId(
            session.id || 1, env, domain);
        expect(deleteSessionResponse).toBe(true);

        var deleteDeviceResponse = await callDeleteDeviceRestEndpointsByDeviceId(device.id || 1, env, domain);
        expect(deleteDeviceResponse).toBe(true);

        //var deleteUserResponse = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(deleteUserResponse).toBe(true);

        var deletePersonResponse = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(deletePersonResponse).toBe(true);
    });
});