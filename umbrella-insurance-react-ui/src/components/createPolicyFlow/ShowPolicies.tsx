import { useEffect } from 'react';
import { updateCurrentPage, updatePerils } from '../../redux/reducers/AppReducer';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';
import { useNavigate } from 'react-router-dom';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { updatePendingPolicies } from '../../redux/reducers/UserReducer';
import ShowPendingPolicyRow from './ShowPendingPolicyRow';
import { callReadPendingPolicyRestEndpointsByUserId } from '../../endpoints/rest/users/policies/pendingPolicies/v1/PendingPolicyRestEndpoints';
import Loading from '../loadings/Loading';

export default function ShowPolicies() {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const session = useSelector((state: RootState) => {
        return state.user.sessionCode;
    }) || "";
    const userId = useSelector((state: RootState) => {
        return state.user.userId;
    }) || 0;
    const pendingPolicies = useSelector((state: RootState) => {
        return state.user.pendingPolicies;
    }) || [];
    const matchedPolicies = useSelector((state: RootState) => {
        return state.user.matchedPolicies;
    }) || [];
    let isLoadingOpen: boolean = useSelector((state: RootState)=>{
        return state.loading.value;
    });
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/showPolicies"));
        }, []
    );

    useEffect(
        function() {
            async function getPendingPolicies() {
                dispatch(updateLoadingState(true));
                //if(pendingPolicies.length === 0) {
                    const pendingPoliciesResponse = await callReadPendingPolicyRestEndpointsByUserId(
                        userId, session, env, domain);
                    if(pendingPoliciesResponse) {
                        dispatch(updatePendingPolicies(toObject(pendingPoliciesResponse)));
                    }
                //}
                dispatch(updateLoadingState(false));
            };
            getPendingPolicies();
        }, []
    )
    const rows = [];
    for (let i = 0; i < pendingPolicies.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<ShowPendingPolicyRow key={i} 
            pendingPolicy={pendingPolicies[i]} 

            />);
    }

    if(isLoadingOpen) {
        return (
            <Loading />
        );
    }
    return (    
        <div className='column2'>
            <h1>Policies</h1>
            <table>
                <thead>
                    <tr>
                        <th>Peril Name</th>
                        <th>Coverage Start Date</th>
                        <th>Coverage End Date</th>
                        <th>Coverage Amount</th>
                        <th>Premium Amount</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Implied Probability</th>
                        <th>Unit</th>
                        <th>Pending Policy State</th>
                    </tr>
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </table>
        </div> 
    );
};