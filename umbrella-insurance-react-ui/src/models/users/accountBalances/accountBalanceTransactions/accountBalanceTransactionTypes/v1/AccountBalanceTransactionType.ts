export class AccountBalanceTransactionType {
    id?: number;
    accountBalanceTransactionTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.accountBalanceTransactionTypeName) {
                this.accountBalanceTransactionTypeName = obj.accountBalanceTransactionTypeName;
            }
        }
    }
}