import { callCreatePendingPolicyTypeRestEndpoints, callReadPendingPolicyTypeRestEndpointsByPendingPolicyTypeId, callDeletePendingPolicyTypeRestEndpointsByPendingPolicyTypeId, callUpdatePendingPolicyTypeRestEndpoints } from "../../../../../../../../endpoints/rest/users/policies/pendingPolicies/pendingPoliciyTypes/v1/PendingPolicyTypeRestEndpoints";
import { PendingPolicyType } from "../../../../../../../../models/users/policies/pendingPolicies/pendingPolicyTypes/v1/PendingPolicyType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('pendingPolicyType endpoint tests', () => {
    var pendingPolicyTypeId: number | undefined; 
    var pendingPolicyType: PendingPolicyType = new PendingPolicyType();
    pendingPolicyType.pendingPolicyTypeName = "1";

    var updatedPendingPolicyType: PendingPolicyType = new PendingPolicyType();
    updatedPendingPolicyType.pendingPolicyTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create pendingPolicyType', async () => {
        var pendingPolicyTypeResponse: PendingPolicyType = await callCreatePendingPolicyTypeRestEndpoints(
            pendingPolicyType, env, domain);
        pendingPolicyTypeId = pendingPolicyTypeResponse.id;
        expect(pendingPolicyType.pendingPolicyTypeName).toBe(pendingPolicyTypeResponse.pendingPolicyTypeName);
    });

    test('call read pendingPolicyType', async () => {
        var pendingPolicyTypes: PendingPolicyType[] | undefined = await callReadPendingPolicyTypeRestEndpointsByPendingPolicyTypeId(
            pendingPolicyTypeId || 1, env, domain) || [];
        expect(pendingPolicyTypes[0].pendingPolicyTypeName).toBe(pendingPolicyType.pendingPolicyTypeName);
    });

    test('call update pendingPolicyType', async () => {
        var pendingPolicyTypeResponse: PendingPolicyType[] = await callUpdatePendingPolicyTypeRestEndpoints(
            updatedPendingPolicyType, env, domain);
        expect(updatedPendingPolicyType.pendingPolicyTypeName).toBe(pendingPolicyTypeResponse[0].pendingPolicyTypeName);
    });

    test('call delete pendingPolicyType', async () => {
        var deletePendingPolicyTypeResponse: boolean = await callDeletePendingPolicyTypeRestEndpointsByPendingPolicyTypeId(
            pendingPolicyTypeId || 1, env, domain);
        expect(true).toBe(deletePendingPolicyTypeResponse);
    });
});