import { callCreateCharityRestEndpoints, callDeleteCharityRestEndpointsByCharityId, callReadCharityRestEndpointsByCharityId, callUpdateCharityRestEndpoints } from "../../../../../endpoints/rest/charities/v1/CharityRestEndpoints";
import { Charity } from "../../../../../models/charities/v1/Charity";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var charityId: number | undefined; 
var env: string = "TEST";
describe.skip('charity endpoint tests', () => {
    var charity: Charity = new Charity();
    charity.charityName = "1";

    var updatedCharity: Charity = new Charity();
    updatedCharity.charityName = "2";

    var domain: string = "http://localhost:8080";

    test('call create charity', async () => {
        var charityResponse: Charity = await callCreateCharityRestEndpoints(charity, env, domain);
        charityId = charityResponse.id;
        expect(charity.charityName).toBe(charityResponse.charityName);
    });

    test('call read charity', async () => {
        var charitys: Charity[] | undefined = await callReadCharityRestEndpointsByCharityId(charityId || 1, env, domain) || [];
        expect(charitys[0].charityName).toBe(charity.charityName);
    });

    test('call update charity', async () => {
        var charityResponse: Charity[] = await callUpdateCharityRestEndpoints(
            updatedCharity, env, domain);
        expect(updatedCharity.charityName).toBe(charityResponse[0].charityName);
    });

    test('call delete charity', async () => {
        var charityName: string = "";
        var deleteCharityResponse: boolean = await callDeleteCharityRestEndpointsByCharityId(charityId || 1, env, domain);
        expect(true).toBe(deleteCharityResponse);
    });
});