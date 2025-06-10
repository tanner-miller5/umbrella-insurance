export class OrderType {
    id?: number;
    orderTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.orderTypeName) {
                this.orderTypeName = obj.orderTypeName;
            }
        }
    }
}