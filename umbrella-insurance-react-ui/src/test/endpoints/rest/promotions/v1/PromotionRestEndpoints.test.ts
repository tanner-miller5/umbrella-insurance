import { callCreatePromotionRestEndpoints, callDeletePromotionRestEndpointsByPromotionId, callReadPromotionRestEndpointsByPromotionId, callUpdatePromotionRestEndpoints } from "../../../../../endpoints/rest/promotions/v1/PromotionRestEndpoints";
import { Promotion } from "../../../../../models/promotions/v1/Promotion";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('promotion endpoint tests', () => {
    var promotionId: number | undefined; 
    var promotion: Promotion = new Promotion();
    promotion.promotionName = "1";

    var updatedPromotion: Promotion = new Promotion();
    updatedPromotion.promotionName = "2";

    var domain: string = "http://localhost:8080";

    test('call create promotion', async () => {
        var promotionResponse: Promotion = await callCreatePromotionRestEndpoints(
            promotion, env, domain);
        promotionId = promotionResponse.id;
        expect(promotion.promotionName).toBe(promotionResponse.promotionName);
    });

    test('call read promotion', async () => {
        var promotions: Promotion[] | undefined = await callReadPromotionRestEndpointsByPromotionId(
            promotionId || 1, env, domain) || [];
        expect(promotions[0].promotionName).toBe(promotion.promotionName);
    });

    test('call update promotion', async () => {
        var promotionResponse: Promotion[] = await callUpdatePromotionRestEndpoints(
            updatedPromotion, env, domain);
        expect(updatedPromotion.promotionName).toBe(promotionResponse[0].promotionName);
    });

    test('call delete promotion', async () => {
        var deletePromotionResponse: boolean = await callDeletePromotionRestEndpointsByPromotionId(
            promotionId || 1, env, domain);
        expect(true).toBe(deletePromotionResponse);
    });
});