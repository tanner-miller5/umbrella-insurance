import { User } from "../../v1/User";

export class Password {
    id?: number;
    user?: User;
    salt?: string;
    password?: string;
    hashedPassword?: string;
    createdDateTime?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.salt) {
                this.salt = obj.salt;
            }
            if(obj?.password) {
                this.password = obj.password;
            }
            if(obj?.hashedPassword) {
                this.hashedPassword = obj.hashedPassword;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
        }
    }
}