
import { callCreateApplicationPrivilegeRestEndpoints, callDeleteApplicationPrivilegeRestEndpointsByApplicationPrivilegeId } from "../../../../../endpoints/rest/applicationPrivileges/v1/ApplicationPrivilegeRestEndpoints";
import { callCreateApplicationRoleApplicationPrivilegeRelationshipRestEndpoints, 
    callDeleteApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleApplicationPrivilegeRelationshipId, 
    callReadApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleApplicationPrivilegeRelationshipId, 
    callUpdateApplicationRoleApplicationPrivilegeRelationshipRestEndpoints
} from "../../../../../endpoints/rest/applicationRoleApplicationPrivilegeRelationships/v1/ApplicationRoleApplicationPrivilegeRelationshipRestEndpoints";
import { callCreateApplicationRoleRestEndpoints, callDeleteApplicationRoleRestEndpointsByApplicationRoleId } from "../../../../../endpoints/rest/applicationRoles/v1/ApplicationRoleRestEndpoints";
import { ApplicationPrivilege } from "../../../../../models/applicationPrivileges/v1/ApplicationPrivilege";
import { ApplicationRoleApplicationPrivilegeRelationship } from "../../../../../models/applicationRoleApplicationPrivilegeRelationships/v1/ApplicationRoleApplicationPrivilegeRelationship";
import { ApplicationRole } from "../../../../../models/applicationRoles/v1/ApplicationRole";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('applicationRoleApplicationPrivilegeRelationship endpoint tests', () => {
    jest.setTimeout(60000);
    var applicationRoleApplicationPrivilegeRelationshipId: number | undefined; 
    var applicationRole: ApplicationRole = new ApplicationRole();
    applicationRole.applicationRoleName = "3";
    var applicationPrivilege: ApplicationPrivilege = new ApplicationPrivilege();
    applicationPrivilege.applicationPrivilegeName = "4";
    var applicationRoleApplicationPrivilegeRelationship: ApplicationRoleApplicationPrivilegeRelationship = new ApplicationRoleApplicationPrivilegeRelationship();

    var updatedApplicationRoleApplicationPrivilegeRelationship: ApplicationRoleApplicationPrivilegeRelationship = new ApplicationRoleApplicationPrivilegeRelationship();

    var domain: string = "http://localhost:8080";

    test('call create applicationRoleApplicationPrivilegeRelationship', async () => {
        applicationRole = await callCreateApplicationRoleRestEndpoints(applicationRole, env, domain);
        applicationRoleApplicationPrivilegeRelationship.applicationRole = applicationRole;
        updatedApplicationRoleApplicationPrivilegeRelationship.applicationRole = applicationRole;

        applicationPrivilege = await callCreateApplicationPrivilegeRestEndpoints(applicationPrivilege, env, domain);
        applicationRoleApplicationPrivilegeRelationship.applicationPrivilege = applicationPrivilege;
        updatedApplicationRoleApplicationPrivilegeRelationship.applicationPrivilege = applicationPrivilege;

        var applicationRoleApplicationPrivilegeRelationshipResponse: ApplicationRoleApplicationPrivilegeRelationship = await callCreateApplicationRoleApplicationPrivilegeRelationshipRestEndpoints(
            applicationRoleApplicationPrivilegeRelationship, env, domain);
        applicationRoleApplicationPrivilegeRelationshipId = applicationRoleApplicationPrivilegeRelationshipResponse.id;

    });

    test('call read applicationRoleApplicationPrivilegeRelationship', async () => {
        var applicationRoleApplicationPrivilegeRelationships: ApplicationRoleApplicationPrivilegeRelationship[] | undefined = await callReadApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleApplicationPrivilegeRelationshipId(
            applicationRoleApplicationPrivilegeRelationshipId || 1, env, domain) || [];

    });

    test('call update applicationRoleApplicationPrivilegeRelationship', async () => {
        var applicationRoleApplicationPrivilegeRelationshipResponse: ApplicationRoleApplicationPrivilegeRelationship[] = await callUpdateApplicationRoleApplicationPrivilegeRelationshipRestEndpoints(
            updatedApplicationRoleApplicationPrivilegeRelationship, env, domain);
    });

    test('call delete applicationRoleApplicationPrivilegeRelationship', async () => {
        var deleteApplicationRoleApplicationPrivilegeRelationshipResponse: boolean = await callDeleteApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleApplicationPrivilegeRelationshipId(
            applicationRoleApplicationPrivilegeRelationshipId || 1, env, domain);
        expect(true).toBe(deleteApplicationRoleApplicationPrivilegeRelationshipResponse);

        var deleteApplicationPrivilegeResponse: boolean = await callDeleteApplicationPrivilegeRestEndpointsByApplicationPrivilegeId(
            applicationPrivilege.id || 1, env, domain);

        var deleteApplicationRoleRestEndpoints: boolean = await callDeleteApplicationRoleRestEndpointsByApplicationRoleId(
            applicationRole.id || 1, env, domain);
    });
});