import { Unit } from "../../../units/v1/Unit";
import { User } from "../../v1/User";
import { AccountBalanceType } from "../accountBalanceTypes/v1/AccountBalanceType";

export class AccountBalance {
    id?: number;
    accountBalanceValue?: number;
    accountBalanceType?: AccountBalanceType;
    updatedDateTime?: string;
    user?: User;
    unit?: Unit;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.accountBalanceValue) {
                this.accountBalanceValue = obj.accountBalanceValue;
            }
            if(obj?.accountBalanceType) {
                this.accountBalanceType = new AccountBalanceType(obj.accountBalanceType);
            }
            if(obj?.updatedDateTime) {
                this.updatedDateTime = obj.updatedDateTime;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
        }
    }
}