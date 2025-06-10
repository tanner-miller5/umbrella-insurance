import { callCreateAccountBalanceTransactionStatusRestEndpoints, callReadAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId, callDeleteAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId, callUpdateAccountBalanceTransactionStatusRestEndpoints } from "../../../../../../../../endpoints/rest/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionStatuses/v1/AccountBalanceTransactionStatusRestEndpoints";
import { AccountBalanceTransactionStatus } from "../../../../../../../../models/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionStatuses/v1/AccountBalanceTransactionStatus";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('accountBalanceTransactionStatus endpoint tests', () => {
    var accountBalanceTransactionStatusId: number | undefined; 
    var accountBalanceTransactionStatus: AccountBalanceTransactionStatus = new AccountBalanceTransactionStatus();
    accountBalanceTransactionStatus.accountBalanceTransactionStatusName = "1";

    var updatedAccountBalanceTransactionStatus: AccountBalanceTransactionStatus = new AccountBalanceTransactionStatus();
    updatedAccountBalanceTransactionStatus.accountBalanceTransactionStatusName = "2";

    var domain: string = "http://localhost:8080";

    test('call create accountBalanceTransactionStatus', async () => {
        var accountBalanceTransactionStatusResponse: AccountBalanceTransactionStatus = await callCreateAccountBalanceTransactionStatusRestEndpoints(
            accountBalanceTransactionStatus, env, domain);
        accountBalanceTransactionStatusId = accountBalanceTransactionStatusResponse.id;
        expect(accountBalanceTransactionStatus.accountBalanceTransactionStatusName).toBe(accountBalanceTransactionStatusResponse.accountBalanceTransactionStatusName);
    });

    test('call read accountBalanceTransactionStatus', async () => {
        var accountBalanceTransactionStatuss: AccountBalanceTransactionStatus[] | undefined = await callReadAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId(
            accountBalanceTransactionStatusId || 1, env, domain) || [];
        expect(accountBalanceTransactionStatuss[0].accountBalanceTransactionStatusName).toBe(accountBalanceTransactionStatus.accountBalanceTransactionStatusName);
    });

    test('call update accountBalanceTransactionStatus', async () => {
        var accountBalanceTransactionStatusResponse: AccountBalanceTransactionStatus[] = await callUpdateAccountBalanceTransactionStatusRestEndpoints(
            updatedAccountBalanceTransactionStatus, env, domain);
        expect(updatedAccountBalanceTransactionStatus.accountBalanceTransactionStatusName).toBe(accountBalanceTransactionStatusResponse[0].accountBalanceTransactionStatusName);
    });

    test('call delete accountBalanceTransactionStatus', async () => {
        var deleteAccountBalanceTransactionStatusResponse: boolean = await callDeleteAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId(
            accountBalanceTransactionStatusId || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceTransactionStatusResponse);
    });
});