export class AccountBalanceTransactionStatus {
    id?: number;
    accountBalanceTransactionStatusName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.accountBalanceTransactionStatusName) {
                this.accountBalanceTransactionStatusName = obj.accountBalanceTransactionStatusName;
            }
        }
    }
}