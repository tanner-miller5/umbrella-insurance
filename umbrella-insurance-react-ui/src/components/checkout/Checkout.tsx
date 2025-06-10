import { useNavigate } from 'react-router-dom';
import '../../css/checkout/checkout.css';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import ItemRow from '../items/ItemRow';

export default function Checkout() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const items = useSelector((state: RootState) => {
        return state.user.cartItems;
    }) || [];
    const rows = [];
    let total = 0;
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
            quantity={items[i].quantity}
        />);
        total += (items[i].price || 0) * (items[i]?.quantity || 0);
    }
    return (
        <div className='column2'>
            <h1>Checkout</h1>
            <div className="summary-section">
                <h2>Order Summary</h2>
                <div id="orderSummary">
                    {rows}
                    <hr/>
                    <p><strong>Total: ${total.toFixed(2)}</strong></p>
                </div>
            </div>
            <div className="checkout-container">
                <div className="form-section">
                    <h2>Shipping Information</h2>
                    <form id="checkoutForm">
                        <div className="form-group">
                            <label htmlFor="country">Country/Region</label>
                            <input type="text" id="country" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="name">Full Name</label>
                            <input type="text" id="name" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="phone_number">Phone number</label>
                            <input type="text" id="phone_number" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="address_line_1">Address line 1</label>
                            <input type="text" id="address_line_1" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="address_line_2">Address line 2</label>
                            <input type="text" id="address_line_2" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="city">City</label>
                            <input type="text" id="city" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="state">State</label>
                            <input type="text" id="state" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="zip_code">ZIP Code</label>
                            <input type="text" id="zip_code" required/>
                        </div>
                        <h2>Payment Information</h2>
                        <div className="form-group">
                            <label htmlFor="cardNumber">Card Number</label>
                            <input type="text" id="cardNumber" maxLength={16} required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="expiry">Expiry Date (MM/YY)</label>
                            <input type="text" id="expiry" maxLength={5} required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="cvv">CVV</label>
                            <input type="text" id="cvv" maxLength={3} required/>
                        </div>
                        <button type="submit">Place Order</button>
                    </form>
                </div>
            </div>
        </div>
    );
};