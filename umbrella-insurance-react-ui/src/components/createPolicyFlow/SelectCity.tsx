import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updatePolicyFor } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";

export default function SelectState(){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/selectCity"));
        }, []
    );
    function onClickSelectState() {
        navigate("/");
    }
    function onClickBack() {
        navigate("/selectState");
    }


    return (    
        <div className='column2'>
            <h1>Select City</h1>
            <button onClick={onClickSelectState}>Start</button>
            <button onClick={onClickBack}>Back</button>
        </div> 
    );
};