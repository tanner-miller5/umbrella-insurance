import { ApplicationPrivilege } from "../../applicationPrivileges/v1/ApplicationPrivilege";
import { ApplicationRole } from "../../applicationRoles/v1/ApplicationRole";

export class ApplicationRoleApplicationPrivilegeRelationship {
    id?: number;
    applicationRole?:ApplicationRole;
    applicationPrivilege?:ApplicationPrivilege;

    constructor(obj?: any) {
        if (obj) {
            if (obj?.id) {
                this.id = obj.id;
            }
            if (obj?.applicationRole) {
                this.applicationRole = new ApplicationRole(obj.applicationRole);
            }
            if (obj?.applicationPrivilege) {
                this.applicationPrivilege = new ApplicationPrivilege(obj.applicationPrivilege);
            }
        }
    }
}