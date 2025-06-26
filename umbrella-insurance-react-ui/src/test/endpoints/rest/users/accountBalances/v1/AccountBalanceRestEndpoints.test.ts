import { useSelector } from "react-redux";
import { RootState } from "../../../../../../redux/store/Store";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateAccountBalanceTypeRestEndpoints, callDeleteAccountBalanceTypeRestEndpointsByAccountBalanceTypeId } from "../../../../../../endpoints/rest/users/accountBalances/accountBalanceTypes/AccountBalanceTypeRestEndpoints";
import { callCreateAccountBalanceRestEndpoints, callReadAccountBalanceRestEndpointsByAccountBalanceId, callDeleteAccountBalanceRestEndpointsByAccountBalanceId, callUpdateAccountBalanceRestEndpoints } from "../../../../../../endpoints/rest/users/accountBalances/v1/AccountBalanceRestEndpoints";
import { Person } from "../../../../../../models/people/v1/Person";
import { AccountBalanceType } from "../../../../../../models/users/accountBalances/accountBalanceTypes/v1/AccountBalanceType";
import { AccountBalance } from "../../../../../../models/users/accountBalances/v1/AccountBalance";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('accountBalance endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var accountBalanceId: number | undefined; 

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

    var accountBalanceType: AccountBalanceType = new AccountBalanceType();
    accountBalanceType.accountBalanceTypeName = "1";

    var accountBalance: AccountBalance = new AccountBalance();
    accountBalance.accountBalanceValue = 2;
    accountBalance.updatedDateTime = "1111-11-11T11:11:11Z";

    var updatedAccountBalance: AccountBalance = new AccountBalance();
    updatedAccountBalance.accountBalanceValue = 22;
    updatedAccountBalance.updatedDateTime = "2111-11-11T11:11:11Z";

    test('call create accountBalance', async () => {
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
        accountBalance.user = user;
        updatedAccountBalance.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        
        var accountBalanceTypeResponse: AccountBalanceType = await callCreateAccountBalanceTypeRestEndpoints(
            accountBalanceType, env, domain);
        accountBalanceType.id = accountBalanceTypeResponse.id;
        accountBalance.accountBalanceType = accountBalanceType;
        updatedAccountBalance.accountBalanceType = accountBalanceType;
        expect(accountBalanceType.accountBalanceTypeName).toBe(accountBalanceTypeResponse.accountBalanceTypeName);

        var accountBalanceResponse: AccountBalance = await callCreateAccountBalanceRestEndpoints(
            accountBalance, env, domain);
        accountBalanceId = accountBalanceResponse.id;
        expect(accountBalance.accountBalanceType).toBe(accountBalanceResponse.accountBalanceType);
        expect(accountBalance.accountBalanceValue).toBe(accountBalanceResponse.accountBalanceValue);
        expect(accountBalance.updatedDateTime).toBe(accountBalanceResponse.updatedDateTime);
        expect(accountBalance.user).toBe(accountBalanceResponse.user);
    });

    test('call read accountBalance', async () => {
        var accountBalanceResponse: AccountBalance[] | undefined = await callReadAccountBalanceRestEndpointsByAccountBalanceId(
            "", accountBalanceId || 1, env, domain) || [];
        expect(accountBalance.accountBalanceType).toBe(accountBalanceResponse[0].accountBalanceType);
        expect(accountBalance.accountBalanceValue).toBe(accountBalanceResponse[0].accountBalanceValue);
        expect(accountBalance.updatedDateTime).toBe(accountBalanceResponse[0].updatedDateTime);
        expect(accountBalance.user).toBe(accountBalanceResponse[0].user);
    });

    test('call update accountBalance', async () => {
        var accountBalanceResponse: AccountBalance[] = await callUpdateAccountBalanceRestEndpoints(
            updatedAccountBalance, env, domain);
        expect(updatedAccountBalance.accountBalanceType).toBe(accountBalanceResponse[0].accountBalanceType);
        expect(updatedAccountBalance.accountBalanceValue).toBe(accountBalanceResponse[0].accountBalanceValue);
        expect(updatedAccountBalance.updatedDateTime).toBe(accountBalanceResponse[0].updatedDateTime);
        expect(updatedAccountBalance.user).toBe(accountBalanceResponse[0].user);
    });

    test('call delete accountBalance', async () => {
        var deleteAccountBalanceResponse: boolean = await callDeleteAccountBalanceRestEndpointsByAccountBalanceId(
            accountBalanceId || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceResponse);

        var deleteAccountBalanceTypeResponse: boolean = await callDeleteAccountBalanceTypeRestEndpointsByAccountBalanceTypeId(
            accountBalanceType.id || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceTypeResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});