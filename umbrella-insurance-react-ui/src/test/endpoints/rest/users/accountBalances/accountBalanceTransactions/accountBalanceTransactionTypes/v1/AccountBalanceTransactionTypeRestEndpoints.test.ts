import { callCreateAccountBalanceTransactionTypeRestEndpoints, callReadAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId, callDeleteAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId, callUpdateAccountBalanceTransactionTypeRestEndpoints } from "../../../../../../../../endpoints/rest/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionTypes/v1/AccountBalanceTransactionTypeRestEndpoints";
import { AccountBalanceTransactionType } from "../../../../../../../../models/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionTypes/v1/AccountBalanceTransactionType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('accountBalanceTransactionType endpoint tests', () => {
    var accountBalanceTransactionTypeId: number | undefined; 
    var accountBalanceTransactionType: AccountBalanceTransactionType = new AccountBalanceTransactionType();
    accountBalanceTransactionType.accountBalanceTransactionTypeName = "1";

    var updatedAccountBalanceTransactionType: AccountBalanceTransactionType = new AccountBalanceTransactionType();
    updatedAccountBalanceTransactionType.accountBalanceTransactionTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create accountBalanceTransactionType', async () => {
        var accountBalanceTransactionTypeResponse: AccountBalanceTransactionType = await callCreateAccountBalanceTransactionTypeRestEndpoints(
            accountBalanceTransactionType, env, domain);
        accountBalanceTransactionTypeId = accountBalanceTransactionTypeResponse.id;
        expect(accountBalanceTransactionType.accountBalanceTransactionTypeName).toBe(accountBalanceTransactionTypeResponse.accountBalanceTransactionTypeName);
    });

    test('call read accountBalanceTransactionType', async () => {
        var accountBalanceTransactionTypes: AccountBalanceTransactionType[] | undefined = await callReadAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId(
            accountBalanceTransactionTypeId || 1, env, domain) || [];
        expect(accountBalanceTransactionTypes[0].accountBalanceTransactionTypeName).toBe(accountBalanceTransactionType.accountBalanceTransactionTypeName);
    });

    test('call update accountBalanceTransactionType', async () => {
        var accountBalanceTransactionTypeResponse: AccountBalanceTransactionType[] = await callUpdateAccountBalanceTransactionTypeRestEndpoints(
            updatedAccountBalanceTransactionType, env, domain);
        expect(updatedAccountBalanceTransactionType.accountBalanceTransactionTypeName).toBe(accountBalanceTransactionTypeResponse[0].accountBalanceTransactionTypeName);
    });

    test('call delete accountBalanceTransactionType', async () => {
        var deleteAccountBalanceTransactionTypeResponse: boolean = await callDeleteAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId(
            accountBalanceTransactionTypeId || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceTransactionTypeResponse);
    });
});