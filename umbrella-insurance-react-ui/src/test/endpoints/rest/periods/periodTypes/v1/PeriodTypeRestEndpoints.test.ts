import { callCreatePeriodTypeRestEndpoints, callReadPeriodTypeRestEndpointsByPeriodTypeId, callDeletePeriodTypeRestEndpointsByPeriodTypeId, callUpdatePeriodTypeRestEndpoints } from "../../../../../../endpoints/rest/periods/periodTypes/v1/PeriodTypeRestEndpoints";
import { PeriodType } from "../../../../../../models/periods/periodTypes/v1/PeriodType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('periodType endpoint tests', () => {
    var periodTypeId: number | undefined; 
    var periodType: PeriodType = new PeriodType();
    periodType.periodTypeName = "1";

    var updatedPeriodType: PeriodType = new PeriodType();
    updatedPeriodType.periodTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create periodType', async () => {
        var periodTypeResponse: PeriodType = await callCreatePeriodTypeRestEndpoints(
            periodType, env, domain);
        periodTypeId = periodTypeResponse.id;
        expect(periodType.periodTypeName).toBe(periodTypeResponse.periodTypeName);
    });

    test('call read periodType', async () => {
        var periodTypes: PeriodType[] | undefined = await callReadPeriodTypeRestEndpointsByPeriodTypeId(
            periodTypeId || 1, env, domain) || [];
        expect(periodTypes[0].periodTypeName).toBe(periodType.periodTypeName);
    });

    test('call update periodType', async () => {
        var periodTypeResponse: PeriodType[] = await callUpdatePeriodTypeRestEndpoints(
            updatedPeriodType, env, domain);
        expect(updatedPeriodType.periodTypeName).toBe(periodTypeResponse[0].periodTypeName);
    });

    test('call delete periodType', async () => {
        var deletePeriodTypeResponse: boolean = await callDeletePeriodTypeRestEndpointsByPeriodTypeId(
            periodTypeId || 1, env, domain);
        expect(true).toBe(deletePeriodTypeResponse);
    });
});