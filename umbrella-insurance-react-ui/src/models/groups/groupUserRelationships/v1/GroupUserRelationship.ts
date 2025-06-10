import { User } from "../../../users/v1/User";
import { Group } from "../../v1/Group";

export class GroupUserRelationship {
    id?: number;
    group?: Group;
    user?: User;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.group) {
                this.group = new Group(obj.group);
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
        }
    }
}