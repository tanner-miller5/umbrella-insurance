import { callCreatePendingBetStateRestEndpoints, callReadPendingBetStateRestEndpointsByPendingBetStateId, callDeletePendingBetStateRestEndpointsByPendingBetStateId, callUpdatePendingBetStateRestEndpoints } from "../../../../../../../../endpoints/rest/users/bets/pendingBets/pendingBetStates/v1/PendingBetStateRestEndpoints";
import { PendingBetState } from "../../../../../../../../models/users/bets/pendingBets/pendingBetStates/v1/PendingBetState";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('pendingBetState endpoint tests', () => {
    var pendingBetStateId: number | undefined; 
    var pendingBetState: PendingBetState = new PendingBetState();
    pendingBetState.pendingBetStateName = "1";

    var updatedPendingBetState: PendingBetState = new PendingBetState();
    updatedPendingBetState.pendingBetStateName = "2";

    var domain: string = "http://localhost:8080";

    test('call create pendingBetState', async () => {
        var pendingBetStateResponse: PendingBetState = await callCreatePendingBetStateRestEndpoints(
            pendingBetState, env, domain);
        pendingBetStateId = pendingBetStateResponse.id;
        expect(pendingBetState.pendingBetStateName).toBe(pendingBetStateResponse.pendingBetStateName);
    });

    test('call read pendingBetState', async () => {
        var pendingBetStates: PendingBetState[] | undefined = await callReadPendingBetStateRestEndpointsByPendingBetStateId(
            pendingBetStateId || 1, env, domain) || [];
        expect(pendingBetStates[0].pendingBetStateName).toBe(pendingBetState.pendingBetStateName);
    });

    test('call update pendingBetState', async () => {
        var pendingBetStateResponse: PendingBetState[] = await callUpdatePendingBetStateRestEndpoints(
            updatedPendingBetState, env, domain);
        expect(updatedPendingBetState.pendingBetStateName).toBe(pendingBetStateResponse[0].pendingBetStateName);
    });

    test('call delete pendingBetState', async () => {
        var deletePendingBetStateResponse: boolean = await callDeletePendingBetStateRestEndpointsByPendingBetStateId(
            pendingBetStateId || 1, env, domain);
        expect(true).toBe(deletePendingBetStateResponse);
    });
});