import { LoanPayment } from "../../../../../../models/users/loanPayments/v1/LoanPayment";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('loanPayment endpoint tests', () => {
    var loanPaymentId: number | undefined; 
    var loanPayment: LoanPayment = new LoanPayment();
    //loanPayment.loanPaymentName = "1";

    var updatedLoanPayment: LoanPayment = new LoanPayment();
    //updatedLoanPayment.loanPaymentName = "2";

    test('call create loanPayment', async () => {
        //var loanPaymentResponse: LoanPayment = await callCreateLoanPaymentRestEndpoints(
        //    loanPayment, env, domain);
        //loanPaymentId = loanPaymentResponse.loanPaymentId;
        //expect(loanPayment.loanPaymentName).toBe(loanPaymentResponse.loanPaymentName);
    });

    test('call read loanPayment', async () => {
        //var loanPayments: LoanPayment[] | undefined = await callReadLoanPaymentRestEndpointsByLoanPaymentId(
        //    loanPaymentId, env, domain) || [];
        //expect(loanPayments[0].loanPaymentName).toBe(loanPayment.loanPaymentName);
    });

    test('call update loanPayment', async () => {
        //var loanPaymentResponse: LoanPayment[] = await callUpdateLoanPaymentRestEndpointsByLoanPaymentId(
        //    updatedLoanPayment, loanPaymentId, env, domain);
        //expect(updatedLoanPayment.loanPaymentName).toBe(loanPaymentResponse[0].loanPaymentName);
    });

    test('call delete loanPayment', async () => {
        //var deleteLoanPaymentResponse: boolean = await callDeleteLoanPaymentRestEndpointsByLoanPaymentId(
        //    loanPaymentId, env, domain);
        //expect(true).toBe(deleteLoanPaymentResponse);
    });
});