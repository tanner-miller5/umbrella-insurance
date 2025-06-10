import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateEncryptionKeyRestEndpoints, callReadEncryptionKeyRestEndpointsByEncryptionKeyId, callDeleteEncryptionKeyRestEndpointsByEncryptionKeyId, callUpdateEncryptionKeyRestEndpoints } from "../../../../../../endpoints/rest/users/encryptionKeys/v1/EncryptionKeyRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { EncryptionKey } from "../../../../../../models/users/encryptedKeys/v1/EncryptionKey";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('encryptionKey endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var encryptionKeyId: number | undefined; 

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
    
    var encryptionKey: EncryptionKey = new EncryptionKey();
    encryptionKey.createdDateTime = "1222-11-11T11:11:11Z";
    encryptionKey.encryptionKeyPrivateKey = "1";
    encryptionKey.encryptionKeyPublicKey = "1";

    var updatedEncryptionKey: EncryptionKey = new EncryptionKey();
    updatedEncryptionKey.createdDateTime = "2222-11-11T11:11:11Z";
    updatedEncryptionKey.encryptionKeyPrivateKey = "3";
    updatedEncryptionKey.encryptionKeyPublicKey = "4";

    test('call create encryptionKey', async () => {
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
        encryptionKey.user = user;
        updatedEncryptionKey.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);

        var encryptionKeyResponse: EncryptionKey = await callCreateEncryptionKeyRestEndpoints(
            encryptionKey, env, domain);
        encryptionKeyId = encryptionKeyResponse.id;
        expect(encryptionKey.createdDateTime).toBe(encryptionKeyResponse.createdDateTime);
        expect(encryptionKey.encryptionKeyPrivateKey).toBe(encryptionKeyResponse.encryptionKeyPrivateKey);
        expect(encryptionKey.encryptionKeyPublicKey).toBe(encryptionKeyResponse.encryptionKeyPublicKey);
        expect(encryptionKey.user).toBe(encryptionKeyResponse.user);
    });

    test('call read encryptionKey', async () => {
        var encryptionKeyResponse: EncryptionKey[] | undefined = await callReadEncryptionKeyRestEndpointsByEncryptionKeyId(
            encryptionKeyId || 1, env, domain) || [];
        expect(encryptionKey.createdDateTime).toBe(encryptionKeyResponse[0].createdDateTime);
        expect(encryptionKey.encryptionKeyPrivateKey).toBe(encryptionKeyResponse[0].encryptionKeyPrivateKey);
        expect(encryptionKey.encryptionKeyPublicKey).toBe(encryptionKeyResponse[0].encryptionKeyPublicKey);
        expect(encryptionKey.user).toBe(encryptionKeyResponse[0].user);
    });

    test('call update encryptionKey', async () => {
        var encryptionKeyResponse: EncryptionKey[] = await callUpdateEncryptionKeyRestEndpoints(
            updatedEncryptionKey, env, domain);
        expect(updatedEncryptionKey.createdDateTime).toBe(encryptionKeyResponse[0].createdDateTime);
        expect(updatedEncryptionKey.encryptionKeyPrivateKey).toBe(encryptionKeyResponse[0].encryptionKeyPrivateKey);
        expect(updatedEncryptionKey.encryptionKeyPublicKey).toBe(encryptionKeyResponse[0].encryptionKeyPublicKey);
        expect(updatedEncryptionKey.user).toBe(encryptionKeyResponse[0].user);
    });

    test('call delete encryptionKey', async () => {
        var deleteEncryptionKeyResponse: boolean = await callDeleteEncryptionKeyRestEndpointsByEncryptionKeyId(
            encryptionKeyId || 1, env, domain);
        expect(true).toBe(deleteEncryptionKeyResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});