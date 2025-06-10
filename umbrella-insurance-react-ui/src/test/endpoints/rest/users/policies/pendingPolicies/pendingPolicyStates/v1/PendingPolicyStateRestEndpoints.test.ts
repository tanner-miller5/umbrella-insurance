import { callCreatePendingPolicyStateRestEndpoints, callReadPendingPolicyStateRestEndpointsByPendingPolicyStateId, callDeletePendingPolicyStateRestEndpointsByPendingPolicyStateId, callUpdatePendingPolicyStateRestEndpoints } from "../../../../../../../../endpoints/rest/users/policies/pendingPolicies/pendingPolicyStates/v1/PendingPolicyStateRestEndpoints";
import { PendingPolicyState } from "../../../../../../../../models/users/policies/pendingPolicies/pendingPolicyStates/v1/PendingPolicyState";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('pendingPolicyState endpoint tests', () => {
    var pendingPolicyStateId: number | undefined; 
    var pendingPolicyState: PendingPolicyState = new PendingPolicyState();
    pendingPolicyState.pendingPolicyStateName = "1";

    var updatedPendingPolicyState: PendingPolicyState = new PendingPolicyState();
    updatedPendingPolicyState.pendingPolicyStateName = "2";

    var domain: string = "http://localhost:8080";

    test('call create pendingPolicyState', async () => {
        var pendingPolicyStateResponse: PendingPolicyState = await callCreatePendingPolicyStateRestEndpoints(
            pendingPolicyState, env, domain);
        pendingPolicyStateId = pendingPolicyStateResponse.id;
        expect(pendingPolicyState.pendingPolicyStateName).toBe(pendingPolicyStateResponse.pendingPolicyStateName);
    });

    test('call read pendingPolicyState', async () => {
        var pendingPolicyStates: PendingPolicyState[] | undefined = await callReadPendingPolicyStateRestEndpointsByPendingPolicyStateId(
            pendingPolicyStateId || 1, env, domain) || [];
        expect(pendingPolicyStates[0].pendingPolicyStateName).toBe(pendingPolicyState.pendingPolicyStateName);
    });

    test('call update pendingPolicyState', async () => {
        var pendingPolicyStateResponse: PendingPolicyState[] = await callUpdatePendingPolicyStateRestEndpoints(
            updatedPendingPolicyState, env, domain);
        expect(updatedPendingPolicyState.pendingPolicyStateName).toBe(pendingPolicyStateResponse[0].pendingPolicyStateName);
    });

    test('call delete pendingPolicyState', async () => {
        var deletePendingPolicyStateResponse: boolean = await callDeletePendingPolicyStateRestEndpointsByPendingPolicyStateId(
            pendingPolicyStateId || 1, env, domain);
        expect(true).toBe(deletePendingPolicyStateResponse);
    });
});