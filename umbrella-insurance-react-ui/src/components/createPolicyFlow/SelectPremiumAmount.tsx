import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen, updateOrderTypes, updatePendingPolicyStates, updatePendingPolicyTypes, updateUnits } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateEndPolicyMonthAndYear, updatePeril, updatePolicyFor, updateSelectedMagnitude, updateSelectedPremiumAmount, updateStartPolicyMonthAndYear } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";
import { PendingPolicy } from "../../models/users/policies/pendingPolicies/v1/PendingPolicy";
import { toUTCString, toUTCStringWithTimezone } from "../headers/Header";
import { Location} from "../../models/geographies/locations/v1/Location";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { callReadOrderTypeRestEndpoints } from "../../endpoints/rest/orderTypes/v1/OrderTypeRestEndpoints";
import { toObject } from "../../utils/Parser";
import { PendingPolicyType } from "../../models/users/policies/pendingPolicies/pendingPolicyTypes/v1/PendingPolicyType";
import { callReadPendingPolicyTypeRestEndpoints } from "../../endpoints/rest/users/policies/pendingPolicies/pendingPoliciyTypes/v1/PendingPolicyTypeRestEndpoints";
import { callReadPendingPolicyStateRestEndpoints } from "../../endpoints/rest/users/policies/pendingPolicies/pendingPolicyStates/v1/PendingPolicyStateRestEndpoints";
import { callReadUnitsRestEndpoint } from "../../endpoints/rest/units/v1/UnitRestEndpoints";
import { Session } from "../../models/users/sessions/v1/Session";
import { callReadSessionRestEndpointsBySessionCode } from "../../endpoints/rest/users/sessions/v1/SessionRestEndpoints";
import { updateButterBucksAccountValue, updateSession, updateUsdAccountValue } from "../../redux/reducers/UserReducer";
import { callReadPerilRestEndpointsByPerilName } from "../../endpoints/rest/perils/v1/PerilRestEndpoints";
import SelectInsurerOrInsured from "./SelectInsurerOrInsured";
import { callCreatePendingPolicyRestEndpoints } from "../../endpoints/rest/users/policies/pendingPolicies/v1/PendingPolicyRestEndpoints";
import { callCreatePendingPolicySoaEndpoints } from "../../endpoints/soa/pendingPolicies/v1/PendingPolicySoaEndpoints";
import { callReadAccountBalanceRestEndpointsByUserId } from "../../endpoints/rest/users/accountBalances/v1/AccountBalanceRestEndpoints";
import { UnitEnum } from "../../models/units/v1/UnitEnum";
import { AccountBalanceTypeEnum } from "../../models/users/accountBalances/accountBalanceTypes/v1/AccountBalanceTypeEnum";

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
    const userId = useSelector((state: RootState) => {
        return state.user.userId;
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
    const orderTypes = useSelector((state: RootState) => {
        return state.app.orderTypes;
    });
    const pendingPolicyTypes = useSelector((state: RootState) => {
        return state.app.pendingPolicyTypes;
    });
    const pendingPolicyStates = useSelector((state: RootState) => {
        return state.app.pendingPolicyStates;
    });
    const units = useSelector((state: RootState) => {
        return state.app.units;
    });
    const sessionCode = useSelector((state: RootState) => {
        return state.user.sessionCode;
    });
    const session = useSelector((state: RootState) => {
        return state.user.session;
    });
    const peril = useSelector((state: RootState) => {
        return state.policy.peril;
    });
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
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/selectPremiumAmount"));
        }, []
    );
    async function onClickNext(event: any) {
        if(username === undefined) {
            navigate("/signIn");
        } else {
            dispatch(updateLoadingState(true));
            if(Number(selectedPremiumAmount)>= Number(selectedCoverageAmount)) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Premium Amount can't be greater than coverage amount"));
                return;
            }
            if(Number(selectedPremiumAmount) === 0) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Premium Amount can't be 0"));
                return;
            }
            let pendingPolicy: PendingPolicy = new PendingPolicy();
            pendingPolicy.accountBalanceCanceledEscrowTransaction = undefined;
            pendingPolicy.accountBalanceEscrowTransaction = undefined;
            pendingPolicy.canceledDateTime = undefined;
            pendingPolicy.coverageAmount = selectedCoverageAmount;
            let currentTime = new Date(Date.now());
            pendingPolicy.createdDateTime = toUTCStringWithTimezone(currentTime);
            pendingPolicy.endDate = endPolicyMonthAndYear + "-01";
            pendingPolicy.startDate = startPolicyMonthAndYear + "-01";
            pendingPolicy.fee = undefined;
            pendingPolicy.impliedProbability = selectedPremiumAmount / selectedCoverageAmount;
            pendingPolicy.location = location;
            pendingPolicy.orderType = orderTypes[1];
            pendingPolicy.originalPendingPolicy = undefined;
            pendingPolicy.pendingPolicyName = peril?.perilName + "-" + sessionCode + "-" + currentTime;
            pendingPolicy.pendingPolicyState = pendingPolicyStates[1];
            if(policyFor === "Insurer") {
                pendingPolicy.pendingPolicyType = pendingPolicyTypes[0];
            } else {
                pendingPolicy.pendingPolicyType = pendingPolicyTypes[1];
            }
            pendingPolicy.peril = peril;
            pendingPolicy.premiumAmount = selectedPremiumAmount;
            pendingPolicy.session = session;
            pendingPolicy.splitPendingPolicy1 = undefined;
            pendingPolicy.splitPendingPolicy2 = undefined;
            if(units) {
                pendingPolicy.unit = units[0];
            }
            pendingPolicy = await callCreatePendingPolicySoaEndpoints(sessionCode || "", 
                pendingPolicy, env, domain);

            let accountBalances = await callReadAccountBalanceRestEndpointsByUserId(sessionCode || "", 
                userId || 0, env, domain);
            
            if(accountBalances && accountBalances.length === 2) {
                if(accountBalances[0].unit?.unitName === UnitEnum.usd.toString() &&
                    accountBalances[0].accountBalanceType?.accountBalanceTypeName  || ""
                    === AccountBalanceTypeEnum.balance.toString()) {
                    dispatch(updateUsdAccountValue(accountBalances[0].accountBalanceValue));
                }
                if(accountBalances[1].unit?.unitName === UnitEnum.butter_bucks.toString() &&
                    accountBalances[1].accountBalanceType?.accountBalanceTypeName  || ""
                    === AccountBalanceTypeEnum.balance.toString()) {
                    dispatch(updateButterBucksAccountValue(accountBalances[1].accountBalanceValue));
                }
            }
  

            navigate("/showPolicies");
            dispatch(updateLoadingState(true));
        }
    }

    useEffect(
        function() {
            async function getOrderTypes() {
                dispatch(updateLoadingState(true));
                if(orderTypes.length == 0) {
                    const orderTypesResponse = await callReadOrderTypeRestEndpoints(env, domain);
                    if(orderTypesResponse) {
                        dispatch(updateOrderTypes(toObject(orderTypesResponse)));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getOrderTypes();
        }, []
    )

    useEffect(
        function() {
            async function getPeril(selectedPeril: string) {
                console.log('getPeril ' + selectedPeril);
                dispatch(updateLoadingState(true));
                if(peril === undefined) {
                    const perilResponse = await callReadPerilRestEndpointsByPerilName(
                        selectedPeril, env, domain);
                    if(perilResponse) {
                        dispatch(updatePeril(toObject(perilResponse[0])));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getPeril(selectedPeril);
        }, []
    )

    useEffect(
        function() {
            async function getPendingPolicyTypes() {
                dispatch(updateLoadingState(true));
                if(pendingPolicyTypes.length == 0) {
                    const pendingPolicyTypesResponse = await callReadPendingPolicyTypeRestEndpoints(env, domain);
                    if(pendingPolicyTypesResponse) {
                        dispatch(updatePendingPolicyTypes(toObject(pendingPolicyTypesResponse)));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getPendingPolicyTypes();
        }, []
    )

    useEffect(
        function() {
            async function getSession(sessionCode?: string) {
                dispatch(updateLoadingState(true));
                if(session === undefined) {
                    const sessionResponse = await callReadSessionRestEndpointsBySessionCode(
                        sessionCode || "", env, domain);
                    if(sessionResponse) {
                        dispatch(updateSession(toObject(sessionResponse[0])));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getSession(sessionCode);
        }, []
    )

    useEffect(
        function() {
            async function getPendingPolicyStates() {
                dispatch(updateLoadingState(true));
                if(pendingPolicyStates.length == 0) {
                    const pendingPolicyStatesResponse = await callReadPendingPolicyStateRestEndpoints(env, domain);
                    if(pendingPolicyStatesResponse) {
                        dispatch(updatePendingPolicyStates(toObject(pendingPolicyStatesResponse)));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getPendingPolicyStates();
        }, []
    )

    useEffect(
        function() {
            async function getUnits() {
                dispatch(updateLoadingState(true));
                if(units === undefined) {
                    const unitsResponse = await callReadUnitsRestEndpoint(env, domain);
                    if(unitsResponse) {
                        dispatch(updateUnits(toObject(unitsResponse)));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getUnits();
        }, []
    )

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
                <button onClick={onClickBack} type="button">Back</button>
            </form>
        </div> 
    );
};