import { callCreateTeamTypeRestEndpoints, callReadTeamTypeRestEndpointsByTeamTypeId, callDeleteTeamTypeRestEndpointsByTeamTypeId, callUpdateTeamTypeRestEndpoints } from "../../../../../../endpoints/rest/teams/teamTypes/v1/TeamTypeRestEndpoints";
import { TeamType } from "../../../../../../models/teams/teamTypes/v1/TeamType";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('teamType endpoint tests', () => {
    var teamTypeId: number | undefined; 
    var teamType: TeamType = new TeamType();
    teamType.teamTypeName = "1";

    var updatedTeamType: TeamType = new TeamType();
    updatedTeamType.teamTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create teamType', async () => {
        var teamTypeResponse: TeamType = await callCreateTeamTypeRestEndpoints(
            teamType, env, domain);
        teamTypeId = teamTypeResponse.id;
        expect(teamType.teamTypeName).toBe(teamTypeResponse.teamTypeName);
    });

    test('call read teamType', async () => {
        var teamTypes: TeamType[] | undefined = await callReadTeamTypeRestEndpointsByTeamTypeId(
            teamTypeId || 1, env, domain) || [];
        expect(teamTypes[0].teamTypeName).toBe(teamType.teamTypeName);
    });

    test('call update teamType', async () => {
        var teamTypeResponse: TeamType[] = await callUpdateTeamTypeRestEndpoints(
            updatedTeamType, env, domain);
        expect(updatedTeamType.teamTypeName).toBe(teamTypeResponse[0].teamTypeName);
    });

    test('call delete teamType', async () => {
        var deleteTeamTypeResponse: boolean = await callDeleteTeamTypeRestEndpointsByTeamTypeId(
            teamTypeId || 1, env, domain);
        expect(true).toBe(deleteTeamTypeResponse);
    });
});