import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updatePolicyFor, updateSelectedCity, updateSelectedState } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";
import { callReadStateRestEndpoints } from "../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { updateCities, updateStates } from "../../redux/reducers/GeographyReducer";
import { toObject } from "../../utils/Parser";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";

export default function SelectState(){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const states = useSelector((state: RootState) => {
        return state.geography.states;
    });
    const selectedState = useSelector((state: RootState) => {
        return state.policy.selectedState;
    }) || "";
    const cities = useSelector((state: RootState) => {
        return state.geography.cities;
    });
    let isLoadingOpen: boolean = useSelector((state: RootState)=>{
        return state.loading.value;
    });
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/selectState"));
        }, []
    );
    function onClickSelectState(event: any) {
        if(selectedState === "") {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select a state"));
            return;
        }
        if(cities) {
            if(cities.length > 0) {
                if(cities[0].state?.stateName !== selectedState) {
                    dispatch(updateCities(null));
                    dispatch(updateSelectedCity(null));
                }
            }
        }
        navigate("/selectCity");
    }
    function onClickBack() {
        navigate("/selectMagnitude");
    }
    useEffect(
        function() {
            async function getStates() {
                dispatch(updateLoadingState(true));
                if(!states) {
                    const statesResponse = await callReadStateRestEndpoints(env, domain);
                    if(statesResponse) {
                        dispatch(updateStates(toObject(statesResponse)));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getStates();
        }, []
    )
    if(isLoadingOpen) {
        return (
            <div className="loadingBackground">
                <img src="logo-03.png" className="loading"></img>
            </div>
        );
    }
    return (    
        <div className='column2'>
            <form className="flexContainer" onSubmit={(e)=>{
                e.preventDefault();
                onClickSelectState(e);
            }}>
                <h1>Select State</h1>
                <select defaultValue={selectedState}
                    onChange={(event)=>{dispatch(updateSelectedState(event.target.value))}}>
                    <option disabled value="">-</option>
                    {states?.map((option, index) => (
                    <option key={index} value={option.stateName}>
                    {option.stateName}
                    </option>
                    ))}
                </select>
                <div className='flexInner'>
                    <button name="action" type="submit" >Submit</button>
                </div>
                <button onClick={onClickBack} type="button">Back</button>
            </form>
        </div> 
    );
};