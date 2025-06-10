import { useSelector } from "react-redux";
import { RootState } from "../../../../../redux/store/Store";
import { callCreateApplicationRoleRestEndpoints, callDeleteApplicationRoleRestEndpointsByApplicationRoleId } from "../../../../../endpoints/rest/applicationRoles/v1/ApplicationRoleRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateUserApplicationRoleRelationshipRestEndpoints, callDeleteUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipId, callReadUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipId, callUpdateUserApplicationRoleRelationshipRestEndpoints } from "../../../../../endpoints/rest/userApplicationRoleRelationships/v1/UserApplicationRoleRelationshipRestEndpoints";
import { ApplicationRole } from "../../../../../models/applicationRoles/v1/ApplicationRole";
import { Person } from "../../../../../models/people/v1/Person";
import { UserApplicationRoleRelationship } from "../../../../../models/userApplicationRoleRelationships/v1/UserApplicationRoleRelationship";
import { User } from "../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('userApplicationRoleRelationship endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var userApplicationRoleRelationshipId: number | undefined; 

    var applicationRole: ApplicationRole = new ApplicationRole();
    applicationRole.applicationRoleName = "1";

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

    var userApplicationRoleRelationship: UserApplicationRoleRelationship = new UserApplicationRoleRelationship();

    var updatedUserApplicationRoleRelationship: UserApplicationRoleRelationship = new UserApplicationRoleRelationship();

    test('call create userApplicationRoleRelationship', async () => {
        var applicationRoleResponse: ApplicationRole = await callCreateApplicationRoleRestEndpoints(
            applicationRole, env, domain);
        applicationRole.id = applicationRoleResponse.id;
        userApplicationRoleRelationship.applicationRole = applicationRole;
        updatedUserApplicationRoleRelationship.applicationRole = applicationRole;
        expect(applicationRole.applicationRoleName).toBe(applicationRoleResponse.applicationRoleName);

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
        userApplicationRoleRelationship.user = user;
        updatedUserApplicationRoleRelationship.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);

        var userApplicationRoleRelationshipResponse: UserApplicationRoleRelationship = await callCreateUserApplicationRoleRelationshipRestEndpoints(
            userApplicationRoleRelationship, env, domain);
        userApplicationRoleRelationshipId = userApplicationRoleRelationshipResponse.id;
        expect(userApplicationRoleRelationship.applicationRole).toBe(userApplicationRoleRelationshipResponse.applicationRole);
        expect(userApplicationRoleRelationship.user).toBe(userApplicationRoleRelationshipResponse.user);
    });

    test('call read userApplicationRoleRelationship', async () => {
        var userApplicationRoleRelationshipResponse: UserApplicationRoleRelationship[] | undefined = await callReadUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipId(
            userApplicationRoleRelationshipId || 1, env, domain) || [];
        expect(userApplicationRoleRelationship.applicationRole).toBe(userApplicationRoleRelationshipResponse[0].applicationRole);
        expect(userApplicationRoleRelationship.user).toBe(userApplicationRoleRelationshipResponse[0].user);
    });

    test('call update userApplicationRoleRelationship', async () => {
        var userApplicationRoleRelationshipResponse: UserApplicationRoleRelationship[] = await callUpdateUserApplicationRoleRelationshipRestEndpoints(
            updatedUserApplicationRoleRelationship, env, domain);
        expect(updatedUserApplicationRoleRelationship.applicationRole).toBe(userApplicationRoleRelationshipResponse[0].applicationRole);
        expect(updatedUserApplicationRoleRelationship.user).toBe(userApplicationRoleRelationshipResponse[0].user);
    });

    test('call delete userApplicationRoleRelationship', async () => {
        var deleteUserApplicationRoleRelationshipResponse: boolean = await callDeleteUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipId(
            userApplicationRoleRelationshipId || 1, env, domain);
        expect(true).toBe(deleteUserApplicationRoleRelationshipResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1omain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain)
        expect(true).toBe(deletePersonResponse);

        var deleteApplicationRoleResponse: boolean = await callDeleteApplicationRoleRestEndpointsByApplicationRoleId(
            applicationRole.id || 1, env, domain);
        expect(true).toBe(deleteApplicationRoleResponse);
    });
});