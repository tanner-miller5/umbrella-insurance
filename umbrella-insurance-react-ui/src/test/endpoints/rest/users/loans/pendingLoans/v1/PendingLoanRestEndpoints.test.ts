import { PendingLoan } from "../../../../../../../models/users/loans/pendingLoans/v1/PendingLoan";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('pendingLoan endpoint tests', () => {
    var pendingLoanId: number | undefined; 
    var pendingLoan: PendingLoan = new PendingLoan();
    //pendingLoan.pendingLoanName = "1";

    var updatedPendingLoan: PendingLoan = new PendingLoan();
    //updatedPendingLoan.pendingLoanName = "2";

    test('call create pendingLoan', async () => {
        //var pendingLoanResponse: PendingLoan = await callCreatePendingLoanRestEndpoints(
        //    pendingLoan, env, domain);
        //pendingLoanId = pendingLoanResponse.pendingLoanId;
        //expect(pendingLoan.pendingLoanName).toBe(pendingLoanResponse.pendingLoanName);
    });

    test('call read pendingLoan', async () => {
        //var pendingLoans: PendingLoan[] | undefined = await callReadPendingLoanRestEndpointsByPendingLoanId(
        //    pendingLoanId, env, domain) || [];
        //expect(pendingLoans[0].pendingLoanName).toBe(pendingLoan.pendingLoanName);
    });

    test('call update pendingLoan', async () => {
        //var pendingLoanResponse: PendingLoan[] = await callUpdatePendingLoanRestEndpointsByPendingLoanId(
        //    updatedPendingLoan, pendingLoanId, env, domain);
        //expect(updatedPendingLoan.pendingLoanName).toBe(pendingLoanResponse[0].pendingLoanName);
    });

    test('call delete pendingLoan', async () => {
        //var deletePendingLoanResponse: boolean = await callDeletePendingLoanRestEndpointsByPendingLoanId(
        //    pendingLoanId, env, domain);
        //expect(true).toBe(deletePendingLoanResponse);
    });
});