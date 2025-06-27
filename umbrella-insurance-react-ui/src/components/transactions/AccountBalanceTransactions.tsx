import { useEffect } from 'react';
import { updateCurrentPage, updatePerils } from '../../redux/reducers/AppReducer';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';
import { useNavigate } from 'react-router-dom';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import AccountBalanceTransactionRow from './AccountBalanceTransactionRow';
import { callReadPendingPolicyRestEndpointsByUserId } from '../../endpoints/rest/users/policies/pendingPolicies/v1/PendingPolicyRestEndpoints';
import Loading from '../loadings/Loading';
import { updateAccountBalanceTransactions } from '../../redux/reducers/AccountBalanceTransactionReducer';
import { callReadAccountBalanceTransactionRestEndpointsByUserId } from '../../endpoints/rest/users/accountBalances/accountBalanceTransactions/v1/AccountBalanceTransactionRestEndpoints';

export default function AccountBalanceTransactions() {
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
    const accountBalanceTransactions = useSelector((state: RootState) => {
        return state.accountBalanceTransaction.accountBalanceTransactions;
    }) || [];
    let isLoadingOpen: boolean = useSelector((state: RootState)=>{
        return state.loading.value;
    });
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/accountBalanceTransactions"));
        }, []
    );

    useEffect(
        function() {
            async function getAccountBalanceTransactions() {
                dispatch(updateLoadingState(true));
                //if(pendingPolicies.length === 0) {
                    const accountBalanceTransactionsResponse = await callReadAccountBalanceTransactionRestEndpointsByUserId(
                        session, userId, env, domain);
                    if(accountBalanceTransactionsResponse) {
                        dispatch(updateAccountBalanceTransactions(toObject(accountBalanceTransactionsResponse)));
                    }
                //}
                dispatch(updateLoadingState(false));
            };
            getAccountBalanceTransactions();
        }, []
    )
    const rows = [];
    for (let i = 0; i < accountBalanceTransactions.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<AccountBalanceTransactionRow key={i} 
            accountBalanceTransaction={accountBalanceTransactions[i]} 

            />);
    }

    if(isLoadingOpen) {
        return (
            <Loading />
        );
    }
    return (    
        <div className='column2'>
            <h1>Account Balance Transactions</h1>
            <table>
                <thead>
                    <tr>
                        <th>Account Balance Transaction Name</th>
                        <th>Account Balance Transaction Status Name</th>
                        <th>Account Balance Transaction Type Name</th>
                        <th>Amount</th>
                        <th>Premium Amount</th>
                        <th>Created Date Time</th>
                        <th>Unit Name</th>
                    </tr>
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </table>
        </div> 
    );
};