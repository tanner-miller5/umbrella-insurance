import { Unit } from "../../../../units/v1/Unit";
import { AccountBalanceTransactionStatus } from "../accountBalanceTransactionStatuses/v1/AccountBalanceTransactionStatus";
import { AccountBalanceTransactionType } from "../accountBalanceTransactionTypes/v1/AccountBalanceTransactionType";

export class AccountBalanceTransaction {
    id?: number;
    accountBalanceTransactionName?: string;
    createdDateTime?: string;
    amount?: number;
    unit?: Unit;
    accountBalanceTransactionType?: AccountBalanceTransactionType;
    accountBalanceTransactionStatus?: AccountBalanceTransactionStatus;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.accountBalanceTransactionName) {
                this.accountBalanceTransactionName = obj.accountBalanceTransactionName;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.amount) {
                this.amount = obj.amount;
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
            if(obj?.accountBalanceTransactionType) {
                this.accountBalanceTransactionType = new AccountBalanceTransactionType(obj.accountBalanceTransactionType);
            }
            if(obj?.accountBalanceTransactionStatus) {
                this.accountBalanceTransactionStatus = new AccountBalanceTransactionStatus(obj.accountBalanceTransactionStatus);
            }
        }
    }
}