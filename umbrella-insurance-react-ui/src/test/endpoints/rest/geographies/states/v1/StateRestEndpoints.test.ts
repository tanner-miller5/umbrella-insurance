import { callCreateStateRestEndpoints, callReadStateRestEndpointsByStateId, callDeleteStateRestEndpointsByStateId, callUpdateStateRestEndpoints } from "../../../../../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { State } from "../../../../../../models/geographies/states/v1/State";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('state endpoint tests', () => {
    var stateId: number | undefined; 
    var state: State = new State();
    state.stateName = "1";

    var updatedState: State = new State();
    updatedState.stateName = "2";

    var domain: string = "http://localhost:8080";

    test('call create state', async () => {
        var stateResponse: State = await callCreateStateRestEndpoints(
            state, env, domain);
        stateId = stateResponse.id;
        expect(state.stateName).toBe(stateResponse.stateName);
    });

    test('call read state', async () => {
        var states: State[] | undefined = await callReadStateRestEndpointsByStateId(
            stateId || 1, env, domain) || [];
        expect(states[0].stateName).toBe(state.stateName);
    });

    test('call update state', async () => {
        var stateResponse: State[] = await callUpdateStateRestEndpoints(
            updatedState, env, domain);
        expect(updatedState.stateName).toBe(stateResponse[0].stateName);
    });

    test('call delete state', async () => {
        var deleteStateResponse: boolean = await callDeleteStateRestEndpointsByStateId(
            stateId || 1, env, domain);
        expect(true).toBe(deleteStateResponse);
    });
});