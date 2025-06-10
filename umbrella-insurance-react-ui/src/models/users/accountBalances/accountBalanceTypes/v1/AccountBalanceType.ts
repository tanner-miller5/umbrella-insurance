export class AccountBalanceType {
    id?: number;
    accountBalanceTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.accountBalanceTypeName) {
                this.accountBalanceTypeName = obj.accountBalanceTypeName;
            }
        }
    }
}