import { Session } from "../../users/sessions/v1/Session";

export class Announcement {
    id?: number;
    createdDateTime?: string; //yyyy-mm-dd hh:mm:ss
    message?: string;
    announcementName?: string;
    session?: Session;

    constructor(obj?: any) {
        if (obj) {
            if (obj?.id) {
                this.id = obj.id;
            }
            if (obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if (obj?.message) {
                this.message = obj.message;
            }
            if (obj?.announcementName) {
                this.announcementName = obj.announcementName;
            }
            if (obj?.session) {
                this.session = new Session(obj.session);
            }
        }
    }
}