import { Session } from "../../../users/sessions/v1/Session";

export class SentText {
    id?: number;
    session?: Session;
    recipientPhoneNumber?: string;
    senderPhoneNumber?: string;
    textMessage?: string;
    sentDateTime?: string; //yyyy-mm-dd hh:mm:ss

    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.sessionId) {
                this.session = new Session(obj.session);
            }
            if(obj?.recipientPhoneNumber) {
                this.recipientPhoneNumber = obj.recipientPhoneNumber;
            }
            if(obj?.senderPhoneNumber) {
                this.senderPhoneNumber = obj.senderPhoneNumber;
            }
            if(obj?.textMessage) {
                this.textMessage = obj.textMessage;
            }
            if(obj?.sentDateTime) {
                this.sentDateTime = obj.sentDateTime;
            }
        }
    }
}