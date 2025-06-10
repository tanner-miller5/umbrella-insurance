import { callCreateGroupUserRelationshipRestEndpoints, callReadGroupUserRelationshipRestEndpointsByGroupUserRelationshipId, callDeleteGroupUserRelationshipRestEndpointsByGroupUserRelationshipId, callUpdateGroupUserRelationshipRestEndpoints } from "../../../../../../endpoints/rest/groups/groupUserRelationships/v1/GroupUserRelationshipRestEndpoints";
import { callCreateGroupRestEndpoints, callDeleteGroupRestEndpointsByGroupId } from "../../../../../../endpoints/rest/groups/v1/GroupRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { GroupUserRelationship } from "../../../../../../models/groups/groupUserRelationships/v1/GroupUserRelationship";
import { Group } from "../../../../../../models/groups/v1/Group";
import { Person } from "../../../../../../models/people/v1/Person";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('groupUserRelationship endpoint tests', () => {
var domain: string = "http://localhost:8080";
    var groupUserRelationshipId: number | undefined; 
    var createdDateTime = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;
    var groupName: string = "1";

    var group: Group = new Group();
    group.groupName = groupName;

    var person: Person = new Person();

    var user: User = new User();
    var groupUserRelationship: GroupUserRelationship = new GroupUserRelationship();

    var updatedGroupUserRelationship: GroupUserRelationship = new GroupUserRelationship();
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

    test('call create groupUserRelationship', async () => {
        
        var createPersonResponse = await callCreatePersonRestEndpoints(person, env, domain);
        expect(createPersonResponse.dateOfBirth).toBe(person.dateOfBirth);
        expect(createPersonResponse.firstName).toBe(person.firstName);
        expect(createPersonResponse.middleName).toBe(person.middleName);
        expect(createPersonResponse.surname).toBe(person.surname);
        expect(createPersonResponse.ssn).toBe(person.ssn);
        person.id = createPersonResponse.id;
        user.person = person;

        var userResponse: CreateUserResponse = await callCreateUserRestEndpoints(user, env, domain);
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        user = userResponse.user || new User();
        groupUserRelationship.user = userResponse.user;
        updatedGroupUserRelationship.user = userResponse.user;

        var groupResponse: Group = await callCreateGroupRestEndpoints(
            group, env, domain);
        expect(group.groupName).toBe(groupResponse.groupName);
        group.id = groupResponse.id;
        groupUserRelationship.group = group;
        updatedGroupUserRelationship.group = group;

        var groupUserRelationshipResponse: GroupUserRelationship = await callCreateGroupUserRelationshipRestEndpoints(
            groupUserRelationship, env, domain);
        groupUserRelationshipId = groupUserRelationshipResponse.id;
        expect(groupUserRelationship.group).toBe(groupUserRelationshipResponse.group);
        expect(groupUserRelationship.user).toBe(groupUserRelationshipResponse.user);
    });

    test('call read groupUserRelationship', async () => {
        var groupUserRelationships: GroupUserRelationship[] | undefined = await callReadGroupUserRelationshipRestEndpointsByGroupUserRelationshipId(
            groupUserRelationshipId || 1, env, domain) || [];
        expect(groupUserRelationship.group).toBe(groupUserRelationships[0].group);
        expect(groupUserRelationship.user).toBe(groupUserRelationships[0].user);
    });

    test('call update groupUserRelationship', async () => {
        var groupUserRelationshipResponse: GroupUserRelationship[] = await callUpdateGroupUserRelationshipRestEndpoints(
            updatedGroupUserRelationship, env, domain);
            expect(groupUserRelationship.group).toBe(groupUserRelationshipResponse[0].group);
            expect(groupUserRelationship.user).toBe(groupUserRelationshipResponse[0].user);
    });

    test('call delete groupUserRelationship', async () => {
        var deleteGroupUserRelationshipResponse: boolean = await callDeleteGroupUserRelationshipRestEndpointsByGroupUserRelationshipId(
            groupUserRelationshipId || 1, env, domain);
        expect(true).toBe(deleteGroupUserRelationshipResponse);

        var deleteGroupResponse: boolean = await callDeleteGroupRestEndpointsByGroupId(
            group.id || 1, env, domain);
        expect(true).toBe(deleteGroupResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});