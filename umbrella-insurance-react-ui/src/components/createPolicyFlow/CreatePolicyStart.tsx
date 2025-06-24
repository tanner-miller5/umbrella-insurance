import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updatePolicyFor } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";

export default function CreatePolicy(){
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
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/createPolicy"));
        }, []
    );
    function onClickCreatePolicy() {
        navigate("/selectInsurerOrInsured");
    }


    return (    
        <div className='column2'>
            <h1>Create Policy</h1>
            <button onClick={onClickCreatePolicy}>Start</button>

        </div> 
    );
};