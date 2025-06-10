import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreatePhoneNumberHistoryRestEndpoints, 
    callReadPhoneNumberHistoryRestEndpointsByPhoneNumberHistoryId, 
    callDeletePhoneNumberHistoryRestEndpointsByPhoneNumberHistoryId, 
    callUpdatePhoneNumberHistoryRestEndpoints } from "../../../../../../endpoints/rest/users/phoneNumbersHistory/v1/PhoneNumberHistoryRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { PhoneNumberHistory } from "../../../../../../models/users/phoneNumbersHistory/v1/PhoneNumberHistory";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('phoneNumberHistory endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var phoneNumberHistoryId: number | undefined; 

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

    var phoneNumberHistory: PhoneNumberHistory = new PhoneNumberHistory();
    phoneNumberHistory.createdDateTime = "1111-11-11T11:11:11Z";
    phoneNumberHistory.phoneNumber = "11";

    var updatedPhoneNumberHistory: PhoneNumberHistory = new PhoneNumberHistory();
    updatedPhoneNumberHistory.createdDateTime = "2111-11-11T11:11:11Z";
    updatedPhoneNumberHistory.phoneNumber = "12";

    test('call create phoneNumberHistory', async () => {
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
        phoneNumberHistory.user = user;
        updatedPhoneNumberHistory.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        
        var phoneNumberHistoryResponse: PhoneNumberHistory = await callCreatePhoneNumberHistoryRestEndpoints(
            phoneNumberHistory, env, domain);
        phoneNumberHistoryId = phoneNumberHistoryResponse.id;
        expect(phoneNumberHistory.createdDateTime).toBe(phoneNumberHistoryResponse.createdDateTime);
        expect(phoneNumberHistory.phoneNumber).toBe(phoneNumberHistoryResponse.phoneNumber);
        expect(phoneNumberHistory.user).toBe(phoneNumberHistoryResponse.user);
    });

    test('call read phoneNumberHistory', async () => {
        var phoneNumberHistoryResponse: PhoneNumberHistory[] | undefined = await callReadPhoneNumberHistoryRestEndpointsByPhoneNumberHistoryId(
            phoneNumberHistoryId || 1, env, domain) || [];
        expect(phoneNumberHistory.createdDateTime).toBe(phoneNumberHistoryResponse[0].createdDateTime);
        expect(phoneNumberHistory.phoneNumber).toBe(phoneNumberHistoryResponse[0].phoneNumber);
        expect(phoneNumberHistory.user).toBe(phoneNumberHistoryResponse[0].user);
    });

    test('call update phoneNumberHistory', async () => {
        var phoneNumberHistoryResponse: PhoneNumberHistory[] = await callUpdatePhoneNumberHistoryRestEndpoints(
            updatedPhoneNumberHistory, env, domain);
        expect(updatedPhoneNumberHistory.createdDateTime).toBe(phoneNumberHistoryResponse[0].createdDateTime);
        expect(updatedPhoneNumberHistory.phoneNumber).toBe(phoneNumberHistoryResponse[0].phoneNumber);
        expect(updatedPhoneNumberHistory.user).toBe(phoneNumberHistoryResponse[0].user);
    });

    test('call delete phoneNumberHistory', async () => {
        var deletePhoneNumberHistoryResponse: boolean = await callDeletePhoneNumberHistoryRestEndpointsByPhoneNumberHistoryId(
            phoneNumberHistoryId || 1, env, domain);
        expect(true).toBe(deletePhoneNumberHistoryResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});