import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateUserAgreementRestEndpoints, callReadUserAgreementRestEndpointsByUserAgreementId, callDeleteUserAgreementRestEndpointsByUserAgreementId, callUpdateUserAgreementRestEndpoints } from "../../../../../../endpoints/rest/users/userAgreements/v1/UserAgreementRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { UserAgreement } from "../../../../../../models/users/userAgreements/v1/UserAgreement";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('userAgreement endpoint tests', () => {
var domain: string = "http://localhost:8080";
    var userAgreementId: number | undefined; 

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

    var userAgreement: UserAgreement = new UserAgreement();
    userAgreement.userAgreementName = "1";
    userAgreement.didAgree = true;
    userAgreement.updatedDateTime = "1111-11-11T11:11:11Z";
    userAgreement.userAgreementText = "1";

    var updatedUserAgreement: UserAgreement = new UserAgreement();
    updatedUserAgreement.userAgreementName = "2";
    updatedUserAgreement.didAgree = true;
    updatedUserAgreement.updatedDateTime = "1111-11-11T11:11:11Z";
    updatedUserAgreement.userAgreementText = "1";

    test('call create userAgreement', async () => {
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
        userAgreement.user = user;
        updatedUserAgreement.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        
        var userAgreementResponse: UserAgreement = await callCreateUserAgreementRestEndpoints(
            userAgreement, env, domain);
        userAgreementId = userAgreementResponse.id;
        expect(userAgreement.userAgreementName).toBe(userAgreementResponse.userAgreementName);
        expect(userAgreement.didAgree).toBe(userAgreementResponse.didAgree);
        expect(userAgreement.updatedDateTime).toBe(userAgreementResponse.updatedDateTime);
        expect(userAgreement.userAgreementText).toBe(userAgreementResponse.userAgreementText);
        expect(userAgreement.user).toBe(userAgreementResponse.user);
    });

    test('call read userAgreement', async () => {
        var userAgreementResponse: UserAgreement[] | undefined = await callReadUserAgreementRestEndpointsByUserAgreementId(
            userAgreementId || 1, env, domain) || [];
        expect(userAgreement.userAgreementName).toBe(userAgreementResponse[0].userAgreementName);
        expect(userAgreement.didAgree).toBe(userAgreementResponse[0].didAgree);
        expect(userAgreement.updatedDateTime).toBe(userAgreementResponse[0].updatedDateTime);
        expect(userAgreement.userAgreementText).toBe(userAgreementResponse[0].userAgreementText);
        expect(userAgreement.user).toBe(userAgreementResponse[0].user);
    });

    test('call update userAgreement', async () => {
        var userAgreementResponse: UserAgreement[] = await callUpdateUserAgreementRestEndpoints(
            updatedUserAgreement, env, domain);
        expect(updatedUserAgreement.userAgreementName).toBe(userAgreementResponse[0].userAgreementName);
    });

    test('call delete userAgreement', async () => {
        var deleteUserAgreementResponse: boolean = await callDeleteUserAgreementRestEndpointsByUserAgreementId(
            userAgreementId || 1, env, domain);
        expect(true).toBe(deleteUserAgreementResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1omain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});