import { callCreateApplicationRoleRestEndpoints, callDeleteApplicationRoleRestEndpointsByApplicationRoleId, callReadApplicationRoleRestEndpointsByApplicationRoleId, callUpdateApplicationRoleRestEndpoints } from "../../../../../endpoints/rest/applicationRoles/v1/ApplicationRoleRestEndpoints";
import { ApplicationRole } from "../../../../../models/applicationRoles/v1/ApplicationRole";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('applicationRole endpoint tests', () => {
    var applicationRoleId: number | undefined; 
    var applicationRole: ApplicationRole = new ApplicationRole();
    applicationRole.applicationRoleName = "1";

    var updatedApplicationRole: ApplicationRole = new ApplicationRole();
    updatedApplicationRole.applicationRoleName = "2";

    var domain: string = "http://localhost:8080";

    test('call create applicationRole', async () => {
        var applicationRoleResponse: ApplicationRole = await callCreateApplicationRoleRestEndpoints(
            applicationRole, env, domain);
        applicationRoleId = applicationRoleResponse.id;
        expect(applicationRole.applicationRoleName).toBe(applicationRoleResponse.applicationRoleName);
    });

    test('call read applicationRole', async () => {
        var applicationRoles: ApplicationRole[] | undefined = await callReadApplicationRoleRestEndpointsByApplicationRoleId(
            applicationRoleId || 1, env, domain) || [];
        expect(applicationRoles[0].applicationRoleName).toBe(applicationRole.applicationRoleName);
    });

    test('call update applicationRole', async () => {
        var applicationRoleResponse: ApplicationRole[] = await callUpdateApplicationRoleRestEndpoints(
            updatedApplicationRole, env, domain);
        expect(updatedApplicationRole.applicationRoleName).toBe(applicationRoleResponse[0].applicationRoleName);
    });

    test('call delete applicationRole', async () => {
        var deleteApplicationRoleResponse: boolean = await callDeleteApplicationRoleRestEndpointsByApplicationRoleId(
            applicationRoleId || 1, env, domain);
        expect(true).toBe(deleteApplicationRoleResponse);
    });
});