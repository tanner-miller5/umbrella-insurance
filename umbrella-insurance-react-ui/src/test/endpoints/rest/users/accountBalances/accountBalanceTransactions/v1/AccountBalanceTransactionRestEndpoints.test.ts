import { callCreateUnitRestEndpoints, callDeleteUnitRestEndpointsByUnitId } from "../../../../../../../endpoints/rest/units/v1/UnitRestEndpoints";
import { callCreateAccountBalanceTransactionStatusRestEndpoints, callDeleteAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId } from "../../../../../../../endpoints/rest/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionStatuses/v1/AccountBalanceTransactionStatusRestEndpoints";
import { callCreateAccountBalanceTransactionTypeRestEndpoints, callDeleteAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId } from "../../../../../../../endpoints/rest/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionTypes/v1/AccountBalanceTransactionTypeRestEndpoints";
import { callCreateAccountBalanceTransactionRestEndpoints, callReadAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId, callUpdateAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId, callDeleteAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId } from "../../../../../../../endpoints/rest/users/accountBalances/accountBalanceTransactions/v1/AccountBalanceTransactionRestEndpoints";
import { Unit } from "../../../../../../../models/units/v1/Unit";
import { AccountBalanceTransactionStatus } from "../../../../../../../models/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionStatuses/v1/AccountBalanceTransactionStatus";
import { AccountBalanceTransactionType } from "../../../../../../../models/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionTypes/v1/AccountBalanceTransactionType";
import { AccountBalanceTransaction } from "../../../../../../../models/users/accountBalances/accountBalanceTransactions/v1/AccountBalanceTransaction";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('accountBalanceTransaction endpoint tests', () => {
    var accountBalanceTransactionId: number | undefined; 

    var accountBalanceTransactionType: AccountBalanceTransactionType = new AccountBalanceTransactionType();
    accountBalanceTransactionType.accountBalanceTransactionTypeName = "12";
    var accountBalanceTransactionStatus: AccountBalanceTransactionStatus = new AccountBalanceTransactionStatus();
    accountBalanceTransactionStatus.accountBalanceTransactionStatusName = "123";
    var unit: Unit = new Unit();
    unit.unitName = "1234";

    var accountBalanceTransaction: AccountBalanceTransaction = new AccountBalanceTransaction();
    accountBalanceTransaction.accountBalanceTransactionName = "1";
    accountBalanceTransaction.amount = 2;
    accountBalanceTransaction.createdDateTime = "1111-11-11T11:11:11Z";

    var updatedAccountBalanceTransaction: AccountBalanceTransaction = new AccountBalanceTransaction();
    updatedAccountBalanceTransaction.accountBalanceTransactionName = "2";
    updatedAccountBalanceTransaction.amount = 3;
    updatedAccountBalanceTransaction.createdDateTime = "2111-11-11T11:11:11Z";

    var domain: string = "http://localhost:8080";

    test('call create accountBalanceTransaction', async () => {
        var unitResponse: Unit = await callCreateUnitRestEndpoints(
            unit, env, domain);
        unit.id = unitResponse.id;
        accountBalanceTransaction.unit = unit;
        updatedAccountBalanceTransaction.unit = unit;
        expect(unit.unitName).toBe(unitResponse.unitName);
        
        var accountBalanceTransactionTypeResponse: AccountBalanceTransactionType = await callCreateAccountBalanceTransactionTypeRestEndpoints(
            accountBalanceTransactionType, env, domain);
        accountBalanceTransactionType.id = accountBalanceTransactionTypeResponse.id;
        accountBalanceTransaction.accountBalanceTransactionType = accountBalanceTransactionType;
        updatedAccountBalanceTransaction.accountBalanceTransactionType = accountBalanceTransactionType;
        expect(accountBalanceTransactionType.accountBalanceTransactionTypeName).toBe(accountBalanceTransactionTypeResponse.accountBalanceTransactionTypeName);

        var accountBalanceTransactionStatusResponse: AccountBalanceTransactionStatus = await callCreateAccountBalanceTransactionStatusRestEndpoints(
            accountBalanceTransactionStatus, env, domain);
        accountBalanceTransactionStatus.id = accountBalanceTransactionStatusResponse.id;
        accountBalanceTransaction.accountBalanceTransactionStatus = accountBalanceTransactionStatus;
        updatedAccountBalanceTransaction.accountBalanceTransactionStatus = accountBalanceTransactionStatus;
        expect(accountBalanceTransactionStatus.accountBalanceTransactionStatusName).toBe(accountBalanceTransactionStatusResponse.accountBalanceTransactionStatusName);

        var accountBalanceTransactionResponse: AccountBalanceTransaction = await callCreateAccountBalanceTransactionRestEndpoints(
            "",accountBalanceTransaction, env, domain);
        accountBalanceTransactionId = accountBalanceTransactionResponse.id;
        expect(accountBalanceTransaction.accountBalanceTransactionName).toBe(accountBalanceTransactionResponse.accountBalanceTransactionName);
        expect(accountBalanceTransaction.accountBalanceTransactionStatus).toBe(accountBalanceTransactionResponse.accountBalanceTransactionStatus);
        expect(accountBalanceTransaction.amount).toBe(accountBalanceTransactionResponse.amount);
        expect(accountBalanceTransaction.accountBalanceTransactionType).toBe(accountBalanceTransactionResponse.accountBalanceTransactionType);
        expect(accountBalanceTransaction.createdDateTime).toBe(accountBalanceTransactionResponse.createdDateTime);
        expect(accountBalanceTransaction.unit).toBe(accountBalanceTransactionResponse.unit);
    });

    test('call read accountBalanceTransaction', async () => {
        var accountBalanceTransactionResponse: AccountBalanceTransaction[] | undefined = await callReadAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId(
            "",accountBalanceTransactionId || 1, env, domain) || [];
        expect(accountBalanceTransaction.accountBalanceTransactionName).toBe(accountBalanceTransactionResponse[0].accountBalanceTransactionName);
        expect(accountBalanceTransaction.accountBalanceTransactionStatus).toBe(accountBalanceTransactionResponse[0].accountBalanceTransactionStatus);
        expect(accountBalanceTransaction.amount).toBe(accountBalanceTransactionResponse[0].amount);
        expect(accountBalanceTransaction.accountBalanceTransactionType).toBe(accountBalanceTransactionResponse[0].accountBalanceTransactionType);
        expect(accountBalanceTransaction.createdDateTime).toBe(accountBalanceTransactionResponse[0].createdDateTime);
        expect(accountBalanceTransaction.unit).toBe(accountBalanceTransactionResponse[0].unit);
    });

    test('call update accountBalanceTransaction', async () => {
        var accountBalanceTransactionResponse: AccountBalanceTransaction[] = await callUpdateAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId(
            updatedAccountBalanceTransaction, accountBalanceTransactionId || 1, env, domain);
        expect(updatedAccountBalanceTransaction.accountBalanceTransactionName).toBe(accountBalanceTransactionResponse[0].accountBalanceTransactionName);
        expect(updatedAccountBalanceTransaction.accountBalanceTransactionStatus).toBe(accountBalanceTransactionResponse[0].accountBalanceTransactionStatus);
        expect(updatedAccountBalanceTransaction.amount).toBe(accountBalanceTransactionResponse[0].amount);
        expect(updatedAccountBalanceTransaction.accountBalanceTransactionType).toBe(accountBalanceTransactionResponse[0].accountBalanceTransactionType);
        expect(updatedAccountBalanceTransaction.createdDateTime).toBe(accountBalanceTransactionResponse[0].createdDateTime);
        expect(updatedAccountBalanceTransaction.unit).toBe(accountBalanceTransactionResponse[0].unit);
    });

    test('call delete accountBalanceTransaction', async () => {
        var deleteAccountBalanceTransactionResponse: boolean = await callDeleteAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId(
            accountBalanceTransactionId || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceTransactionResponse);

        var deleteAccountBalanceTransactionStatusResponse: boolean = await callDeleteAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId(
            accountBalanceTransactionStatus.id || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceTransactionStatusResponse);

        var deleteAccountBalanceTransactionTypeResponse: boolean = await callDeleteAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId(
            accountBalanceTransactionType.id || 1, env, domain);
        expect(true).toBe(deleteAccountBalanceTransactionTypeResponse);

        var deleteUnitResponse: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unit.id || 1, env, domain);
        expect(true).toBe(deleteUnitResponse);
    });
});