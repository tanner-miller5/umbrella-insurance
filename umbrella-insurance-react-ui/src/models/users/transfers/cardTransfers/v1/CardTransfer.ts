import { CardOnFile } from "../../../cardsOnFile/v1/CardOnFile";
import { Transfer } from "../../v1/Transfer";

export class CardTransfer {
    id?: number;
    cardOnFile?: CardOnFile;
    transfer?: Transfer;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.cardOnFile) {
                this.cardOnFile = new CardOnFile(obj.cardOnFile);
            }
            if(obj?.transfer) {
                this.transfer = new Transfer(obj.transfer);
            }
        }
    }
}