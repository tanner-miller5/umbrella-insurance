export class LoanPayment {
    id?: number;
    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
        }
    }
}