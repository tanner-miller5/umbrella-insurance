import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateBankAccountRestEndpoints, callReadBankAccountRestEndpointsByBankAccountId, callDeleteBankAccountRestEndpointsByBankAccountId, callUpdateBankAccountRestEndpoints } from "../../../../../../endpoints/rest/users/bankAccounts/v1/BankAccountRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { BankAccount } from "../../../../../../models/users/bankAccounts/v1/BankAccount";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('bankAccount endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var bankAccountId: number | undefined; 

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
   
    var bankAccount: BankAccount = new BankAccount();
    bankAccount.accountNumber = "1";
    bankAccount.createdDateTime = "1111-11-11T11:11:11Z";
    bankAccount.routingNumber = "12";

    var updatedBankAccount: BankAccount = new BankAccount();
    updatedBankAccount.accountNumber = "1";
    updatedBankAccount.createdDateTime = "1111-11-11T11:11:11Z";
    updatedBankAccount.routingNumber = "12";

    test('call create bankAccount', async () => {
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
        bankAccount.user = user;
        updatedBankAccount.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);

        var bankAccountResponse: BankAccount = await callCreateBankAccountRestEndpoints(
            bankAccount, env, domain);
        bankAccountId = bankAccountResponse.id;
        expect(bankAccount.accountNumber).toBe(bankAccountResponse.accountNumber);
        expect(bankAccount.createdDateTime).toBe(bankAccountResponse.createdDateTime);
        expect(bankAccount.routingNumber).toBe(bankAccountResponse.routingNumber);
    });

    test('call read bankAccount', async () => {
        var bankAccountResponse: BankAccount[] | undefined = await callReadBankAccountRestEndpointsByBankAccountId(
            bankAccountId || 1, env, domain) || [];
        expect(bankAccount.accountNumber).toBe(bankAccountResponse[0].accountNumber);
        expect(bankAccount.createdDateTime).toBe(bankAccountResponse[0].createdDateTime);
        expect(bankAccount.routingNumber).toBe(bankAccountResponse[0].routingNumber);
    });

    test('call update bankAccount', async () => {
        var bankAccountResponse: BankAccount[] = await callUpdateBankAccountRestEndpoints(
            updatedBankAccount, env, domain);
        expect(updatedBankAccount.accountNumber).toBe(bankAccountResponse[0].accountNumber);
        expect(updatedBankAccount.createdDateTime).toBe(bankAccountResponse[0].createdDateTime);
        expect(updatedBankAccount.routingNumber).toBe(bankAccountResponse[0].routingNumber);
    });

    test('call delete bankAccount', async () => {
        var deleteBankAccountResponse: boolean = await callDeleteBankAccountRestEndpointsByBankAccountId(
            bankAccountId || 1, env, domain);
        expect(true).toBe(deleteBankAccountResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});