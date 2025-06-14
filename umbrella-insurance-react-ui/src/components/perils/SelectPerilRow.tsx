import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateSelectedPeril } from "../../redux/reducers/PolicyReducer";

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

    function onClickPeril() {
        dispatch(updateSelectedPeril(perilName));
        navigate("/selectState");
    }

    return (    
        <div>
            <button onClick={onClickPeril}>{perilName}: {description}<br/>
            {scaleName}: Min Magnitude = {minMagnitude} - Max Magnitude = {maxMagnitude}</button>
        </div>);
}