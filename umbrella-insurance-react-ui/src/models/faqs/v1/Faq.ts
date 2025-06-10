import { Session } from "../../users/sessions/v1/Session";

export class Faq {
    id?: number;
    createdDateTime?: string; //yyyy-mm-dd hh:mm:ss
    question?: string;
    answer?: string;
    faqName?: string;
    session?: Session;
    constructor(obj?: any) {
        if (obj) {
            if (obj?.id) {
                this.id = obj.id;
            }
            if (obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if (obj?.question) {
                this.question = obj.question;
            }
            if (obj?.answer) {
                this.answer = obj.answer;
            }
            if (obj?.faqName) {
                this.faqName = obj.faqName;
            }
            if (obj?.session) {
                this.session = new Session(obj.session);
            }
        }
    }
}