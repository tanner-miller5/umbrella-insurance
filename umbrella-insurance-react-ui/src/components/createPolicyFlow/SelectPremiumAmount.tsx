import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateEndPolicyMonthAndYear, updatePolicyFor, updateSelectedMagnitude, updateSelectedPremiumAmount, updateStartPolicyMonthAndYear } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";
import { PendingPolicy } from "../../models/users/policies/pendingPolicies/v1/PendingPolicy";
import { toUTCString } from "../headers/Header";
import { Location} from "../../models/geographies/locations/v1/Location";

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
    const selectedCoverageAmount = useSelector((state: RootState) => {
        return state.policy.selectedCoverageAmount;
    }) || 0;  
    const policyFor = useSelector((state: RootState) => {
        return state.policy.policyFor;
    }) || "";
    const minOrMaxText = policyFor === "Insured" ? "maximum" : "minimum";
    const startPolicyMonthAndYear = useSelector((state: RootState) => {
        return state.policy.startPolicyMonthAndYear;
    }) || "";
    const endPolicyMonthAndYear = useSelector((state: RootState) => {
        return state.policy.endPolicyMonthAndYear;
    }) || "";
    const selectedCity = useSelector((state: RootState) => {
        return state.policy.selectedCity;
    }) || "";
    const selectedState = useSelector((state: RootState) => {
        return state.policy.selectedState;
    }) || "";
    const cities = useSelector((state: RootState) => {
        return state.geography.cities;
    });
    const selectedPeril = useSelector((state: RootState) => {
        return state.policy.selectedPeril;
    }) || "";
    let location: Location;
    if(cities) {
        for(let i = 0; i < cities.length; i++) {
            let city: Location = cities[i];
            if(city.city?.cityName === selectedCity &&
                city.state?.stateName === selectedState
            ) {
                location = city;
            }
        }
    }

    useEffect(
        function() {
            dispatch(updateCurrentPage("/selectPremiumAmount"));
        }, []
    );
    function onClickNext(event: any) {
        if(username === undefined) {
            navigate("/signIn");
        } else {
            let pendingPolicy: PendingPolicy = new PendingPolicy();
            pendingPolicy.accountBalanceCanceledEscrowTransaction = undefined;
            pendingPolicy.accountBalanceEscrowTransaction = undefined;
            pendingPolicy.canceledDateTime = undefined;
            pendingPolicy.coverageAmount = selectedCoverageAmount;
            let currentTime = new Date(Date.now());
            pendingPolicy.createdDateTime = toUTCString(currentTime);
            pendingPolicy.endDate = endPolicyMonthAndYear;
            pendingPolicy.startDate = startPolicyMonthAndYear;
            pendingPolicy.fee = undefined;
            pendingPolicy.impliedProbability = selectedPremiumAmount / selectedCoverageAmount;
            pendingPolicy.location = location;
            pendingPolicy.orderType = undefined;
            pendingPolicy.originalPendingPolicy = undefined;
            pendingPolicy.pendingPolicyName = undefined;
            pendingPolicy.pendingPolicyState = undefined;
            pendingPolicy.pendingPolicyType = undefined;
            pendingPolicy.peril = undefined;
            pendingPolicy.premiumAmount = selectedPremiumAmount;
            pendingPolicy.session = undefined;
            pendingPolicy.splitPendingPolicy1 = undefined;
            pendingPolicy.splitPendingPolicy2 = undefined;
            pendingPolicy.unit = undefined;

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