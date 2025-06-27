import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateSelectedPeril, updateSelectedPerilMaxMagnitude, updateSelectedPerilMinMagnitude } from "../../redux/reducers/PolicyReducer";
import { RootState } from "../../redux/store/Store";
import { PendingPolicy } from "../../models/users/policies/pendingPolicies/v1/PendingPolicy";
import { AccountBalanceTransaction } from "../../models/users/accountBalances/accountBalanceTransactions/v1/AccountBalanceTransaction";

interface AccountBalanceTransactionRowProps {
    accountBalanceTransaction: AccountBalanceTransaction;
}
export default function AccountBalanceTransactionRow({
    accountBalanceTransaction
    }:AccountBalanceTransactionRowProps){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const selectedPeril = useSelector((state: RootState) => {
        return state.policy.selectedPeril;
    });

    function onClickPolicy(event: any) {

    }
    const accountBalanceTransactionRowClassName = "";


    return (
        <tr className={accountBalanceTransactionRowClassName} >
            <td>{accountBalanceTransaction.accountBalanceTransactionName}</td>
            <td>{accountBalanceTransaction.accountBalanceTransactionStatus?.accountBalanceTransactionStatusName}</td>
            <td>{accountBalanceTransaction.accountBalanceTransactionType?.accountBalanceTransactionTypeName}</td>
            <td>{accountBalanceTransaction.amount}</td>
            <td>{accountBalanceTransaction.createdDateTime}</td>
            <td>{accountBalanceTransaction?.unit?.unitName}</td>
        </tr>);
}