import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updatePolicyFor, updateSelectedCity } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";
import { callReadLocationRestEndpointsByStateName } from "../../endpoints/rest/geographies/locations/v1/LocationRestEndpoints";
import { updateCities } from "../../redux/reducers/GeographyReducer";
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
    const cities = useSelector((state: RootState) => {
        return state.geography.cities;
    });
    const selectedState = useSelector((state: RootState) => {
        return state.policy.selectedState;
    }) || "";
    const selectedCity = useSelector((state: RootState) => {
        return state.policy.selectedCity;
    }) || "";
    let isLoadingOpen: boolean = useSelector((state: RootState)=>{
        return state.loading.value;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/selectCity"));
        }, []
    );
    function onClickSelectCity(event: any) {
        if(selectedCity === "") {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select a city"));
            return;
        }
        navigate("/selectPeriodCoverage");
    }
    function onClickBack() {
        navigate("/selectState");
    }
    useEffect(
        function() {
            async function getCitiesByState(stateName: string) {
                dispatch(updateLoadingState(true));
                if(!cities) {
                    const citiesResponse = await callReadLocationRestEndpointsByStateName(stateName, env, domain);
                    if(citiesResponse) {
                        dispatch(updateCities(toObject(citiesResponse)));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getCitiesByState(selectedState);
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
                onClickSelectCity(e);
            }}>
                <h1>Select City</h1>
                <select defaultValue={selectedCity}
                    onChange={(event)=>{dispatch(updateSelectedCity(event.target.value))}}>
                    <option disabled value="">-</option>
                    {cities?.map((option, index) => (
                    <option key={index} value={option.city?.cityName}>
                    {option.city?.cityName}
                    </option>
                    ))}
                </select>
                <div className='flexInner'>
                    <button name="action" type="submit" >Submit</button>
                </div>
                <button onClick={onClickBack}>Back</button>
            </form>
        </div> 
    );
};