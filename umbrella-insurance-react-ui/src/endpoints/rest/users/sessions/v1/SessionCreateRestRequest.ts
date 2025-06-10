export class SessionCreateRestRequest {
    username?: string;
    password?: string;

    constructor(obj?: any) {
        if (obj) {
            if(obj?.username) {
                this.username = obj.username;
            }
            if (obj?.password) {
                this.password = obj.password;
            }
        }
    }
}