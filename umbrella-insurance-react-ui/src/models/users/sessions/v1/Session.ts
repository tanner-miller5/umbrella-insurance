import { Device } from "../../../devices/v1/Device";
import { User } from "../../v1/User";

export class Session {
    id?: number;
    user?: User;
    sessionCode?: string;
    startDateTime?: string;
    endDateTime?: string;
    minutesToExpire?: number;
    device?: Device;

    constructor(obj?: any) {
        if(obj) {
            if (obj?.id) {
                this.id = obj.id;
            }
            if (obj?.user) {
                this.user = new User(obj.user)
            }
            if (obj?.sessionCode) {
                this.sessionCode = obj.sessionCode;
            }
            if (obj?.startDateTime) {
                this.startDateTime = obj.startDateTime;
            }
            if (obj?.endDateTime) {
                this.endDateTime = obj.endDateTime;
            }
            if (obj?.minutesToExpire) {
                this.minutesToExpire = obj.minutesToExpire;
            }
            if (obj?.device) {
                this.device = new Device(obj.device);
            }
        }
    }
    /*
    toJSON() {
        return {
            "id": this.id ? this.id?.toString() : null,
            "user": this.user ? this.user?.toString() : null,
            "sessionCode": this.sessionCode ? this.sessionCode  : null,
            "startDateTime": this.startDateTime ? this.startDateTime : null,
            "endDateTime": this.endDateTime ? this.endDateTime : null,
            "minutesToExpire": this.minutesToExpire ? this.minutesToExpire?.toString() : null,
            "device": this.device ? this.device?.toString(): null
        }
    }
        */

}