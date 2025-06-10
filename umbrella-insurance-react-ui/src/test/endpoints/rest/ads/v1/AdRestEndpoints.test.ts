import { callCreateAdRestEndpoints, callDeleteAdRestEndpointsByAdId, callReadAdRestEndpointsByAdId, callUpdateAdRestEndpoints } from "../../../../../endpoints/rest/ads/v1/AdRestEndpoints";
import { Ad } from "../../../../../models/ads/v1/Ad";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var adId: number | undefined; 
var env: string = "TEST";
describe.skip('ad endpoint tests', () => {
    var ad: Ad = new Ad();
    ad.adData = "1";
    ad.adName = "1";
    ad.createdDateTime = "2022-11-11T11:11:11Z";

    var updatedAd: Ad = new Ad();
    updatedAd.adData = "2";
    updatedAd.adName = "2";
    updatedAd.createdDateTime = "2022-11-12T11:11:11Z";

    var domain: string = "http://localhost:8080";

    test('call create ad', async () => {
        var adResponse: Ad = await callCreateAdRestEndpoints(ad, env, domain);
        adId = adResponse.id;
        expect(ad.adData).toBe(adResponse.adData);
        expect(ad.adName).toBe(adResponse.adName);
        expect(ad.createdDateTime).toBe(adResponse.createdDateTime);
    });

    test('call read ad', async () => {
        var ads: Ad[] | undefined = await callReadAdRestEndpointsByAdId(adId || 1, env, domain) || [];
        expect(ads[0].adData).toBe(ad.adData);
        expect(ads[0].adName).toBe(ad.adName);
        expect(ads[0].createdDateTime).toBe(ad.createdDateTime);
    });

    test('call update ad', async () => {
        var adResponse: Ad[] = await callUpdateAdRestEndpoints(updatedAd, env, domain);
        expect(updatedAd.adData).toBe(adResponse[0].adData);
        expect(updatedAd.adName).toBe(adResponse[0].adName);
        expect(updatedAd.createdDateTime).toBe(adResponse[0].createdDateTime);
    });

    test('call delete ad', async () => {
        var adName: string = "";
        var deleteAdResponse: boolean = await callDeleteAdRestEndpointsByAdId(adId || 1, env, domain);
        expect(true).toBe(deleteAdResponse);
    });
});