import { callCreateVerificationMethodRestEndpoints, callReadVerificationMethodRestEndpointsByVerificationMethodId, callDeleteVerificationMethodRestEndpointsByVerificationMethodId, callUpdateVerificationMethodRestEndpoints } from "../../../../../../../endpoints/rest/users/verificationCodes/verificationMethods/v1/VerificationMethodRestEndpoints";
import { VerificationMethod } from "../../../../../../../models/users/verificationCodes/verificationMethods/v1/VerificationMethod";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('verificationMethod endpoint tests', () => {
    var verificationMethodId: number | undefined; 
    var verificationMethod: VerificationMethod = new VerificationMethod();
    verificationMethod.verificationMethodName = "1";

    var updatedVerificationMethod: VerificationMethod = new VerificationMethod();
    updatedVerificationMethod.verificationMethodName = "2";

    var domain: string = "http://localhost:8080";

    test('call create verificationMethod', async () => {
        var verificationMethodResponse: VerificationMethod = await callCreateVerificationMethodRestEndpoints(
            verificationMethod, env, domain);
        verificationMethodId = verificationMethodResponse.id;
        expect(verificationMethod.verificationMethodName).toBe(verificationMethodResponse.verificationMethodName);
    });

    test('call read verificationMethod', async () => {
        var verificationMethods: VerificationMethod[] | undefined = await callReadVerificationMethodRestEndpointsByVerificationMethodId(
            verificationMethodId || 1, env, domain) || [];
        expect(verificationMethods[0].verificationMethodName).toBe(verificationMethod.verificationMethodName);
    });

    test('call update verificationMethod', async () => {
        var verificationMethodResponse: VerificationMethod[] = await callUpdateVerificationMethodRestEndpoints(
            updatedVerificationMethod, env, domain);
        expect(updatedVerificationMethod.verificationMethodName).toBe(verificationMethodResponse[0].verificationMethodName);
    });

    test('call delete verificationMethod', async () => {
        var deleteVerificationMethodResponse: boolean = await callDeleteVerificationMethodRestEndpointsByVerificationMethodId(
            verificationMethodId || 1, env, domain);
        expect(true).toBe(deleteVerificationMethodResponse);
    });
});