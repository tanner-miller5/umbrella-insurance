import { Session } from "../../users/sessions/v1/Session";
import { User } from "../../users/v1/User";

export class Review {
    id?: number;
    createdDateTime?: string; //yyyy-mm-dd hh:mm:ss
    subject?: string;
    comment?: string;
    rating?: number;
    session?: Session;
    frontendAppVersion?: string;
    backendAppVersion?: string;
    user?: User;
    constructor(obj?: any) {
        if (obj) {
            if (obj?.id) {
                this.id = obj.id;
            }
            if (obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if (obj?.subject) {
                this.subject = obj.subject;
            }
            if (obj?.comment) {
                this.comment = obj.comment;
            }
            if (obj?.rating) {
                this.rating = obj.rating;
            }
            if (obj?.session) {
                this.session = new Session(obj.session);
            }
            if (obj?.frontendAppVersion) {
                this.frontendAppVersion = obj.frontendAppVersion;
            }
            if (obj?.backendAppVersion) {
                this.backendAppVersion = obj.backendAppVersion;
            }
            if (obj?.user) {
                this.user = new User(obj.user);
            }
        }
    }
}