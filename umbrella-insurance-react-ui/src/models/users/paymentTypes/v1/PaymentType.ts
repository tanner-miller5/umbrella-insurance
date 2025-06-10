export class PaymentType {
    id?: number;
    paymentTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.paymentTypeName) {
                this.paymentTypeName = obj.paymentTypeName;
            }
        }
    }
}