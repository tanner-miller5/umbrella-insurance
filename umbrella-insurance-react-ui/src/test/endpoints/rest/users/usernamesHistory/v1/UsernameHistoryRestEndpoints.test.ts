import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateUsernameHistoryRestEndpoints, callReadUsernameHistoryRestEndpointsByUsernameHistoryId, callDeleteUsernameHistoryRestEndpointsByUsernameHistoryId, callUpdateUsernameHistoryRestEndpoints } from "../../../../../../endpoints/rest/users/usernamesHistory/v1/UsernameHistoryRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { UsernameHistory } from "../../../../../../models/users/usernamesHistory/v1/UsernameHistory";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('usernameHistory endpoint tests', () => {
var domain: string = "http://localhost:8080";
    var usernameHistoryId: number | undefined; 

    var createdDateTime = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;

    var person: Person = new Person();

    var user: User = new User();

    var updatedUser: User = new User();

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

    var usernameHistory: UsernameHistory = new UsernameHistory();
    usernameHistory.createdDateTime = "1111-11-11T11:11:11Z";
    usernameHistory.username = "1";

    var updatedUsernameHistory: UsernameHistory = new UsernameHistory();
    updatedUsernameHistory.createdDateTime = "2111-11-11T11:11:11Z";
    updatedUsernameHistory.username = "2";

    test('call create usernameHistory', async () => {
        var createPersonResponse = await callCreatePersonRestEndpoints(person, env, domain);
        expect(createPersonResponse.dateOfBirth).toBe(person.dateOfBirth);
        expect(createPersonResponse.firstName).toBe(person.firstName);
        expect(createPersonResponse.middleName).toBe(person.middleName);
        expect(createPersonResponse.surname).toBe(person.surname);
        expect(createPersonResponse.ssn).toBe(person.ssn);
        person.id = createPersonResponse.id
        user.person = person;
        updatedUser.person = person;

        var userResponse: CreateUserResponse = await callCreateUserRestEndpoints(user, env, domain);
        user = userResponse.user || new User();
        usernameHistory.user = user;
        updatedUsernameHistory.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);

        var usernameHistoryResponse: UsernameHistory = await callCreateUsernameHistoryRestEndpoints(
            usernameHistory, env, domain);
        usernameHistoryId = usernameHistoryResponse.id;
        expect(usernameHistory.createdDateTime).toBe(usernameHistoryResponse.createdDateTime);
        expect(usernameHistory.user).toBe(usernameHistoryResponse.user);
        expect(usernameHistory.username).toBe(usernameHistoryResponse.username);
    });

    test('call read usernameHistory', async () => {
        var usernameHistoryResponse: UsernameHistory[] | undefined = await callReadUsernameHistoryRestEndpointsByUsernameHistoryId(
            usernameHistoryId || 1, env, domain) || [];
        expect(usernameHistory.createdDateTime).toBe(usernameHistoryResponse[0].createdDateTime);
        expect(usernameHistory.user).toBe(usernameHistoryResponse[0].user);
        expect(usernameHistory.username).toBe(usernameHistoryResponse[0].username);
    });

    test('call update usernameHistory', async () => {
        var usernameHistoryResponse: UsernameHistory[] = await callUpdateUsernameHistoryRestEndpoints(
            updatedUsernameHistory, env, domain);
            expect(updatedUsernameHistory.createdDateTime).toBe(usernameHistoryResponse[0].createdDateTime);
            expect(updatedUsernameHistory.user).toBe(usernameHistoryResponse[0].user);
            expect(updatedUsernameHistory.username).toBe(usernameHistoryResponse[0].username);
    });

    test('call delete usernameHistory', async () => {
        var deleteUsernameHistoryResponse: boolean = await callDeleteUsernameHistoryRestEndpointsByUsernameHistoryId(
            usernameHistoryId || 1, env, domain);
        expect(true).toBe(deleteUsernameHistoryResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});