import { User } from "../../users/v1/User";

export class Device {
    id?: number;
    ipAddress?: string;
    userAgent?: string;
    deviceName?: string;
    createdDateTime?: string; //yyyy-mm-dd hh:mm:ss

    constructor(obj?: any) {
        if (obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if (obj?.ipAddress) {
                this.ipAddress = obj.ipAddress;
            }
            if (obj?.userAgent) {
                this.userAgent = obj.userAgent;
            }
            if (obj?.deviceName) {
                this.deviceName = obj.deviceName;
            }
            if (obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
        }
    }
}