import { callCreatePaymentTypeRestEndpoints, callReadPaymentTypeRestEndpointsByPaymentTypeId, callDeletePaymentTypeRestEndpointsByPaymentTypeId, callUpdatePaymentTypeRestEndpoints } from "../../../../../../endpoints/rest/users/paymentTypes/v1/PaymentTypeRestEndpoints";
import { PaymentType } from "../../../../../../models/users/paymentTypes/v1/PaymentType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('paymentType endpoint tests', () => {
    var paymentTypeId: number | undefined; 
    var paymentType: PaymentType = new PaymentType();
    paymentType.paymentTypeName = "1";

    var updatedPaymentType: PaymentType = new PaymentType();
    updatedPaymentType.paymentTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create paymentType', async () => {
        var paymentTypeResponse: PaymentType = await callCreatePaymentTypeRestEndpoints(
            paymentType, env, domain);
        paymentTypeId = paymentTypeResponse.id;
        expect(paymentType.paymentTypeName).toBe(paymentTypeResponse.paymentTypeName);
    });

    test('call read paymentType', async () => {
        var paymentTypes: PaymentType[] | undefined = await callReadPaymentTypeRestEndpointsByPaymentTypeId(
            paymentTypeId || 1, env, domain) || [];
        expect(paymentTypes[0].paymentTypeName).toBe(paymentType.paymentTypeName);
    });

    test('call update paymentType', async () => {
        var paymentTypeResponse: PaymentType[] = await callUpdatePaymentTypeRestEndpoints(
            updatedPaymentType, env, domain);
        expect(updatedPaymentType.paymentTypeName).toBe(paymentTypeResponse[0].paymentTypeName);
    });

    test('call delete paymentType', async () => {
        var deletePaymentTypeResponse: boolean = await callDeletePaymentTypeRestEndpointsByPaymentTypeId(
            paymentTypeId || 1, env, domain);
        expect(true).toBe(deletePaymentTypeResponse);
    });
});