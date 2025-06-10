import { callCreateHolidayRestEndpoints, callReadHolidayRestEndpointsByHolidayId, callDeleteHolidayRestEndpointsByHolidayId, callUpdateHolidayRestEndpoints } from "../../../../../../endpoints/rest/dates/holidays/v1/HolidayRestEndpoints";
import { Holiday } from "../../../../../../models/dates/holidays/v1/Holiday";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('holiday endpoint tests', () => {
    var holidayId: number | undefined; 
    var holiday: Holiday = new Holiday();
    holiday.holidayName = "1";
    holiday.holidayDate = "2024-06-23";

    var updatedHoliday: Holiday = new Holiday();
    updatedHoliday.holidayName = "2";
    updatedHoliday.holidayDate = "2024-06-24";

    var domain: string = "http://localhost:8080";

    test('call create holiday', async () => {
        var holidayResponse: Holiday = await callCreateHolidayRestEndpoints(
            holiday, env, domain);
        holidayId = holidayResponse.id;
        expect(holiday.holidayName).toBe(holidayResponse.holidayName);
        expect(holiday.holidayDate).toBe(holidayResponse.holidayDate);
    });

    test('call read holiday', async () => {
        var holidays: Holiday[] | undefined = await callReadHolidayRestEndpointsByHolidayId(
            holidayId || 1, env, domain) || [];
        expect(holidays[0].holidayName).toBe(holiday.holidayName);
        expect(holidays[0].holidayDate).toBe(holiday.holidayDate);
    });

    test('call update holiday', async () => {
        var holidayResponse: Holiday[] = await callUpdateHolidayRestEndpoints(
            updatedHoliday, env, domain);
        expect(updatedHoliday.holidayName).toBe(holidayResponse[0].holidayName);
        expect(updatedHoliday.holidayDate).toBe(holidayResponse[0].holidayDate);
    });

    test('call delete holiday', async () => {
        var deleteHolidayResponse: boolean = await callDeleteHolidayRestEndpointsByHolidayId(
            holidayId || 1, env, domain);
        expect(true).toBe(deleteHolidayResponse);
    });
});