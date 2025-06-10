import { callCreateBetTypeRestEndpoints, callReadBetTypeRestEndpointsByBetTypeId, callDeleteBetTypeRestEndpointsByBetTypeId, callUpdateBetTypeRestEndpoints } from "../../../../../../../endpoints/rest/users/bets/betTypes/v1/BetTypeRestEndpoints";
import { BetType } from "../../../../../../../models/users/bets/betTypes/v1/BetType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('betType endpoint tests', () => {
    var betTypeId: number | undefined; 
    var betType: BetType = new BetType();
    betType.betTypeName = "1";

    var updatedBetType: BetType = new BetType();
    updatedBetType.betTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create betType', async () => {
        var betTypeResponse: BetType = await callCreateBetTypeRestEndpoints(
            betType, env, domain);
        betTypeId = betTypeResponse.id;
        expect(betType.betTypeName).toBe(betTypeResponse.betTypeName);
    });

    test('call read betType', async () => {
        var betTypes: BetType[] | undefined = await callReadBetTypeRestEndpointsByBetTypeId(
            betTypeId || 1, env, domain) || [];
        expect(betTypes[0].betTypeName).toBe(betType.betTypeName);
    });

    test('call update betType', async () => {
        var betTypeResponse: BetType[] = await callUpdateBetTypeRestEndpoints(
            updatedBetType, env, domain);
        expect(updatedBetType.betTypeName).toBe(betTypeResponse[0].betTypeName);
    });

    test('call delete betType', async () => {
        var deleteBetTypeResponse: boolean = await callDeleteBetTypeRestEndpointsByBetTypeId(
            betTypeId || 1, env, domain);
        expect(true).toBe(deleteBetTypeResponse);
    });
});