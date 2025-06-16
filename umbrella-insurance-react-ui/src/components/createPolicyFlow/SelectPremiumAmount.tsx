import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateEndPolicyMonthAndYear, updatePolicyFor, updateSelectedMagnitude, updateSelectedPremiumAmount, updateStartPolicyMonthAndYear } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";

export default function SelectCoverageAmount(){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });  
    const username = useSelector((state: RootState) => {
        return state.user.username;
    });
    const selectedPremiumAmount = useSelector((state: RootState) => {
        return state.policy.selectedPremiumAmount;
    }) || 0;  
    const policyFor = useSelector((state: RootState) => {
        return state.policy.policyFor;
    }) || "";
    const minOrMaxText = policyFor === "Insured" ? "maximum" : "minimum";
 
    useEffect(
        function() {
            dispatch(updateCurrentPage("/selectPremiumAmount"));
        }, []
    );
    function onClickNext(event: any) {
        if(username === undefined) {
            navigate("/signIn");
        } else {
            navigate("/");
        }
    }
    function onClickBack() {
        navigate("/selectCoverageAmount");
    }

    function onClickPremiumAmountChange(event: any) {
        dispatch(updateSelectedPremiumAmount(event.target.value));
    }

    return (    
        <div className='column2'>
            <form className="flexContainer" onSubmit={(e)=>{
                e.preventDefault();
                onClickNext(e);
            }}>
                <h1>Select {minOrMaxText} Premium Amount</h1>
                <input type="number" id="premiumAmountPicker" name="premiumAmountPicker" value={selectedPremiumAmount}
                    min={0} onChange={onClickPremiumAmountChange}/>
                <button name="action" type="submit">Next</button>
                <button onClick={onClickBack}>Back</button>
            </form>
        </div> 
    );
};