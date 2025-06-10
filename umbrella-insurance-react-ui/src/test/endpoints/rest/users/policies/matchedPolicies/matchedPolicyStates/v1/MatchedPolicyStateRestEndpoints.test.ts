import { callCreateMatchedPolicyStateRestEndpoints, callReadMatchedPolicyStateRestEndpointsByMatchedPolicyStateId, callDeleteMatchedPolicyStateRestEndpointsByMatchedPolicyStateId, callUpdateMatchedPolicyStateRestEndpoints } from "../../../../../../../../endpoints/rest/users/policies/matchedPolicies/matchedPolicyStates/v1/MatchedPolicyStateRestEndpoints";
import { MatchedPolicyState } from "../../../../../../../../models/users/policies/matchedPolicies/matchedPolicyStates/v1/MatchedPolicyState";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('matchedPolicyState endpoint tests', () => {
    var matchedPolicyStateId: number | undefined; 
    var matchedPolicyState: MatchedPolicyState = new MatchedPolicyState();
    matchedPolicyState.matchedPolicyStateName = "1";

    var updatedMatchedPolicyState: MatchedPolicyState = new MatchedPolicyState();
    updatedMatchedPolicyState.matchedPolicyStateName = "2";

    var domain: string = "http://localhost:8080";

    test('call create matchedPolicyState', async () => {
        var matchedPolicyStateResponse: MatchedPolicyState = await callCreateMatchedPolicyStateRestEndpoints(
            matchedPolicyState, env, domain);
        matchedPolicyStateId = matchedPolicyStateResponse.id;
        expect(matchedPolicyState.matchedPolicyStateName).toBe(matchedPolicyStateResponse.matchedPolicyStateName);
    });

    test('call read matchedPolicyState', async () => {
        var matchedPolicyStates: MatchedPolicyState[] | undefined = await callReadMatchedPolicyStateRestEndpointsByMatchedPolicyStateId(
            matchedPolicyStateId || 1, env, domain) || [];
        expect(matchedPolicyStates[0].matchedPolicyStateName).toBe(matchedPolicyState.matchedPolicyStateName);
    });

    test('call update matchedPolicyState', async () => {
        var matchedPolicyStateResponse: MatchedPolicyState[] = await callUpdateMatchedPolicyStateRestEndpoints(
            updatedMatchedPolicyState, env, domain);
        expect(updatedMatchedPolicyState.matchedPolicyStateName).toBe(matchedPolicyStateResponse[0].matchedPolicyStateName);
    });

    test('call delete matchedPolicyState', async () => {
        var deleteMatchedPolicyStateResponse: boolean = await callDeleteMatchedPolicyStateRestEndpointsByMatchedPolicyStateId(
            matchedPolicyStateId || 1, env, domain);
        expect(true).toBe(deleteMatchedPolicyStateResponse);
    });
});