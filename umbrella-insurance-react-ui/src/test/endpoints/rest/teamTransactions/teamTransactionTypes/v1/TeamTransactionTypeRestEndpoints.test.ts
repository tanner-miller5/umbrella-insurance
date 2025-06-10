import { callCreateTeamTransactionTypeRestEndpoints, callReadTeamTransactionTypeRestEndpointsByTeamTransactionTypeId, callDeleteTeamTransactionTypeRestEndpointsByTeamTransactionTypeId, callUpdateTeamTransactionTypeRestEndpoints } from "../../../../../../endpoints/rest/teamTransactions/teamTransactionTypes/v1/TeamTransactionTypeRestEndpoints";
import { TeamTransactionType } from "../../../../../../models/teamTransactions/teamTransactionTypes/v1/TeamTransactionType";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('teamTransactionType endpoint tests', () => {
    var teamTransactionTypeId: number | undefined; 
    var teamTransactionType: TeamTransactionType = new TeamTransactionType();
    teamTransactionType.teamTransactionTypeName = "1";

    var updatedTeamTransactionType: TeamTransactionType = new TeamTransactionType();
    updatedTeamTransactionType.teamTransactionTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create teamTransactionType', async () => {
        var teamTransactionTypeResponse: TeamTransactionType = await callCreateTeamTransactionTypeRestEndpoints(
            teamTransactionType, env, domain);
        teamTransactionTypeId = teamTransactionTypeResponse.id;
        expect(teamTransactionType.teamTransactionTypeName).toBe(teamTransactionTypeResponse.teamTransactionTypeName);
    });

    test('call read teamTransactionType', async () => {
        var teamTransactionTypes: TeamTransactionType[] | undefined = await callReadTeamTransactionTypeRestEndpointsByTeamTransactionTypeId(
            teamTransactionTypeId || 1, env, domain) || [];
        expect(teamTransactionTypes[0].teamTransactionTypeName).toBe(teamTransactionType.teamTransactionTypeName);
    });

    test('call update teamTransactionType', async () => {
        var teamTransactionTypeResponse: TeamTransactionType[] = await callUpdateTeamTransactionTypeRestEndpoints(
            updatedTeamTransactionType, env, domain);
        expect(updatedTeamTransactionType.teamTransactionTypeName).toBe(teamTransactionTypeResponse[0].teamTransactionTypeName);
    });

    test('call delete teamTransactionType', async () => {
        var deleteTeamTransactionTypeResponse: boolean = await callDeleteTeamTransactionTypeRestEndpointsByTeamTransactionTypeId(
            teamTransactionTypeId || 1, env, domain);
        expect(true).toBe(deleteTeamTransactionTypeResponse);
    });
});