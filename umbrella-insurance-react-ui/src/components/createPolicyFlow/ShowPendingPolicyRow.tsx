import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateSelectedPeril, updateSelectedPerilMaxMagnitude, updateSelectedPerilMinMagnitude } from "../../redux/reducers/PolicyReducer";
import { RootState } from "../../redux/store/Store";
import { PendingPolicy } from "../../models/users/policies/pendingPolicies/v1/PendingPolicy";

interface ShowPendingPolicyRowProps {
    pendingPolicy: PendingPolicy;
}
export default function ShowPendingPolicyRow({
    pendingPolicy
    }:ShowPendingPolicyRowProps){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const selectedPeril = useSelector((state: RootState) => {
        return state.policy.selectedPeril;
    });

    function onClickPolicy(event: any) {

    }
    const pendingPolicyClassName = "";


    return (
        <tr className={pendingPolicyClassName} >
            <td>{pendingPolicy.peril?.perilName}</td>
            <td>{pendingPolicy.startDate}</td>
            <td>{pendingPolicy.endDate}</td>
            <td>{pendingPolicy.coverageAmount}</td>
            <td>{pendingPolicy.premiumAmount}</td>
            <td>{pendingPolicy?.location?.city?.cityName}</td>
            <td>{pendingPolicy?.location?.state?.stateName}</td>
            <td>{((pendingPolicy.impliedProbability || 0) * 100).toFixed(2)}%</td>
            <td>{pendingPolicy?.unit?.unitName}</td>
            <td>{pendingPolicy?.pendingPolicyState?.pendingPolicyStateName}</td>
        </tr>);
}