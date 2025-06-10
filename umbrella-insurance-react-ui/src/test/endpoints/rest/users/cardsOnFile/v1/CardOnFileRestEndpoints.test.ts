import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateCardOnFileRestEndpoints, callReadCardOnFileRestEndpointsByCardOnFileId, callDeleteCardOnFileRestEndpointsByCardOnFileId, callUpdateCardOnFileRestEndpoints } from "../../../../../../endpoints/rest/users/cardsOnFile/v1/CardOnFileRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { CardOnFile } from "../../../../../../models/users/cardsOnFile/v1/CardOnFile";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('cardOnFile endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var cardOnFileId: number | undefined; 

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

    var cardOnFile: CardOnFile = new CardOnFile();
    cardOnFile.cardNumber = "1";
    cardOnFile.createdDateTime = "4111-11-11T11:11:11Z";
    cardOnFile.cvv = "1";
    cardOnFile.deletedDateTime = "3111-11-11T11:11:11Z";
    cardOnFile.expirationDate = "1111-11-11";
    cardOnFile.isDeleted = false;
    cardOnFile.phoneNumber = "1234";

    var updatedCardOnFile: CardOnFile = new CardOnFile();
    updatedCardOnFile.cardNumber = "1";
    updatedCardOnFile.createdDateTime = "1111-11-11T11:11:11Z";
    updatedCardOnFile.cvv = "1";
    updatedCardOnFile.deletedDateTime = "2111-11-11T11:11:11Z";
    updatedCardOnFile.expirationDate = "1111-11-11";
    updatedCardOnFile.isDeleted = false;
    updatedCardOnFile.phoneNumber = "1234";

    test('call create cardOnFile', async () => {
        var createPersonResponse = await callCreatePersonRestEndpoints(person, env, domain);
        expect(createPersonResponse.dateOfBirth).toBe(person.dateOfBirth);
        expect(createPersonResponse.firstName).toBe(person.firstName);
        expect(createPersonResponse.middleName).toBe(person.middleName);
        expect(createPersonResponse.surname).toBe(person.surname);
        expect(createPersonResponse.ssn).toBe(person.ssn);
        person = createPersonResponse;
        user.person = person;

        var userResponse: CreateUserResponse = await callCreateUserRestEndpoints(user, env, domain);
        user = userResponse.user || new User();
        cardOnFile.user = user;
        updatedCardOnFile.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        
        var cardOnFileResponse: CardOnFile = await callCreateCardOnFileRestEndpoints(
            cardOnFile, env, domain);
        cardOnFileId = cardOnFileResponse.id;
        expect(cardOnFile.cardNumber).toBe(cardOnFileResponse.cardNumber);
        expect(cardOnFile.createdDateTime).toBe(cardOnFileResponse.createdDateTime);
        expect(cardOnFile.cvv).toBe(cardOnFileResponse.cvv);
        expect(cardOnFile.deletedDateTime).toBe(cardOnFileResponse.deletedDateTime);
        expect(cardOnFile.expirationDate).toBe(cardOnFileResponse.expirationDate);
        expect(cardOnFile.isDeleted).toBe(cardOnFileResponse.isDeleted);
        expect(cardOnFile.location).toBe(cardOnFileResponse.location);
        expect(cardOnFile.phoneNumber).toBe(cardOnFileResponse.phoneNumber);
        expect(cardOnFile.user).toBe(cardOnFileResponse.user);
    });

    test('call read cardOnFile', async () => {
        var cardOnFileResponse: CardOnFile[] | undefined = await callReadCardOnFileRestEndpointsByCardOnFileId(
            cardOnFileId || 1, env, domain) || [];
        expect(cardOnFile.cardNumber).toBe(cardOnFileResponse[0].cardNumber);
        expect(cardOnFile.createdDateTime).toBe(cardOnFileResponse[0].createdDateTime);
        expect(cardOnFile.cvv).toBe(cardOnFileResponse[0].cvv);
        expect(cardOnFile.deletedDateTime).toBe(cardOnFileResponse[0].deletedDateTime);
        expect(cardOnFile.expirationDate).toBe(cardOnFileResponse[0].expirationDate);
        expect(cardOnFile.isDeleted).toBe(cardOnFileResponse[0].isDeleted);
        expect(cardOnFile.location).toBe(cardOnFileResponse[0].location);
        expect(cardOnFile.phoneNumber).toBe(cardOnFileResponse[0].phoneNumber);
        expect(cardOnFile.user).toBe(cardOnFileResponse[0].user);
    });

    test('call update cardOnFile', async () => {
        var cardOnFileResponse: CardOnFile[] = await callUpdateCardOnFileRestEndpoints(
            updatedCardOnFile, env, domain);
        expect(updatedCardOnFile.cardNumber).toBe(cardOnFileResponse[0].cardNumber);
        expect(updatedCardOnFile.createdDateTime).toBe(cardOnFileResponse[0].createdDateTime);
        expect(updatedCardOnFile.cvv).toBe(cardOnFileResponse[0].cvv);
        expect(updatedCardOnFile.deletedDateTime).toBe(cardOnFileResponse[0].deletedDateTime);
        expect(updatedCardOnFile.expirationDate).toBe(cardOnFileResponse[0].expirationDate);
        expect(updatedCardOnFile.isDeleted).toBe(cardOnFileResponse[0].isDeleted);
        expect(updatedCardOnFile.location).toBe(cardOnFileResponse[0].location);
        expect(updatedCardOnFile.phoneNumber).toBe(cardOnFileResponse[0].phoneNumber);
        expect(updatedCardOnFile.user).toBe(cardOnFileResponse[0].user);
    });

    test('call delete cardOnFile', async () => {
        var deleteCardOnFileResponse: boolean = await callDeleteCardOnFileRestEndpointsByCardOnFileId(
            cardOnFileId || 1, env, domain);
        expect(true).toBe(deleteCardOnFileResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});