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
            Peril:{pendingPolicy.peril?.perilName},
            Start Date:{pendingPolicy.startDate},
            End Date:{pendingPolicy.endDate},
            Coverage Amount:{pendingPolicy.coverageAmount},
            Premium Amount:{pendingPolicy.premiumAmount},
            City:{pendingPolicy?.location?.city?.cityName},
            State:{pendingPolicy?.location?.state?.stateName},
            Implied Probability:{pendingPolicy?.impliedProbability},
            Unit Name:{pendingPolicy?.unit?.unitName},
            Pending Policy State Name:{pendingPolicy?.pendingPolicyState?.pendingPolicyStateName},
        </tr>);
}