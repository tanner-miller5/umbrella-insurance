import { useSelector } from "react-redux";
import { RootState } from "../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { Person } from "../../../../../models/people/v1/Person";
import { User } from "../../../../../models/users/v1/User";
import { UpdateUserResponse } from "../../../../../endpoints/soa/user/v1/responses/UpdateUserResponse";
import { DeleteUserResponse } from "../../../../../endpoints/soa/user/v1/responses/DeleteUserResponse";
import { callCreateUserRestEndpoints, callReadUserRestEndpointsByUserId, callUpdateUserRestEndpoint } from "../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var userId: number | undefined; 
var env: string = "TEST";
describe.skip('user endpoint tests', () => {
    var domain: string = "http://localhost:8080";
    var createdDateTime = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;
    var updatedCreatedDateTime = "2111-11-11T11:11:11Z";
    var updatedEmailAddress: string = "4";
    var updatedPhoneNumber: string = "5";
    var updatedUsername: string = "6";
    var updatedIsPhoneNumberVerified: boolean = true;
    var updatedIsEmailAddressVerified: boolean = true;

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

    updatedUser.createdDateTime = updatedCreatedDateTime;
    updatedUser.emailAddress = updatedEmailAddress;
    updatedUser.phoneNumber = updatedPhoneNumber;
    updatedUser.username = updatedUsername;
    updatedUser.isEmailAddressVerified = updatedIsEmailAddressVerified;
    updatedUser.isPhoneNumberVerified = updatedIsPhoneNumberVerified;

    test('call create user', async () => {
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
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
    });

    test('call read user', async () => {
        var users: User[] | undefined = await callReadUserRestEndpointsByUserId(userId || 1, env, domain) || [];
        expect(users[0].createdDateTime).toBe(user.createdDateTime);
        expect(users[0].emailAddress).toBe(user.emailAddress);
        expect(users[0].phoneNumber).toBe(user.phoneNumber);
        expect(users[0].username).toBe(user.username);
        expect(users[0].isEmailAddressVerified).toBe(user.isEmailAddressVerified);
        expect(users[0].isPhoneNumberVerified).toBe(user.isPhoneNumberVerified);
    });

    test('call update user', async () => {
        var updateUser: UpdateUserResponse = await callUpdateUserRestEndpoint(
            updatedUser, "sessionCode", env, domain);

    });

    test('call delete user', async () => {
        //var deleteUserResponse: DeleteUserResponse = await callDeleteUserRestEndpointsByUserId(userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});