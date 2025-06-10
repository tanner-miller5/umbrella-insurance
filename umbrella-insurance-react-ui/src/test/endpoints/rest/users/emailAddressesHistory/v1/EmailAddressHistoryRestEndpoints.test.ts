import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateEmailAddressHistoryRestEndpoints, callReadEmailAddressHistoryRestEndpointsByEmailAddressHistoryId, callDeleteEmailAddressHistoryRestEndpointsByEmailAddressHistoryId, callUpdateEmailAddressHistoryRestEndpoints } from "../../../../../../endpoints/rest/users/emailAddressesHistory/v1/EmailAddressHIstoryRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { EmailAddressHistory } from "../../../../../../models/users/emailAddressesHistory/v1/EmailAddressHistory";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('emailAddressHistory endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var emailAddressHistoryId: number | undefined; 

    var createdDateTime = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;

    var person: Person = new Person();

   var user: User = new User();

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

    var emailAddressHistory: EmailAddressHistory = new EmailAddressHistory();
    emailAddressHistory.createdDateTime = "1111-11-11T11:11:11Z";
    emailAddressHistory.emailAddress = "1";

    var updatedEmailAddressHistory: EmailAddressHistory = new EmailAddressHistory();
    updatedEmailAddressHistory.createdDateTime = "1111-11-11T11:11:11Z";
    updatedEmailAddressHistory.emailAddress = "1";

    test('call create emailAddressHistory', async () => {
        var createPersonResponse = await callCreatePersonRestEndpoints(person, env, domain);
        expect(createPersonResponse.dateOfBirth).toBe(person.dateOfBirth);
        expect(createPersonResponse.firstName).toBe(person.firstName);
        expect(createPersonResponse.middleName).toBe(person.middleName);
        expect(createPersonResponse.surname).toBe(person.surname);
        expect(createPersonResponse.ssn).toBe(person.ssn);
        person.id = createPersonResponse.id;
        user.person = person;

        var userResponse: CreateUserResponse = await callCreateUserRestEndpoints(user, env, domain);
        user = userResponse.user || new User();
        emailAddressHistory.user = user;
        updatedEmailAddressHistory.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);

        var emailAddressHistoryResponse: EmailAddressHistory = await callCreateEmailAddressHistoryRestEndpoints(
            emailAddressHistory, env, domain);
        emailAddressHistoryId = emailAddressHistoryResponse.id;
        expect(emailAddressHistory.createdDateTime).toBe(emailAddressHistoryResponse.createdDateTime);
        expect(emailAddressHistory.emailAddress).toBe(emailAddressHistoryResponse.emailAddress);
        expect(emailAddressHistory.user).toBe(emailAddressHistoryResponse.user);
    });

    test('call read emailAddressHistory', async () => {
        var emailAddressHistoryResponse: EmailAddressHistory[] | undefined = await callReadEmailAddressHistoryRestEndpointsByEmailAddressHistoryId(
            emailAddressHistoryId || 1, env, domain) || [];
        expect(emailAddressHistory.createdDateTime).toBe(emailAddressHistoryResponse[0].createdDateTime);
        expect(emailAddressHistory.emailAddress).toBe(emailAddressHistoryResponse[0].emailAddress);
        expect(emailAddressHistory.user).toBe(emailAddressHistoryResponse[0].user);
    });

    test('call update emailAddressHistory', async () => {
        var emailAddressHistoryResponse: EmailAddressHistory[] = await callUpdateEmailAddressHistoryRestEndpoints(
            updatedEmailAddressHistory, env, domain);
        expect(updatedEmailAddressHistory.createdDateTime).toBe(emailAddressHistoryResponse[0].createdDateTime);
        expect(updatedEmailAddressHistory.emailAddress).toBe(emailAddressHistoryResponse[0].emailAddress);
        expect(updatedEmailAddressHistory.user).toBe(emailAddressHistoryResponse[0].user);
    });

    test('call delete emailAddressHistory', async () => {
        var deleteEmailAddressHistoryResponse: boolean = await callDeleteEmailAddressHistoryRestEndpointsByEmailAddressHistoryId(
            emailAddressHistoryId || 1, env, domain);
        expect(true).toBe(deleteEmailAddressHistoryResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});