import { callCreateUnitRestEndpoints, callDeleteUnitRestEndpointsByUnitId } from "../../../../../../endpoints/rest/units/v1/UnitRestEndpoints";
import { callCreateFeeRestEndpoints, callReadFeeRestEndpointsByFeeId, callDeleteFeeRestEndpointsByFeeId, callUpdateFeeRestEndpoints } from "../../../../../../endpoints/rest/users/fees/v1/FeeRestEndpoints";
import { Unit } from "../../../../../../models/units/v1/Unit";
import { Fee } from "../../../../../../models/users/fees/v1/Fee";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('fee endpoint tests', () => {
    var feeId: number | undefined; 

    var unit: Unit = new Unit();
    unit.unitName = "1";

    var fee: Fee = new Fee();
    fee.feeName = "1";
    fee.feePercent = 1;
    fee.fixedFee = 1;

    var updatedFee: Fee = new Fee();
    updatedFee.feeName = "2";
    updatedFee.feePercent = 1;
    updatedFee.fixedFee = 1;

    var domain: string = "http://localhost:8080";

    test('call create fee', async () => {
        var unitResponse: Unit = await callCreateUnitRestEndpoints(
            unit, env, domain);
        unit.id = unitResponse.id;
        fee.unit = unitResponse;
        updatedFee.unit = unitResponse;
        expect(unit.unitName).toBe(unitResponse.unitName);
        
        var feeResponse: Fee = await callCreateFeeRestEndpoints(
            fee, env, domain);
        feeId = feeResponse.id;
        expect(fee.feeName).toBe(feeResponse.feeName);
        expect(fee.feePercent).toBe(feeResponse.feePercent);
        expect(fee.fixedFee).toBe(feeResponse.fixedFee);
        expect(fee.unit).toBe(feeResponse.unit);
    });

    test('call read fee', async () => {
        var fees: Fee[] | undefined = await callReadFeeRestEndpointsByFeeId(
            feeId || 1, env, domain) || [];
        expect(fees[0].feeName).toBe(fee.feeName);
        expect(fees[0].feePercent).toBe(fee.feePercent);
        expect(fees[0].fixedFee).toBe(fee.fixedFee);
        expect(fees[0].unit).toBe(fee.unit);
    });

    test('call update fee', async () => {
        var feeResponse: Fee[] = await callUpdateFeeRestEndpoints(
            updatedFee, env, domain);
        expect(updatedFee.feeName).toBe(feeResponse[0].feeName);
        expect(updatedFee.feePercent).toBe(feeResponse[0].feePercent);
        expect(updatedFee.fixedFee).toBe(feeResponse[0].fixedFee);
        expect(updatedFee.unit).toBe(feeResponse[0].unit);
    });

    test('call delete fee', async () => {
        var deleteFeeResponse: boolean = await callDeleteFeeRestEndpointsByFeeId(
            feeId || 1, env, domain);
        expect(true).toBe(deleteFeeResponse);

        var deleteUnitResponse: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unit.id || 1, env, domain);
        expect(true).toBe(deleteUnitResponse);
    });
});