import { callCreateZipCodeRestEndpoints, callReadZipCodeRestEndpointsByZipCodeId, callDeleteZipCodeRestEndpointsByZipCodeId, callUpdateZipCodeRestEndpoints } from "../../../../../../endpoints/rest/geographies/zipCodes/v1/ZipCodeRestEndpoints";
import { ZipCode } from "../../../../../../models/geographies/zipCodes/v1/ZipCode";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('zipCode endpoint tests', () => {
    var zipCodeId: number | undefined; 
    var zipCode: ZipCode = new ZipCode();
    zipCode.zipCodeValue = "1";

    var updatedZipCode: ZipCode = new ZipCode();
    updatedZipCode.zipCodeValue = "2";

    var domain: string = "http://localhost:8080";

    test('call create zipCode', async () => {
        var zipCodeResponse: ZipCode = await callCreateZipCodeRestEndpoints(
            zipCode, env, domain);
        zipCodeId = zipCodeResponse.id;
        expect(zipCode.zipCodeValue).toBe(zipCodeResponse.zipCodeValue);
    });

    test('call read zipCode', async () => {
        var zipCodes: ZipCode[] | undefined = await callReadZipCodeRestEndpointsByZipCodeId(
            zipCodeId || 1, env, domain) || [];
        expect(zipCode.zipCodeValue).toBe(zipCodes[0].zipCodeValue);
    });

    test('call update zipCode', async () => {
        var zipCodeResponse: ZipCode[] = await callUpdateZipCodeRestEndpoints(
            updatedZipCode, env, domain);
        expect(updatedZipCode.zipCodeValue).toBe(zipCodeResponse[0].zipCodeValue);
    });

    test('call delete zipCode', async () => {
        var deleteZipCodeResponse: boolean = await callDeleteZipCodeRestEndpointsByZipCodeId(
            zipCodeId || 1, env, domain);
        expect(true).toBe(deleteZipCodeResponse);
    });
});