import { Analyst } from "../../../people/analysts/v1/Analyst";
import { Unit } from "../../../units/v1/Unit";
import { User } from "../../v1/User";

export class Bot {
    id?: number;
    user?: User;
    botName?: string;
    analyst?: Analyst;
    createdDateTime?: string;
    isDeleted?: boolean;
    isDisabled?: boolean;
    deletedDateTime?: string;
    amountFunded?: number;
    unit?: Unit;//currency

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.botName) {
                this.botName = obj.botName;
            }
            if(obj?.analyst) {
                this.analyst = new Analyst(obj.analyst);
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(typeof obj?.isDeleted === "boolean") {
                this.isDeleted = obj.isDeleted;
            }
            if(typeof obj?.isDisabled === "boolean") {
                this.isDisabled = obj.isDisabled;
            }
            if(obj?.deletedDateTime) {
                this.deletedDateTime = obj.deletedDateTime;
            }
            if(obj?.amountFunded) {
                this.amountFunded = obj.amountFunded;
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
        }
    }
}