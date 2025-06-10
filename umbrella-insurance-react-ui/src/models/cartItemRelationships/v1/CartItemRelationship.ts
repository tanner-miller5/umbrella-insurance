import { Cart } from "../../carts/v1/Cart";
import { Item } from "../../items/v1/Item";

export class CartItemRelationship {
    id?: number;
    cart?: Cart;
    item?: Item;
    quantity?: number;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.cart) {
                this.cart = new Cart(obj.cart);
            }
            if(obj?.item) {
                this.item = new Item(obj.item);
            }
            if(obj?.quantity) {
                this.quantity = obj.quantity;
            }
        }
    }
}