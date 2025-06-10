import { callReadFaqRestEndpointsByFaqId } from "../../../../../endpoints/rest/faqs/v1/FaqRestEndpoints";
import { Faq } from "../../../../../models/faqs/v1/Faq";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var faqId: number | undefined; 
var env: string = "TEST";
describe.skip('faq endpoint tests', () => {
    var faq: Faq = new Faq();
    faq.question = "1";
    faq.answer = "1";
    faq.faqName = "1";
    faq.createdDateTime = "2022-11-11T11:11:11Z";

    var updatedFaq: Faq = new Faq();
    updatedFaq.question = "2";
    updatedFaq.answer = "2";
    updatedFaq.faqName = "2";
    updatedFaq.createdDateTime = "2022-11-12T11:11:11Z";

    var domain: string = "http://localhost:8080";

    //fix me session code
    /*
    test('call create faq', async () => {
        var faqResponse: Faq = await callCreateFaqRestEndpoints(faq, env, domain, sessionCode);
        faqId = faqResponse.faqId;
        expect(faq.question).toBe(faqResponse.question);
        expect(faq.answer).toBe(faqResponse.answer);
        expect(faq.faqName).toBe(faqResponse.faqName);
        expect(faq.createdDateTime).toBe(faqResponse.createdDateTime);
    });
    */

    test('call read faq', async () => {
        var faqs: Faq[] | undefined = await callReadFaqRestEndpointsByFaqId(faqId || 1, env, domain) || [];
        expect(faqs[0].question).toBe(faq.question);
        expect(faqs[0].answer).toBe(faq.answer);
        expect(faqs[0].faqName).toBe(faq.faqName);
        expect(faqs[0].createdDateTime).toBe(faq.createdDateTime);
    });

    //fix me session code
    /*
    test('call update faq', async () => {
        var faqResponse: Faq[] = await callUpdateFaqRestEndpointsByFaqId(
            updatedFaq, faqId || 1, env, domain, sessionCode);
        expect(updatedFaq.question).toBe(faqResponse[0].question);
        expect(updatedFaq.answer).toBe(faqResponse[0].answer);
        expect(updatedFaq.faqName).toBe(faqResponse[0].faqName);
        expect(updatedFaq.createdDateTime).toBe(faqResponse[0].createdDateTime);
    });

    test('call delete faq', async () => {
        var deleteFaqResponse: boolean = await callDeleteFaqRestEndpointsByFaqId(faqId || 1, 
        env, domain, sessionCode);
        expect(true).toBe(deleteFaqResponse);
    });
    */

});