import { callCreateExchangeRateRestEndpoints, callDeleteExchangeRateRestEndpointsByExchangeRateId, callReadExchangeRateRestEndpointsByExchangeRateId, callUpdateExchangeRateRestEndpoints } from "../../../../../endpoints/rest/exchangeRates/v1/ExchangeRateRestEndpoints";
import { callCreateUnitRestEndpoints, callDeleteUnitRestEndpointsByUnitId, callReadUnitRestEndpointsByUnitId, callReadUnitRestEndpointsByUnitName } from "../../../../../endpoints/rest/units/v1/UnitRestEndpoints";
import { ExchangeRate } from "../../../../../models/exchangeRates/ExchangeRate";
import { Unit } from "../../../../../models/units/v1/Unit";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('exchangeRate endpoint tests', () => {
    var exchangeRateId: number | undefined; 
    var unit1ToUnit2Ratio: number = 3.3;
    var updatedUnit1ToUnit2Ratio: number = 2.2;
    var unit1: Unit = new Unit();
    var unit2: Unit = new Unit();
    var exchangeRate: ExchangeRate = new ExchangeRate();

    var updatedExchangeRate: ExchangeRate = new ExchangeRate();

    unit1.unitName = "USD";
    unit2.unitName = "Yen";

    exchangeRate.unit1ToUnit2Ratio = unit1ToUnit2Ratio;

    updatedExchangeRate.unit1ToUnit2Ratio = updatedUnit1ToUnit2Ratio;

    var domain: string = "http://localhost:8080";

    test('call create exchangeRate', async () => {
        var unit1Response = await callCreateUnitRestEndpoints(unit1, env, domain);
        expect(unit1Response.unitName).toBe(unit1.unitName);
        var units = await callReadUnitRestEndpointsByUnitName(
                unit1.unitName || "", env, domain);
        if (units) {
            unit1 = units[0];
            exchangeRate.unit1 = unit1;
            updatedExchangeRate.unit1 = unit1;
        }

        var unit2Response = await callCreateUnitRestEndpoints(unit2, env, domain);
        expect(unit2Response.unitName).toBe(unit2.unitName);
        var units2 = await callReadUnitRestEndpointsByUnitName(
                unit2.unitName || "", env, domain);
        if (units2) {
            unit2 = units2[0];
            exchangeRate.unit2 = unit2;
            updatedExchangeRate.unit2 = unit2;
        }

        var exchangeRateResponse: ExchangeRate = await callCreateExchangeRateRestEndpoints(
            exchangeRate, env, domain);
        exchangeRateId = exchangeRateResponse.id;
        expect(exchangeRate.unit1).toBe(exchangeRateResponse.unit1);
        expect(exchangeRate.unit2).toBe(exchangeRateResponse.unit2);
        expect(exchangeRate.unit1ToUnit2Ratio).toBe(exchangeRateResponse.unit1ToUnit2Ratio);
    });

    test('call read exchangeRate', async () => {
        var exchangeRates: ExchangeRate[] | undefined = await callReadExchangeRateRestEndpointsByExchangeRateId(
            exchangeRateId || 1, env, domain) || [];
            expect(exchangeRate.unit1).toBe(exchangeRates[0].unit1);
            expect(exchangeRate.unit2).toBe(exchangeRates[0].unit2);
            expect(exchangeRate.unit1ToUnit2Ratio).toBe(exchangeRates[0].unit1ToUnit2Ratio);
    });

    test('call update exchangeRate', async () => {
        var exchangeRateResponse: ExchangeRate[] = await callUpdateExchangeRateRestEndpoints(
        updatedExchangeRate, env, domain);
        expect(updatedExchangeRate.unit1).toBe(exchangeRateResponse[0].unit1);
        expect(updatedExchangeRate.unit2).toBe(exchangeRateResponse[0].unit2);
        expect(updatedExchangeRate.unit1ToUnit2Ratio).toBe(exchangeRateResponse[0].unit1ToUnit2Ratio);
    });

    test('call delete exchangeRate', async () => {
        var deleteExchangeRateResponse: boolean = await callDeleteExchangeRateRestEndpointsByExchangeRateId(
            exchangeRateId || 1, env, domain);
        expect(true).toBe(deleteExchangeRateResponse);

        var deleteUnit1Response: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unit1.id || 1, env, domain);
        expect(true).toBe(deleteUnit1Response);

        var deleteUnit2Response: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unit2.id || 1, env, domain);
        expect(true).toBe(deleteUnit2Response);
    });
});