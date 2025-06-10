import { User } from "../../users/v1/User";

export class Cart {
    id?: number;
    stage?: string;
    user?: User;
    createdDateTime?: string; //yyyy-mm-dd

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.stage) {
                this.stage = obj.stage;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
        }
    }
}