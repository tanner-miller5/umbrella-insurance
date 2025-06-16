import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updatePolicyFor, updateSelectedState } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";
import { callReadStateRestEndpoints } from "../../endpoints/rest/geographies/states/v1/StateRestEndpoints";
import { updateStates } from "../../redux/reducers/GeographyReducer";
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
    let isLoadingOpen: boolean = useSelector((state: RootState)=>{
        return state.loading.value;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/selectState"));
        }, []
    );
    function onClickSelectState(event: any) {
        if(selectedState === "") {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select a state"));
            return;
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
                const states = await callReadStateRestEndpoints(env, domain);
                if(states) {
                    dispatch(updateStates(toObject(states)));
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
                <button onClick={onClickBack}>Back</button>
            </form>
        </div> 
    );
};