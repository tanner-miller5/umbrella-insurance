import { callCreateAccountBalanceTypeRestEndpoints, callReadAccountBalanceTypeRestEndpointsByAccountBalanceTypeId, callDeleteAccountBalanceTypeRestEndpointsByAccountBalanceTypeId, callUpdateAccountBalanceTypeRestEndpoints } from "../../../../../../../endpoints/rest/users/accountBalances/accountBalanceTypes/AccountBalanceTypeRestEndpoints";
import { AccountBalanceType } from "../../../../../../../models/users/accountBalances/accountBalanceTypes/v1/AccountBalanceType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('accountBalanceType endpoint tests', () => {
    var accountBalanceTypeId: number | undefined; 
    var accountBalanceType: AccountBalanceType = new AccountBalanceType();
    accountBalanceType.accountBalanceTypeName = "1";

    var updatedAccountBalanceType: AccountBalanceType = new AccountBalanceType();
    updatedAccountBalanceType.accountBalanceTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create accountBalanceType', async () => {
        var accountBalanceTypeResponse: AccountBalanceType = await callCreateAccountBalanceTypeRestEndpoints(
            accountBalanceType, env, domain);
        accountBalanceTypeId = accountBalanceTypeResponse.id;
        expect(accountBalanceType.accountBalanceTypeName).toBe(accountBalanceTypeResponse.accountBalanceTypeName);
    });

    test('call read accountBalanceType', async () => {
        var accountBalanceTypes: AccountBalanceType[] | undefined = await callReadAccountBalanceTypeRestEndpointsByAccountBalanceTypeId(
            accountBalanceTypeId || 1, env, domain) || [];
        expect(accountBalanceTypes[0].accountBalanceTypeName).toBe(accountBalanceType.accountBalanceTypeName);
    });

    test('call update accountBalanceType', async () => {
        var accountBalanceTypeResponse: AccountBalanceType[] = await callUpdateAccountBalanceTypeRestEndpoints(
            updatedAccountBalanceType, env, domain);
        expect(updatedAccountBalanceType.accountBalanceTypeName).toBe(accountBalanceTypeResponse[0].accountBalanceTypeName);
    });

    test('call delete accountBalanceType', async () => {
        var deleteAccountBalanceTypeResponse: boolean = await callDeleteAccountBalanceTypeRestEndpointsByAccountBalanceTypeId(
            accountBalanceTypeId || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceTypeResponse);
    });
});