import { callCreateStreetAddressRestEndpoints, callReadStreetAddressRestEndpointsByStreetAddressId, callDeleteStreetAddressRestEndpointsByStreetAddressId, callUpdateStreetAddressRestEndpoints } from "../../../../../../endpoints/rest/geographies/streetAddresses/v1/StreetAddressRestEndpoints";
import { StreetAddress } from "../../../../../../models/geographies/streetAddresses/v1/StreetAddress";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('streetAddress endpoint tests', () => {
    var streetAddressId: number | undefined; 
    var streetAddress: StreetAddress = new StreetAddress();
    streetAddress.streetAddressLine1 = "1";
    streetAddress.streetAddressLine2 = "2";

    var updatedStreetAddress: StreetAddress = new StreetAddress();
    updatedStreetAddress.streetAddressLine1 = "1";
    updatedStreetAddress.streetAddressLine2 = "2";

    var domain: string = "http://localhost:8080";

    test('call create streetAddress', async () => {
        var streetAddressResponse: StreetAddress = await callCreateStreetAddressRestEndpoints(
            streetAddress, env, domain);
        streetAddressId = streetAddressResponse.id;
        expect(streetAddress.streetAddressLine1).toBe(streetAddressResponse.streetAddressLine1);
        expect(streetAddress.streetAddressLine2).toBe(streetAddressResponse.streetAddressLine2);
    });

    test('call read streetAddress', async () => {
        var streetAddresss: StreetAddress[] | undefined = await callReadStreetAddressRestEndpointsByStreetAddressId(
            streetAddressId || 1, env, domain) || [];
            expect(streetAddress.streetAddressLine1).toBe(streetAddresss[0].streetAddressLine1);
            expect(streetAddress.streetAddressLine2).toBe(streetAddresss[0].streetAddressLine2);
    });

    test('call update streetAddress', async () => {
        var streetAddressResponse: StreetAddress[] = await callUpdateStreetAddressRestEndpoints(
            updatedStreetAddress, env, domain);
            expect(updatedStreetAddress.streetAddressLine1).toBe(streetAddressResponse[0].streetAddressLine1);
            expect(updatedStreetAddress.streetAddressLine2).toBe(streetAddressResponse[0].streetAddressLine2);
    });

    test('call delete streetAddress', async () => {
        var deleteStreetAddressResponse: boolean = await callDeleteStreetAddressRestEndpointsByStreetAddressId(
            streetAddressId || 1, env, domain);
        expect(true).toBe(deleteStreetAddressResponse);
    });
});