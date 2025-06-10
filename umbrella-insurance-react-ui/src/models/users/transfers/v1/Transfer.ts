import { Unit } from "../../../units/v1/Unit";
import { User } from "../../v1/User";

export class Transfer {
    id?: number;
    user?: User;
    unit?: Unit;//currency
    amount?: number;
    createdDateTime?: string;
    isVoided?: boolean;
    voidedEvent?: Event;
    voidedDateTime?: string;
    postedDateTime?: string;
    transferName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
            if(obj?.amount) {
                this.amount = obj.amount;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.isVoided) {
                this.isVoided = obj.isVoided;
            }
            if(obj?.voidedEvent) {
                this.voidedEvent = new Event(obj.voidedEvent);
            }
            if(obj?.voidedDateTime) {
                this.voidedDateTime = obj.voidedDateTime;
            }
            if(obj?.postedDateTime) {
                this.postedDateTime = obj.postedDateTime;
            }
            if(obj?.transferName) {
                this.transferName = obj.transferName;
            }
        }
    }
}