import { callCreateGroupRestEndpoints, callDeleteGroupRestEndpointsByGroupId, callReadGroupRestEndpointsByGroupId, callUpdateGroupRestEndpoints } from "../../../../../endpoints/rest/groups/v1/GroupRestEndpoints";
import { Group } from "../../../../../models/groups/v1/Group";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('group endpoint tests', () => {
    var groupId: number | undefined; 
    var group: Group = new Group();
    group.groupName = "1";

    var updatedGroup: Group = new Group();
    updatedGroup.groupName = "2";

    var domain: string = "http://localhost:8080";

    test('call create group', async () => {
        var groupResponse: Group = await callCreateGroupRestEndpoints(
            group, env, domain);
        groupId = groupResponse.id;
        expect(group.groupName).toBe(groupResponse.groupName);
    });

    test('call read group', async () => {
        var groups: Group[] | undefined = await callReadGroupRestEndpointsByGroupId(
            groupId || 1, env, domain) || [];
        expect(groups[0].groupName).toBe(group.groupName);
    });

    test('call update group', async () => {
        var groupResponse: Group[] = await callUpdateGroupRestEndpoints(
            updatedGroup, env, domain);
        expect(updatedGroup.groupName).toBe(groupResponse[0].groupName);
    });

    test('call delete group', async () => {
        var deleteGroupResponse: boolean = await callDeleteGroupRestEndpointsByGroupId(
            groupId || 1, env, domain);
        expect(true).toBe(deleteGroupResponse);
    });
});