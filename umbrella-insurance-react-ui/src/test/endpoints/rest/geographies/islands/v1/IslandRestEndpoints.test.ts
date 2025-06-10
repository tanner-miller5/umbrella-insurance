import { callCreateIslandRestEndpoints, callReadIslandRestEndpointsByIslandId, callDeleteIslandRestEndpointsByIslandId, callUpdateIslandRestEndpoints } from "../../../../../../endpoints/rest/geographies/islands/v1/IslandRestEndpoints";
import { Island } from "../../../../../../models/geographies/islands/v1/Island";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('island endpoint tests', () => {
    var islandId: number | undefined; 
    var island: Island = new Island();
    island.islandName = "1";

    var updatedIsland: Island = new Island();
    updatedIsland.islandName = "2";

    var domain: string = "http://localhost:8080";

    test('call create island', async () => {
        var islandResponse: Island = await callCreateIslandRestEndpoints(
            island, env, domain);
        islandId = islandResponse.id;
        expect(island.islandName).toBe(islandResponse.islandName);
    });

    test('call read island', async () => {
        var islands: Island[] | undefined = await callReadIslandRestEndpointsByIslandId(
            islandId || 1, env, domain) || [];
        expect(islands[0].islandName).toBe(island.islandName);
    });

    test('call update island', async () => {
        var islandResponse: Island[] = await callUpdateIslandRestEndpoints(
            updatedIsland, env, domain);
        expect(updatedIsland.islandName).toBe(islandResponse[0].islandName);
    });

    test('call delete island', async () => {
        var deleteIslandResponse: boolean = await callDeleteIslandRestEndpointsByIslandId(
            islandId || 1, env, domain);
        expect(true).toBe(deleteIslandResponse);
    });
});