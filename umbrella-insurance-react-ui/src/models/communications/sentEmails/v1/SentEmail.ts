import { Session } from "../../../users/sessions/v1/Session";

export class SentEmail {
    id?: number;
    session?: Session;
    recipientEmailAddress?: string;
    senderEmailAddress?: string;
    contentType?: string;
    emailSubject?: string;
    emailBody?: string;
    sentDateTime?: string; //yyyy-mm-dd hh:mm:ss

    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.session) {
                this.session = new Session(obj.session);
            }
            if(obj?.recipientEmailAddress) {
                this.recipientEmailAddress = obj.recipientEmailAddress;
            }
            if(obj?.senderEmailAddress) {
                this.senderEmailAddress = obj.senderEmailAddress;
            }
            if(obj?.contentType) {
                this.contentType = obj.contentType;
            }
            if(obj?.emailSubject) {
                this.emailSubject = obj.emailSubject;
            }
            if(obj?.emailBody) {
                this.emailBody = obj.emailBody;
            }
            if(obj?.sentDateTime) {
                this.sentDateTime = obj.sentDateTime;
            }
        }
    }
}