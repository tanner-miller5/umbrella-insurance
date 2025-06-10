import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateTransferRestEndpoints, callReadTransferRestEndpointsByTransferId, callDeleteTransferRestEndpointsByTransferId, callUpdateTransferRestEndpoints } from "../../../../../../endpoints/rest/users/transfers/v1/TransferRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { Transfer } from "../../../../../../models/users/transfers/v1/Transfer";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('transfer endpoint tests', () => {
var domain: string = "http://localhost:8080";
    var transferId: number | undefined; 

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

    var transfer: Transfer = new Transfer();
    transfer.transferName = "1";
    transfer.amount = 1;
    transfer.createdDateTime = "1111-11-11T11:11:11Z";
    transfer.isVoided = true;
    transfer.postedDateTime = "1111-11-11T11:11:11Z";
    transfer.voidedDateTime = "2111-11-11T11:11:11Z";

    var updatedTransfer: Transfer = new Transfer();
    updatedTransfer.transferName = "1";
    updatedTransfer.amount = 1;
    updatedTransfer.createdDateTime = "1111-11-11T11:11:11Z";
    updatedTransfer.isVoided = true;
    updatedTransfer.postedDateTime = "1111-11-11T11:11:11Z";
    updatedTransfer.voidedDateTime = "2111-11-11T11:11:11Z";

    test('call create transfer', async () => {
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
        transfer.user = user;
        updatedTransfer.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        
        var transferResponse: Transfer = await callCreateTransferRestEndpoints(
            transfer, env, domain);
        transferId = transferResponse.id;
        expect(transfer.transferName).toBe(transferResponse.transferName);
        expect(transfer.amount).toBe(transferResponse.amount);
        expect(transfer.createdDateTime).toBe(transferResponse.createdDateTime);
        expect(transfer.isVoided).toBe(transferResponse.isVoided);
        expect(transfer.postedDateTime).toBe(transferResponse.postedDateTime);
        expect(transfer.voidedDateTime).toBe(transferResponse.voidedDateTime);
    });

    test('call read transfer', async () => {
        var transferResponse: Transfer[] | undefined = await callReadTransferRestEndpointsByTransferId(
            transferId || 1, env, domain) || [];
        expect(transfer.transferName).toBe(transferResponse[0].transferName);
        expect(transfer.amount).toBe(transferResponse[0].amount);
        expect(transfer.createdDateTime).toBe(transferResponse[0].createdDateTime);
        expect(transfer.isVoided).toBe(transferResponse[0].isVoided);
        expect(transfer.postedDateTime).toBe(transferResponse[0].postedDateTime);
        expect(transfer.voidedDateTime).toBe(transferResponse[0].voidedDateTime);
    });

    test('call update transfer', async () => {
        var transferResponse: Transfer[] = await callUpdateTransferRestEndpoints(
            updatedTransfer, env, domain);
        expect(updatedTransfer.transferName).toBe(transferResponse[0].transferName);
        expect(updatedTransfer.amount).toBe(transferResponse[0].amount);
        expect(updatedTransfer.createdDateTime).toBe(transferResponse[0].createdDateTime);
        expect(updatedTransfer.isVoided).toBe(transferResponse[0].isVoided);
        expect(updatedTransfer.postedDateTime).toBe(transferResponse[0].postedDateTime);
        expect(updatedTransfer.voidedDateTime).toBe(transferResponse[0].voidedDateTime);
    });

    test('call delete transfer', async () => {
        var deleteTransferResponse: boolean = await callDeleteTransferRestEndpointsByTransferId(
            transferId || 1, env, domain);
        expect(true).toBe(deleteTransferResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});