import { callCreateOrderTypeRestEndpoints, callDeleteOrderTypeRestEndpointsByOrderTypeId, callReadOrderTypeRestEndpointsByOrderTypeId, callUpdateOrderTypeRestEndpoints } from "../../../../../endpoints/rest/orderTypes/v1/OrderTypeRestEndpoints";
import { OrderType } from "../../../../../models/orderTypes/v1/OrderType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('orderType endpoint tests', () => {
    var orderTypeId: number | undefined; 
    var orderType: OrderType = new OrderType();
    orderType.orderTypeName = "1";

    var updatedOrderType: OrderType = new OrderType();
    updatedOrderType.orderTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create orderType', async () => {
        var orderTypeResponse: OrderType = await callCreateOrderTypeRestEndpoints(
            orderType, env, domain);
        orderTypeId = orderTypeResponse.id;
        expect(orderType.orderTypeName).toBe(orderTypeResponse.orderTypeName);
    });

    test('call read orderType', async () => {
        var orderTypes: OrderType[] | undefined = await callReadOrderTypeRestEndpointsByOrderTypeId(
            orderTypeId || 1, env, domain) || [];
        expect(orderTypes[0].orderTypeName).toBe(orderType.orderTypeName);
    });

    test('call update orderType', async () => {
        var orderTypeResponse: OrderType[] = await callUpdateOrderTypeRestEndpoints(
            updatedOrderType, env, domain);
        expect(updatedOrderType.orderTypeName).toBe(orderTypeResponse[0].orderTypeName);
    });

    test('call delete orderType', async () => {
        var deleteOrderTypeResponse: boolean = await callDeleteOrderTypeRestEndpointsByOrderTypeId(
            orderTypeId || 1, env, domain);
        expect(true).toBe(deleteOrderTypeResponse);
    });
});