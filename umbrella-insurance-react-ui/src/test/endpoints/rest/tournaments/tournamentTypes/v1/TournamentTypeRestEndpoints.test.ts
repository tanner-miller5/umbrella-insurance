import { callCreateTournamentTypeRestEndpoints, callReadTournamentTypeRestEndpointsByTournamentTypeId, callDeleteTournamentTypeRestEndpointsByTournamentTypeId, callUpdateTournamentTypeRestEndpoints } from "../../../../../../endpoints/rest/tournaments/tournamentTypes/v1/TournamentTypeRestEndpoints";
import { TournamentType } from "../../../../../../models/tournaments/tournamentTypes/v1/TournamentType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('tournamentType endpoint tests', () => {
    var tournamentTypeId: number | undefined; 
    var tournamentType: TournamentType = new TournamentType();
    tournamentType.tournamentTypeName = "1";

    var updatedTournamentType: TournamentType = new TournamentType();
    updatedTournamentType.tournamentTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create tournamentType', async () => {
        var tournamentTypeResponse: TournamentType = await callCreateTournamentTypeRestEndpoints(
            tournamentType, env, domain);
        tournamentTypeId = tournamentTypeResponse.id;
        expect(tournamentType.tournamentTypeName).toBe(tournamentTypeResponse.tournamentTypeName);
    });

    test('call read tournamentType', async () => {
        var tournamentTypes: TournamentType[] | undefined = await callReadTournamentTypeRestEndpointsByTournamentTypeId(
            tournamentTypeId || 1, env, domain) || [];
        expect(tournamentTypes[0].tournamentTypeName).toBe(tournamentType.tournamentTypeName);
    });

    test('call update tournamentType', async () => {
        var tournamentTypeResponse: TournamentType[] = await callUpdateTournamentTypeRestEndpoints(
            updatedTournamentType, env, domain);
        expect(updatedTournamentType.tournamentTypeName).toBe(tournamentTypeResponse[0].tournamentTypeName);
    });

    test('call delete tournamentType', async () => {
        var deleteTournamentTypeResponse: boolean = await callDeleteTournamentTypeRestEndpointsByTournamentTypeId(
            tournamentTypeId || 1, env, domain);
        expect(true).toBe(deleteTournamentTypeResponse);
    });
});