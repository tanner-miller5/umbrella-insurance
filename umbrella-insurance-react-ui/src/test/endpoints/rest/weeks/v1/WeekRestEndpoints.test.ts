import { callCreateWeekRestEndpoints, callDeleteWeekRestEndpointsByWeekId, callReadWeekRestEndpointsByWeekId, callUpdateWeekRestEndpoints } from "../../../../../endpoints/rest/weeks/v1/WeekRestEndpoints";
import { Week } from "../../../../../models/weeks/v1/Week";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('week endpoint tests', () => {
    var weekId: number | undefined; 
    var week: Week = new Week();
    week.weekEndDate = "1222-11-11";
    week.weekStartDate = "1222-11-12";
    week.weekNumber = 1;
    week.weekTitle = "1";

    var updatedWeek: Week = new Week();
    updatedWeek.weekEndDate = "1222-11-13";
    updatedWeek.weekStartDate = "1222-11-14";
    updatedWeek.weekNumber = 1;
    updatedWeek.weekTitle = "1";
    var domain: string = "http://localhost:8080";

    test('call create week', async () => {
        var weekResponse: Week = await callCreateWeekRestEndpoints(
            week, env, domain);
        weekId = weekResponse.id;
        expect(week.weekEndDate).toBe(weekResponse.weekEndDate);
        expect(week.weekStartDate).toBe(weekResponse.weekStartDate);
        expect(week.weekNumber).toBe(weekResponse.weekNumber);
        expect(week.weekTitle).toBe(weekResponse.weekTitle);
    });

    test('call read week', async () => {
        var weekResponse: Week[] | undefined = await callReadWeekRestEndpointsByWeekId(
            weekId || 1, env, domain) || [];
        expect(week.weekEndDate).toBe(weekResponse[0].weekEndDate);
        expect(week.weekStartDate).toBe(weekResponse[0].weekStartDate);
        expect(week.weekNumber).toBe(weekResponse[0].weekNumber);
        expect(week.weekTitle).toBe(weekResponse[0].weekTitle);
    });

    test('call update week', async () => {
        var weekResponse: Week[] = await callUpdateWeekRestEndpoints(
            updatedWeek, env, domain);
        expect(updatedWeek.weekEndDate).toBe(weekResponse[0].weekEndDate);
        expect(updatedWeek.weekStartDate).toBe(weekResponse[0].weekStartDate);
        expect(updatedWeek.weekNumber).toBe(weekResponse[0].weekNumber);
        expect(updatedWeek.weekTitle).toBe(weekResponse[0].weekTitle);
    });

    test('call delete week', async () => {
        var deleteWeekResponse: boolean = await callDeleteWeekRestEndpointsByWeekId(
            weekId || 1, env, domain);
        expect(true).toBe(deleteWeekResponse);
    });
});