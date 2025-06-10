import { callCreateUnitRestEndpoints, callDeleteUnitRestEndpointsByUnitId, callReadUnitRestEndpointsByUnitId, callUpdateUnitRestEndpoints } from "../../../../../endpoints/rest/units/v1/UnitRestEndpoints";
import { Unit } from "../../../../../models/units/v1/Unit";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('unit endpoint tests', () => {
    var unitId: number | undefined; 
    var unit: Unit = new Unit();
    unit.unitName = "1";

    var updatedUnit: Unit = new Unit();
    updatedUnit.unitName = "2";

    var domain: string = "http://localhost:8080";

    test('call create unit', async () => {
        var unitResponse: Unit = await callCreateUnitRestEndpoints(
            unit, env, domain);
        unitId = unitResponse.id;
        expect(unit.unitName).toBe(unitResponse.unitName);
    });

    test('call read unit', async () => {
        var units: Unit[] | undefined = await callReadUnitRestEndpointsByUnitId(
            unitId || 1, env, domain) || [];
        expect(units[0].unitName).toBe(unit.unitName);
    });

    test('call update unit', async () => {
        var unitResponse: Unit[] = await callUpdateUnitRestEndpoints(
            updatedUnit, env, domain);
        expect(updatedUnit.unitName).toBe(unitResponse[0].unitName);
    });

    test('call delete unit', async () => {
        var deleteUnitResponse: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unitId || 1, env, domain);
        expect(true).toBe(deleteUnitResponse);
    });
});