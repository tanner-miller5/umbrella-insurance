export class Promotion {
    id?: number;
    promotionName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.promotionName) {
                this.promotionName = obj.promotionName;
            }
        }
    }
}