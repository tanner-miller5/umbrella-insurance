import { callCreateTeamMemberTypeRestEndpoints, callDeleteTeamMemberTypeRestEndpointsByTeamMemberTypeId, callReadTeamMemberTypeRestEndpointsByTeamMemberTypeId, callUpdateTeamMemberTypeRestEndpoints } from "../../../../../endpoints/rest/teamMemberTypes/v1/TeamMemberTypeRestEndpoints";
import { TeamMemberType } from "../../../../../models/teamMemberTypes/v1/TeamMemberType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('teamMemberType endpoint tests', () => {
    var teamMemberTypeId: number | undefined; 
    var teamMemberType: TeamMemberType = new TeamMemberType();
    teamMemberType.teamMemberTypeName = "1";

    var updatedTeamMemberType: TeamMemberType = new TeamMemberType();
    updatedTeamMemberType.teamMemberTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create teamMemberType', async () => {
        var teamMemberTypeResponse: TeamMemberType = await callCreateTeamMemberTypeRestEndpoints(
            teamMemberType, env, domain);
        teamMemberTypeId = teamMemberTypeResponse.id;
        expect(teamMemberType.teamMemberTypeName).toBe(teamMemberTypeResponse.teamMemberTypeName);
    });

    test('call read teamMemberType', async () => {
        var teamMemberTypes: TeamMemberType[] | undefined = await callReadTeamMemberTypeRestEndpointsByTeamMemberTypeId(
            teamMemberTypeId || 1, env, domain) || [];
        expect(teamMemberTypes[0].teamMemberTypeName).toBe(teamMemberType.teamMemberTypeName);
    });

    test('call update teamMemberType', async () => {
        var teamMemberTypeResponse: TeamMemberType[] = await callUpdateTeamMemberTypeRestEndpoints(
            updatedTeamMemberType, env, domain);
        expect(updatedTeamMemberType.teamMemberTypeName).toBe(teamMemberTypeResponse[0].teamMemberTypeName);
    });

    test('call delete teamMemberType', async () => {
        var deleteTeamMemberTypeResponse: boolean = await callDeleteTeamMemberTypeRestEndpointsByTeamMemberTypeId(
            teamMemberTypeId || 1, env, domain);
        expect(true).toBe(deleteTeamMemberTypeResponse);
    });
});