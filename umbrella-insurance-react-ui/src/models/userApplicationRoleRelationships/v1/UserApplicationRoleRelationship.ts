import { ApplicationRole } from "../../applicationRoles/v1/ApplicationRole";
import { User } from "../../users/v1/User";

export class UserApplicationRoleRelationship {
    id?: number
    user?: User;
    applicationRole?: ApplicationRole;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.applicationRole) {
                this.applicationRole = new ApplicationRole(obj.applicationRole);
            }
        }
    }
}