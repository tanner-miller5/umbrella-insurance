import { Session } from "../../sessions/v1/Session";

export class TOTP {
    id?: number;
    session?: Session;
    totpCode?: string;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.session) {
                this.session = new Session(obj.session);
            }
            if(obj?.totpCode) {
                this.totpCode = obj.totpCode;
            }
        }
    }
}