import { Transfer } from "../../v1/Transfer";

export class WireTransfer {
    id?: number;
    transfer?: Transfer;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.transfer) {
                this.transfer = new Transfer(obj.transfer);
            }
        }
    }
}