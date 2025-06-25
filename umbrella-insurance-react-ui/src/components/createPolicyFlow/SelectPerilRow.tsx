import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateSelectedPeril, updateSelectedPerilMaxMagnitude, updateSelectedPerilMinMagnitude } from "../../redux/reducers/PolicyReducer";
import { RootState } from "../../redux/store/Store";

interface SelectPerilRowProps {
    perilName: string;
    description?: string;
    scaleName?: string;
    minMagnitude?: number;
    maxMagnitude?: number;
}
export default function SelectPerilRow({perilName,
    description,
    scaleName,
    minMagnitude,
    maxMagnitude
    }:SelectPerilRowProps){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const selectedPeril = useSelector((state: RootState) => {
        return state.policy.selectedPeril;
    });

    function onClickPeril(event: any) {
        dispatch(updateSelectedPeril(perilName));
        dispatch(updateSelectedPerilMinMagnitude(minMagnitude));
        dispatch(updateSelectedPerilMaxMagnitude(maxMagnitude));
        navigate("/selectMagnitude");
    }
    const perilClassName = selectedPeril === perilName ? 'liActive' : "";

    return (    
        <div>
            <form className="flexContainer" onSubmit={(e)=>{
                e.preventDefault();
                onClickPeril(e);
            }}>
                <button className={perilClassName} name="action" type="submit">
                    {perilName} <br/>
                    {description}<br/>
                    Scale Name: {scaleName}<br/>
                    Min Magnitude: {minMagnitude}<br/>
                    Max Magnitude: {maxMagnitude}
                </button>
            </form>
        </div>);
}