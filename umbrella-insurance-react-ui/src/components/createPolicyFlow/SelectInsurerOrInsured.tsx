import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateCurrentPage } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updatePolicyFor } from "../../redux/reducers/PolicyReducer";
import { useNavigate } from "react-router-dom";

export default function SelectInsurerOrInsured(){
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
            dispatch(updateCurrentPage("/insurerOrInsured"));
        }, []
    );
    function onClickInsurer() {
        dispatch(updatePolicyFor("Insurer"));
        navigate("/selectPeril");
    }
    function onClickInsured() {
        dispatch(updatePolicyFor("Insured"));
        navigate("/selectPeril");
    }
    function onClickBack() {
        navigate("/createPolicy");
    }


    return (    
            <div className='column2'>
                <h1>Are you looking to insure or be insured?</h1>
                <button onClick={onClickInsurer}>Insurer</button>
                <button onClick={onClickInsured}>Insured</button>
                <button onClick={onClickBack}>Back</button>
            </div> 
    );
};