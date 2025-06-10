import { User } from "../../v1/User";

export class UsernameHistory {
    id?: number;
    user?: User;
    username?: string;
    createdDateTime?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.username) {
                this.username = obj.username;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
        }
    }
}