import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store/Store";
import { useEffect } from "react";
import { updateCurrentPage } from "../../redux/reducers/AppReducer";
import ItemRow from "./ItemRow";
import { useNavigate } from "react-router-dom";

export default function Items() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const items = useSelector((state: RootState) => {
        return state.app.items;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/items"));
        }, []
    )
    const rows = [];
    for (let i = 0; i < items.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<ItemRow key={i} id={items[i].id || 0} 
            itemName={items[i].itemName}
            price={items[i].price}
            description={items[i].description}
            itemImage={items[i].itemImage}
            upc={items[i].upc}
            unit={items[i].unit}
        />);
    }

    async function onClickSubmit() {
        navigate("/checkout");
    }
    
    return (
        <div className='column2'>
            <h1>Items</h1> 
            {rows}
            <form className='flexContainer' onSubmit={(e)=>{
                e.preventDefault();
                onClickSubmit();
                }}>
                <div className="flexInner">
                    <button name="action" type="submit">Checkout</button>
                </div>
            </form>
        </div>
    )
}