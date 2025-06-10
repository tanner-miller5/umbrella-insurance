import { Unit } from "../../units/v1/Unit";

export class Item {
    id?: number;
    itemName?: string;
    price?: number;
    description?: string;
    itemImage?: number[];
    upc?: string; 
    unit?: Unit;
    quantity?: number;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.itemName) {
                this.itemName = obj.itemName;
            }
            if(typeof obj?.price === 'number') {
                this.price = obj.price;
            }
            if(obj?.description) {
                this.description = obj.description;
            }
            if(obj?.itemImage) {
                this.itemImage = obj.itemImage;
            }
            if(obj?.upc) {
                this.upc = obj.upc;
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
            if(obj?.quantity) {
                this.quantity = obj?.quantity;
            }
        }
    }
    /*
    toJSON() {
        return {
            "id": this.id ? this.id?.toString() : null,
            "itemName": this.itemName ? this.itemName?.toString() : null,
            "price": this.price ? this.price : null,
            "description": this.description ? this.description : null,
            "itemImage": this.itemImage ? this.itemImage : null,
            "upc": this.upc ? this.upc?.toString() : null,
            "unit": this.unit ? this.unit?.toString(): null
        }
    }
        */
}