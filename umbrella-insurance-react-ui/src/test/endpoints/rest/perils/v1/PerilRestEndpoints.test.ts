import { callCreatePerilRestEndpoints, callDeletePerilRestEndpointsByPerilId, callReadPerilRestEndpointsByPerilId, callUpdatePerilRestEndpoints } from "../../../../../endpoints/rest/perils/v1/PerilRestEndpoints";
import { Peril } from "../../../../../models/perils/v1/Peril";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('peril endpoint tests', () => {
    var perilId: number | undefined; 
    var peril: Peril = new Peril();
    peril.perilName = "1";

    var updatedPeril: Peril = new Peril();
    updatedPeril.perilName = "2";

    var domain: string = "http://localhost:8080";

    test('call create peril', async () => {
        var perilResponse: Peril = await callCreatePerilRestEndpoints(
            peril, env, domain);
        perilId = perilResponse.id;
        expect(peril.perilName).toBe(perilResponse.perilName);
    });

    test('call read peril', async () => {
        var perils: Peril[] | undefined = await callReadPerilRestEndpointsByPerilId(
            perilId || 1, env, domain) || [];
        expect(perils[0].perilName).toBe(peril.perilName);
    });

    test('call update peril', async () => {
        var perilResponse: Peril[] = await callUpdatePerilRestEndpoints(
            updatedPeril, env, domain);
        expect(updatedPeril.perilName).toBe(perilResponse[0].perilName);
    });

    test('call delete peril', async () => {
        var deletePerilResponse: boolean = await callDeletePerilRestEndpointsByPerilId(
            perilId || 1, env, domain);
        expect(true).toBe(deletePerilResponse);
    });
});