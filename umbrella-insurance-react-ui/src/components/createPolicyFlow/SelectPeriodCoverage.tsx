import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateEndPolicyMonthAndYear, updatePolicyFor, updateStartPolicyMonthAndYear } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";

export default function SelectPeriodCoverage(){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const startPolicyMonthAndYear = useSelector((state: RootState) => {
        return state.policy.startPolicyMonthAndYear;
    }) || "";    
    const endPolicyMonthAndYear = useSelector((state: RootState) => {
        return state.policy.endPolicyMonthAndYear;
    }) || "";
    useEffect(
        function() {
            dispatch(updateCurrentPage("/selectPeriodCoverage"));
        }, []
    );
    function onClickNext(event: any) {
        if(endPolicyMonthAndYear <= startPolicyMonthAndYear) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("End coverage period needs to be after start coverage period."));
            return;
        } else if(endPolicyMonthAndYear === "") {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select a end coverage period."));
            return;
        } else if(startPolicyMonthAndYear === "") {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select a start coverage period."));
            return;
        }
        navigate("/");
    }
    function onClickBack() {
        navigate("/selectCity");
    }
    const currentMonth = new Date().getMonth() + 1
    let currentMonthString = currentMonth.toString();
    if(currentMonthString.length === 1) {
        currentMonthString = "0" + currentMonthString;
    }
    const currentYear = new Date().getFullYear();
    const nextYear = currentYear + 1;
    const minStart = currentYear + "-" + currentMonthString;
    const maxStart = nextYear + "-" + currentMonthString;
    let minEnd = currentYear + "-" + currentMonthString;
    const maxEnd = nextYear + "-" + currentMonthString;
    if(startPolicyMonthAndYear != "") {
        minEnd = startPolicyMonthAndYear;
    }

    function onClickStartPolicyMonthAndYear(event: any) {
        dispatch(updateStartPolicyMonthAndYear(event.target.value));
    }
    function onClickEndPolicyMonthAndYear(event: any) {
        dispatch(updateEndPolicyMonthAndYear(event.target.value));
    }

    return (    
        <div className='column2'>
            <form className="flexContainer" onSubmit={(e)=>{
                e.preventDefault();
                onClickNext(e);
            }}>
                <h1>Select Period Coverage</h1>
                <label htmlFor="startMonthPicker">Select Start Coverage Period Month and Year:</label>
                <input type="month" id="startMonthPicker" name="monthPicker" value={startPolicyMonthAndYear}
                onChange={onClickStartPolicyMonthAndYear}
                min={minStart} max={maxStart}/>
                <label htmlFor="endMonthPicker">Select End Coverage Period Month and Year:</label>
                <input type="month" id="endMonthPicker" name="monthPicker" value={endPolicyMonthAndYear}
                onChange={onClickEndPolicyMonthAndYear}
                min={minEnd} max={maxEnd}/>
                <button name="action" type="submit">Next</button>
                <button onClick={onClickBack}>Back</button>
            </form>
        </div> 
    );
};