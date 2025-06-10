import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreatePasswordRestEndpoints, callReadPasswordRestEndpointsByPasswordId, callDeletePasswordRestEndpointsByPasswordId, callUpdatePasswordRestEndpoints } from "../../../../../../endpoints/rest/users/passwords/v1/PasswordRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { Password } from "../../../../../../models/users/passwords/v1/Password";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('password endpoint tests', () => {
var domain: string = "http://localhost:8080";
    var passwordId: number | undefined; 

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

    var password: Password = new Password();
    password.createdDateTime = "1111-11-11T11:11:11Z";
    password.hashedPassword = "1";
    password.password = "1";
    password.salt = "1";

    var updatedPassword: Password = new Password();
    updatedPassword.createdDateTime = "2111-11-11T11:11:11Z";
    updatedPassword.hashedPassword = "2";
    updatedPassword.password = "2";
    updatedPassword.salt = "2";

    test('call create password', async () => {
        var createPersonResponse = await callCreatePersonRestEndpoints(person, env, domain);
        expect(createPersonResponse.dateOfBirth).toBe(person.dateOfBirth);
        expect(createPersonResponse.firstName).toBe(person.firstName);
        expect(createPersonResponse.middleName).toBe(person.middleName);
        expect(createPersonResponse.surname).toBe(person.surname);
        expect(createPersonResponse.ssn).toBe(person.ssn);
        person.id = createPersonResponse.id
        user.person = person;

        var userResponse: CreateUserResponse = await callCreateUserRestEndpoints(user, env, domain);
        user = userResponse.user || new User();
        password.user = user;
        updatedPassword.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);

        var passwordResponse: Password = await callCreatePasswordRestEndpoints(
            password, env, domain);
        passwordId = passwordResponse.id;
        expect(password.createdDateTime).toBe(passwordResponse.createdDateTime);
        expect(password.hashedPassword).toBe(passwordResponse.hashedPassword);
        expect(password.salt).toBe(passwordResponse.salt);
        expect(password.user).toBe(passwordResponse.user);
    });

    test('call read password', async () => {
        var passwordResponse: Password[] | undefined = await callReadPasswordRestEndpointsByPasswordId(
            passwordId || 1, env, domain) || [];
        expect(password.createdDateTime).toBe(passwordResponse[0].createdDateTime);
        expect(password.hashedPassword).toBe(passwordResponse[0].hashedPassword);
        expect(password.salt).toBe(passwordResponse[0].salt);
        expect(password.user).toBe(passwordResponse[0].user);
    });

    test('call update password', async () => {
        var passwordResponse: Password[] = await callUpdatePasswordRestEndpoints(
            updatedPassword, env, domain);
        expect(updatedPassword.createdDateTime).toBe(passwordResponse[0].createdDateTime);
        expect(updatedPassword.hashedPassword).toBe(passwordResponse[0].hashedPassword);
        expect(updatedPassword.salt).toBe(passwordResponse[0].salt);
        expect(updatedPassword.user).toBe(passwordResponse[0].user);
    });

    test('call delete password', async () => {
        var deletePasswordResponse: boolean = await callDeletePasswordRestEndpointsByPasswordId(
            passwordId || 1, env, domain);
        expect(true).toBe(deletePasswordResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});