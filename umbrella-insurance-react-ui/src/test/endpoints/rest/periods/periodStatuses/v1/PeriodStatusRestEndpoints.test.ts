import { callCreatePeriodStatusRestEndpoints, callReadPeriodStatusRestEndpointsByPeriodStatusId, callDeletePeriodStatusRestEndpointsByPeriodStatusId, callUpdatePeriodStatusRestEndpoints } from "../../../../../../endpoints/rest/periods/periodStatuses/v1/PeriodStatusRestEndpoints";
import { PeriodStatus } from "../../../../../../models/periods/periodStatuses/v1/PeriodStatus";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('periodStatus endpoint tests', () => {
    var periodStatusId: number | undefined; 
    var periodStatus: PeriodStatus = new PeriodStatus();
    periodStatus.periodStatusName = "1";

    var updatedPeriodStatus: PeriodStatus = new PeriodStatus();
    updatedPeriodStatus.periodStatusName = "2";

    var domain: string = "http://localhost:8080";

    test('call create periodStatus', async () => {
        var periodStatusResponse: PeriodStatus = await callCreatePeriodStatusRestEndpoints(
            periodStatus, env, domain);
        periodStatusId = periodStatusResponse.id;
        expect(periodStatus.periodStatusName).toBe(periodStatusResponse.periodStatusName);
    });

    test('call read periodStatus', async () => {
        var periodStatuss: PeriodStatus[] | undefined = await callReadPeriodStatusRestEndpointsByPeriodStatusId(
            periodStatusId || 1, env, domain) || [];
        expect(periodStatuss[0].periodStatusName).toBe(periodStatus.periodStatusName);
    });

    test('call update periodStatus', async () => {
        var periodStatusResponse: PeriodStatus[] = await callUpdatePeriodStatusRestEndpoints(
            updatedPeriodStatus, env, domain);
        expect(updatedPeriodStatus.periodStatusName).toBe(periodStatusResponse[0].periodStatusName);
    });

    test('call delete periodStatus', async () => {
        var deletePeriodStatusResponse: boolean = await callDeletePeriodStatusRestEndpointsByPeriodStatusId(
            periodStatusId || 1, env, domain);
        expect(true).toBe(deletePeriodStatusResponse);
    });
});