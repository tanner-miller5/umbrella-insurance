import { callCreateCartItemRelationshipRestEndpoints, 
    callReadCartItemRelationshipRestEndpointsByCartIdAndItemId, callUpdateCartItemRelationshipRestEndpoints } from "../../endpoints/rest/cartItemRelationships/v1/CartItemRelationshipRestEndpoints";
import { CartItemRelationship } from "../../models/cartItemRelationships/v1/CartItemRelationship";
import { Cart } from "../../models/carts/v1/Cart";
import { Item } from "../../models/items/v1/Item";
import { Unit } from "../../models/units/v1/Unit";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store/Store";
import { callReadItemRestEndpointsByItemId } from "../../endpoints/rest/items/v1/ItemRestEndpoints";

interface ItemRowProps {
    id?: number;
    itemName?: string;
    price?: number;
    description?: string;
    itemImage?: number[];
    upc?: string; 
    unit?: Unit;
    quantity?: number;
}
export default function ItemRow({id, itemName, price, description, itemImage, upc, unit,
    quantity
}:ItemRowProps){
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const cart = useSelector((state: RootState) => {
        return state.user.cart;
    });
    const session = useSelector((state: RootState) => {
        return state.user.sessionCode;
    }) || "";
    const userId = useSelector((state: RootState) => {
        return state.user.userId;
    }) || 0;

    async function onClickAddItem(cart: Cart | undefined, itemId: number | undefined, 
        env: string, domain: string, session: string) {
        let cartItemRelationships: CartItemRelationship[] | undefined;
        let cartItemRelationship: CartItemRelationship = new CartItemRelationship();
        if(cart?.id && itemId) {
            let items: Item[] = await callReadItemRestEndpointsByItemId(itemId, env, domain, session) || [];
            
            cartItemRelationships = await callReadCartItemRelationshipRestEndpointsByCartIdAndItemId(
                cart.id, itemId, env, domain, session);
            if(!cartItemRelationships) {
                cartItemRelationship.quantity = 1;
                cartItemRelationship.item = items[0];
                cartItemRelationship.cart = cart;
                cartItemRelationship = await callCreateCartItemRelationshipRestEndpoints(cartItemRelationship,
                    env, domain, session);
            }
        }
    }

    async function onClickSubtractItem(itemId: number, env: string, 
        domain: string, session: string) {
        //await callUpdateCartItemRelationshipRestEndpoints(cartItemRelationship, env, domain, session);
    }

    return (    
    <div className="outlined">
        <form className='flexContainer' onSubmit={(e)=>{
                e.preventDefault();
            }}>
            <label>Id: {id?.toString()}</label>
            <label>Item Name: {itemName}</label>
            <label>Price: {price}</label>
            <label>Description: {description}</label>
            <label>Item Image: {itemImage}</label>
            <label>UPC: {upc}</label>
            <label>Unit: {unit?.unitName?.replaceAll('_', ' ')}</label>
            <label>Quantity: {quantity}</label>
            <div className="flexInner">
                <button name="action" type="button" 
                //onClick={onClickSubtractItem(id || 0, env, domain, session)}
                    >Subtract Item</button>
            </div>
            <div className="flexInner">
                <button name="action" type="button" 
                //onClick={onClickAddItem(cart, item, env, domain, session)}
                >Add Item</button>
            </div>
        </form>
    </div>);
}