import { Session } from "../../sessions/v1/Session";
import { User } from "../../v1/User";
import { EventType } from "../eventTypes/v1/EventType";

export class Event {
    id?: number;
    session?: Session;
    eventType?: EventType;
    createdDateTime?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.session) {
                this.session = new Session(obj.session);
            }
            if(obj?.eventType) {
                this.eventType = new EventType(obj.eventType);
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
        }
    }
}