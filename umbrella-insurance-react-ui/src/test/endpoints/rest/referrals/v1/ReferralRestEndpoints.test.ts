import { callCreateReferralRestEndpoints, callDeleteReferralRestEndpointsByReferralId, callReadReferralRestEndpointsByReferralId, callUpdateReferralRestEndpoints } from "../../../../../endpoints/rest/referrals/v1/ReferralRestEndpoints";
import { Referral } from "../../../../../models/referrals/v1/Referral";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('referral endpoint tests', () => {
    var referralId: number | undefined; 
    var referral: Referral = new Referral();
    referral.referralName = "1";

    var updatedReferral: Referral = new Referral();
    updatedReferral.referralName = "2";

    var domain: string = "http://localhost:8080";

    test('call create referral', async () => {
        var referralResponse: Referral = await callCreateReferralRestEndpoints(
            referral, env, domain);
        referralId = referralResponse.id;
        expect(referral.referralName).toBe(referralResponse.referralName);
    });

    test('call read referral', async () => {
        var referrals: Referral[] | undefined = await callReadReferralRestEndpointsByReferralId(
            referralId || 1, env, domain) || [];
        expect(referrals[0].referralName).toBe(referral.referralName);
    });

    test('call update referral', async () => {
        var referralResponse: Referral[] = await callUpdateReferralRestEndpoints(
            updatedReferral, env, domain);
        expect(updatedReferral.referralName).toBe(referralResponse[0].referralName);
    });

    test('call delete referral', async () => {
        var deleteReferralResponse: boolean = await callDeleteReferralRestEndpointsByReferralId(
            referralId || 1, env, domain);
        expect(true).toBe(deleteReferralResponse);
    });
});