import { useSelector } from "react-redux";
import { RootState } from "../../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateCardOnFileRestEndpoints, callDeleteCardOnFileRestEndpointsByCardOnFileId } from "../../../../../../../endpoints/rest/users/cardsOnFile/v1/CardOnFileRestEndpoints";
import { callCreateCardTransferRestEndpoints, callReadCardTransferRestEndpointsByCardTransferId, callDeleteCardTransferRestEndpointsByCardTransferId, callUpdateCardTransferRestEndpoints } from "../../../../../../../endpoints/rest/users/transfers/cardTransfers/CardTransferRestEndpoints";
import { callCreateTransferRestEndpoints, callDeleteTransferRestEndpointsByTransferId } from "../../../../../../../endpoints/rest/users/transfers/v1/TransferRestEndpoints";
import { Person } from "../../../../../../../models/people/v1/Person";
import { CardOnFile } from "../../../../../../../models/users/cardsOnFile/v1/CardOnFile";
import { CardTransfer } from "../../../../../../../models/users/transfers/cardTransfers/v1/CardTransfer";
import { Transfer } from "../../../../../../../models/users/transfers/v1/Transfer";
import { User } from "../../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('cardTransfer endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var cardTransferId: number | undefined; 

    var cardOnFile: CardOnFile = new CardOnFile();
    cardOnFile.cardNumber = "1";
    cardOnFile.createdDateTime = "4111-11-11T11:11:11Z";
    cardOnFile.cvv = "1";
    cardOnFile.deletedDateTime = "3111-11-11T11:11:11Z";
    cardOnFile.expirationDate = "1111-11-11";
    cardOnFile.isDeleted = false;
    cardOnFile.phoneNumber = "1234";

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
    
    var cardTransfer: CardTransfer = new CardTransfer();

    var updatedCardTransfer: CardTransfer = new CardTransfer();

    test('call create cardTransfer', async () => {
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
        cardOnFile.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        
        var transferResponse: Transfer = await callCreateTransferRestEndpoints(
            transfer, env, domain);
        transfer.id = transferResponse.id;
        cardTransfer.transfer = transfer;
        updatedCardTransfer.transfer = transfer;
        expect(transfer.transferName).toBe(transferResponse.transferName);
        expect(transfer.amount).toBe(transferResponse.amount);
        expect(transfer.createdDateTime).toBe(transferResponse.createdDateTime);
        expect(transfer.isVoided).toBe(transferResponse.isVoided);
        expect(transfer.postedDateTime).toBe(transferResponse.postedDateTime);
        expect(transfer.voidedDateTime).toBe(transferResponse.voidedDateTime);

        var cardOnFileResponse: CardOnFile = await callCreateCardOnFileRestEndpoints(
            cardOnFile, env, domain);
        cardOnFile.id = cardOnFileResponse.id;
        cardTransfer.cardOnFile = cardOnFile;
        updatedCardTransfer.cardOnFile = cardOnFile;
        expect(cardOnFile.cardNumber).toBe(cardOnFileResponse.cardNumber);
        expect(cardOnFile.createdDateTime).toBe(cardOnFileResponse.createdDateTime);
        expect(cardOnFile.cvv).toBe(cardOnFileResponse.cvv);
        expect(cardOnFile.deletedDateTime).toBe(cardOnFileResponse.deletedDateTime);
        expect(cardOnFile.expirationDate).toBe(cardOnFileResponse.expirationDate);
        expect(cardOnFile.isDeleted).toBe(cardOnFileResponse.isDeleted);
        expect(cardOnFile.location).toBe(cardOnFileResponse.location);
        expect(cardOnFile.phoneNumber).toBe(cardOnFileResponse.phoneNumber);
        expect(cardOnFile.user).toBe(cardOnFileResponse.user);

        var cardTransferResponse: CardTransfer = await callCreateCardTransferRestEndpoints(
            cardTransfer, env, domain);
        cardTransferId = cardTransferResponse.id;
        expect(cardTransfer.transfer).toBe(cardTransferResponse.transfer);
    });

    test('call read cardTransfer', async () => {
        var cardTransfers: CardTransfer[] | undefined = await callReadCardTransferRestEndpointsByCardTransferId(
            cardTransferId || 1, env, domain) || [];
        expect(cardTransfers[0].transfer).toBe(cardTransfer.transfer);
    });

    test('call update cardTransfer', async () => {
        var cardTransferResponse: CardTransfer[] = await callUpdateCardTransferRestEndpoints(
            updatedCardTransfer, env, domain);
        expect(updatedCardTransfer.transfer).toBe(cardTransferResponse[0].transfer);
    });

    test('call delete cardTransfer', async () => {
        var deleteCardTransferResponse: boolean = await callDeleteCardTransferRestEndpointsByCardTransferId(
            cardTransferId || 1, env, domain);
        expect(true).toBe(deleteCardTransferResponse);

        var deleteCardOnFileResponse: boolean = await callDeleteCardOnFileRestEndpointsByCardOnFileId(
            cardOnFile.id || 1, env, domain);
        expect(true).toBe(deleteCardOnFileResponse);

        var deleteTransferResponse: boolean = await callDeleteTransferRestEndpointsByTransferId(
            transfer.id || 1, env, domain);
        expect(true).toBe(deleteTransferResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1omain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});