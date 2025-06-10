import { MatchedLoan } from "../../../../../../../models/users/loans/matchedLoans/v1/MatchedLoan";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('matchedLoan endpoint tests', () => {
    var matchedLoanId: number | undefined; 
    var matchedLoan: MatchedLoan = new MatchedLoan();
    //matchedLoan.matchedLoanName = "1";

    var updatedMatchedLoan: MatchedLoan = new MatchedLoan();
    //updatedMatchedLoan.matchedLoanName = "2";

    test('call create matchedLoan', async () => {
        //var matchedLoanResponse: MatchedLoan = await callCreateMatchedLoanRestEndpoints(
        //    matchedLoan, env, domain);
        //matchedLoanId = matchedLoanResponse.matchedLoanId;
        //expect(matchedLoan.matchedLoanName).toBe(matchedLoanResponse.matchedLoanName);
    });

    test('call read matchedLoan', async () => {
        //var matchedLoans: MatchedLoan[] | undefined = await callReadMatchedLoanRestEndpointsByMatchedLoanId(
        //    matchedLoanId, env, domain) || [];
        //expect(matchedLoans[0].matchedLoanName).toBe(matchedLoan.matchedLoanName);
    });

    test('call update matchedLoan', async () => {
        //var matchedLoanResponse: MatchedLoan[] = await callUpdateMatchedLoanRestEndpointsByMatchedLoanId(
        //    updatedMatchedLoan, matchedLoanId, env, domain);
        //expect(updatedMatchedLoan.matchedLoanName).toBe(matchedLoanResponse[0].matchedLoanName);
    });

    test('call delete matchedLoan', async () => {
        //var deleteMatchedLoanResponse: boolean = await callDeleteMatchedLoanRestEndpointsByMatchedLoanId(
        //    matchedLoanId, env, domain);
        //expect(true).toBe(deleteMatchedLoanResponse);
    });
});