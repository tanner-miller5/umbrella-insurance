import { callCreateMatchedBetStateRestEndpoints, callReadMatchedBetStateRestEndpointsByMatchedBetStateId, callDeleteMatchedBetStateRestEndpointsByMatchedBetStateId, callUpdateMatchedBetStateRestEndpoints } from "../../../../../../../../endpoints/rest/users/bets/matchedBets/matchedBetStates/v1/MatchedBetStateRestEndpoints";
import { MatchedBetState } from "../../../../../../../../models/users/bets/matchedBets/matchedBetStates/v1/MatchedBetState";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('matchedBetState endpoint tests', () => {
    var matchedBetStateId: number | undefined; 
    var matchedBetState: MatchedBetState = new MatchedBetState();
    matchedBetState.matchedBetStateName = "1";

    var updatedMatchedBetState: MatchedBetState = new MatchedBetState();
    updatedMatchedBetState.matchedBetStateName = "2";

    var domain: string = "http://localhost:8080";

    test('call create matchedBetState', async () => {
        var matchedBetStateResponse: MatchedBetState = await callCreateMatchedBetStateRestEndpoints(
            matchedBetState, env, domain);
        matchedBetStateId = matchedBetStateResponse.id;
        expect(matchedBetState.matchedBetStateName).toBe(matchedBetStateResponse.matchedBetStateName);
    });

    test('call read matchedBetState', async () => {
        var matchedBetStates: MatchedBetState[] | undefined = await callReadMatchedBetStateRestEndpointsByMatchedBetStateId(
            matchedBetStateId || 1, env, domain) || [];
        expect(matchedBetStates[0].matchedBetStateName).toBe(matchedBetState.matchedBetStateName);
    });

    test('call update matchedBetState', async () => {
        var matchedBetStateResponse: MatchedBetState[] = await callUpdateMatchedBetStateRestEndpoints(
            updatedMatchedBetState, env, domain);
        expect(updatedMatchedBetState.matchedBetStateName).toBe(matchedBetStateResponse[0].matchedBetStateName);
    });

    test('call delete matchedBetState', async () => {
        var deleteMatchedBetStateResponse: boolean = await callDeleteMatchedBetStateRestEndpointsByMatchedBetStateId(
            matchedBetStateId || 1, env, domain);
        expect(true).toBe(deleteMatchedBetStateResponse);
    });
});