import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateEndPolicyMonthAndYear, updatePolicyFor, updateSelectedCoverageAmount, updateSelectedMagnitude, updateStartPolicyMonthAndYear } from "../../redux/reducers/PolicyReducer";
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
    const selectedCoverageAmount = useSelector((state: RootState) => {
        return state.policy.selectedCoverageAmount;
    }) || 0;  

    const policyFor = useSelector((state: RootState) => {
        return state.policy.policyFor;
    }) || "";
    const minOrMaxText = policyFor === "Insured" ? "minimum" : "maximum";
 
    useEffect(
        function() {
            dispatch(updateCurrentPage("/selectCoverageAmount"));
        }, []
    );
    function onClickNext(event: any) {
        if(Number(selectedCoverageAmount) === 0) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Coverage Amount can't be 0"));
            return;
        }
        navigate("/selectPremiumAmount");
    }
    function onClickBack() {
        navigate("/selectPeriodCoverage");
    }

    function onClickCoverageAmountChange(event: any) {
        dispatch(updateSelectedCoverageAmount(event.target.value));
    }

    return (    
        <div className='column2'>
            <form className="flexContainer" onSubmit={(e)=>{
                e.preventDefault();
                onClickNext(e);
            }}>
                <h1>Select {minOrMaxText} Coverage Amount</h1>
                <input type="number" id="coverageAmountPicker" name="coverageAmountPicker" value={selectedCoverageAmount}
                    min={0} onChange={onClickCoverageAmountChange}/>
                <button name="action" type="submit">Next</button>
                <button onClick={onClickBack}>Back</button>
            </form>
        </div> 
    );
};