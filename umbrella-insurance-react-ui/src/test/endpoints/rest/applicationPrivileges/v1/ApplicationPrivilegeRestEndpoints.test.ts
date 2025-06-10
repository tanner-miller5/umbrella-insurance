
import { callCreateApplicationPrivilegeRestEndpoints, callDeleteApplicationPrivilegeRestEndpointsByApplicationPrivilegeId, callReadApplicationPrivilegeRestEndpointsByApplicationPrivilegeId, callUpdateApplicationPrivilegeRestEndpoints } from "../../../../../endpoints/rest/applicationPrivileges/v1/ApplicationPrivilegeRestEndpoints";
import { ApplicationPrivilege } from "../../../../../models/applicationPrivileges/v1/ApplicationPrivilege";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('applicationPrivilege endpoint tests', () => {
    var applicationPrivilegeId: number | undefined; 
    var applicationPrivilege: ApplicationPrivilege = new ApplicationPrivilege();
    applicationPrivilege.applicationPrivilegeName = "1";

    var updatedApplicationPrivilege: ApplicationPrivilege = new ApplicationPrivilege();
    updatedApplicationPrivilege.applicationPrivilegeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create applicationPrivilege', async () => {
        var applicationPrivilegeResponse: ApplicationPrivilege = await callCreateApplicationPrivilegeRestEndpoints(applicationPrivilege, env, domain);
        applicationPrivilegeId = applicationPrivilegeResponse.id;
        expect(applicationPrivilege.applicationPrivilegeName).toBe(applicationPrivilegeResponse.applicationPrivilegeName);
    });

    test('call read applicationPrivilege', async () => {
        var applicationPrivileges: ApplicationPrivilege[] | undefined = await callReadApplicationPrivilegeRestEndpointsByApplicationPrivilegeId(
            applicationPrivilegeId || 1, env, domain) || [];
        expect(applicationPrivileges[0].applicationPrivilegeName).toBe(applicationPrivilege.applicationPrivilegeName);
    });

    test('call update applicationPrivilege', async () => {
        var applicationPrivilegeResponse: ApplicationPrivilege[] = await callUpdateApplicationPrivilegeRestEndpoints(
            updatedApplicationPrivilege, env, domain);
        expect(updatedApplicationPrivilege.applicationPrivilegeName).toBe(applicationPrivilegeResponse[0].applicationPrivilegeName);
    });

    test('call delete applicationPrivilege', async () => {
        var deleteApplicationPrivilegeResponse: boolean = await callDeleteApplicationPrivilegeRestEndpointsByApplicationPrivilegeId(
            applicationPrivilegeId || 1, env, domain);
        expect(true).toBe(deleteApplicationPrivilegeResponse);
    });
});