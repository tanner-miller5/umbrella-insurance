import { Session } from "../../users/sessions/v1/Session";

export class Ad {
    id?: number;
    adName?: string;
    adData?: string;
    createdDateTime?: string; //yyyy-mm-dd hh:mm:ss
    session?: Session;
    constructor(obj?: any) {
        if (obj) {
            if (obj.id) {
                this.id = obj.id;
            }
            if (obj.adName) {
                this.adName = obj.adName;
            }
            if (obj.adData) {
                this.adData = obj.adData;
            }
            if (obj.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if (obj.session) {
                this.session = new Session(obj.session);
            }
        }
    }
}