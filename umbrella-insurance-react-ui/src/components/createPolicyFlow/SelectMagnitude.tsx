import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateEndPolicyMonthAndYear, updatePolicyFor, updateSelectedMagnitude, updateStartPolicyMonthAndYear } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";

export default function SelectMagnitude(){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });  
    const selectedPerilMinMagnitude = useSelector((state: RootState) => {
        return state.policy.selectedPerilMinMagnitude;
    }) || 0;  
    const selectedPerilMaxMagnitude = useSelector((state: RootState) => {
        return state.policy.selectedPerilMaxMagnitude;
    }) || 0;  
    const selectedPeril = useSelector((state: RootState) => {
        return state.policy.selectedPeril;
    }) || "";  
    const selectedMagnitude = useSelector((state: RootState) => {
        return state.policy.selectedMagnitude;
    }) || selectedPerilMinMagnitude;  
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateSelectedMagnitude(selectedPerilMinMagnitude));
            dispatch(updateCurrentPage("/selectMagnitude"));
        }, []
    );
    function onClickNext(event: any) {
        if(selectedMagnitude === 0) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select magnitude."));
            return;
        } 
        navigate("/selectState");
    }
    function onClickBack() {
        navigate("/selectPeril");
        dispatch(updateSelectedMagnitude(null));
    }

    function onClickRangeChange(event: any) {
        dispatch(updateSelectedMagnitude(event.target.value));
    }

    return (    
        <div className='column2'>
            <form className="flexContainer" onSubmit={(e)=>{
                e.preventDefault();
                onClickNext(e);
            }}>
                <h1>Select a Magnitude to insure for {selectedPeril}</h1>
                <label htmlFor="magnitudePicker">Magnitude={selectedMagnitude}</label>
                <input type="range" id="magnitudePicker" name="monthPicker" value={selectedMagnitude}
                onChange={onClickRangeChange}
                min={selectedPerilMinMagnitude} max={selectedPerilMaxMagnitude}/>
                <button name="action" type="submit">Next</button>
                <button onClick={onClickBack} type="button">Back</button>
            </form>
        </div> 
    );
};