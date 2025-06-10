import { OrderType } from "../../../../orderTypes/v1/OrderType";
import { Peril } from "../../../../perils/v1/Peril";
import { Unit } from "../../../../units/v1/Unit";
import { AccountBalanceTransaction } from "../../../accountBalances/accountBalanceTransactions/v1/AccountBalanceTransaction";
import { Fee } from "../../../fees/v1/Fee";
import { Session } from "../../../sessions/v1/Session";
import { PendingPolicyState } from "../pendingPolicyStates/v1/PendingPolicyState";
import { PendingPolicyType } from "../pendingPolicyTypes/v1/PendingPolicyType";
import {Location} from "../../../../geographies/locations/v1/Location"

export class PendingPolicy {
    id?: number;
    session?: Session;
    premiumAmount?: number;
    startDate?: string;
    endDate?: string;
    coverageAmount?: number;
    impliedProbability?: number;
    unit?: Unit;//currency
    peril?: Peril;
    location?: Location;
    pendingPolicyState?: PendingPolicyState;
    fee?: Fee;
    pendingPolicyName?: string;
    splitPendingPolicy1?: PendingPolicy;
    splitPendingPolicy2?: PendingPolicy;
    originalPendingPolicy?: PendingPolicy;
    accountBalanceEscrowTransaction?: AccountBalanceTransaction;
    accountBalanceCanceledEscrowTransaction?: AccountBalanceTransaction;
    createdDateTime?: string
    canceledDateTime?: string;
    pendingPolicyType?: PendingPolicyType;
    orderType?: OrderType;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.session) {
                this.session = new Session(obj.session);
            }
            if(obj?.premiumAmount) {
                this.premiumAmount = obj.premiumAmount;
            }
            if(obj?.startDate) {
                this.startDate = obj.startDate;
            }
            if(obj?.endDate) {
                this.endDate = obj.endDate;
            }
            if(obj?.coverageAmount) {
                this.coverageAmount = obj.coverageAmount;
            }
            if(obj?.impliedProbability) {
                this.impliedProbability = obj.impliedProbability;
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
            if(obj?.peril) {
                this.peril = new Peril(obj.peril);
            }
            if(obj?.location) {
                this.location = new Location(obj.location);
            }
            if(obj?.pendingPolicyState) {
                this.pendingPolicyState = new PendingPolicyState(obj.pendingPolicyState);
            }
            if(obj?.fee) {
                this.fee = new Fee(obj.fee);
            }
            if(obj?.pendingPolicyName) {
                this.pendingPolicyName = obj.pendingPolicyName;
            }
            if(obj?.splitPendingPolicy1) {
                this.splitPendingPolicy1 = new PendingPolicy(obj.splitPendingPolicy1);
            }
            if(obj?.splitPendingPolicy2) {
                this.splitPendingPolicy2 = new PendingPolicy(obj.splitPendingPolicy2);
            }
            if(obj?.originalPendingPolicy) {
                this.originalPendingPolicy = new PendingPolicy(obj.originalPendingPolicy);
            }
            if(obj?.accountBalanceEscrowTransaction) {
                this.accountBalanceEscrowTransaction = new AccountBalanceTransaction(obj.accountBalanceEscrowTransaction);
            }
            if(obj?.accountBalanceCanceledEscrowTransaction) {
                this.accountBalanceCanceledEscrowTransaction = new AccountBalanceTransaction(obj.accountBalanceCanceledEscrowTransaction);
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.canceledDateTime) {
                this.canceledDateTime = obj.canceledDateTime;
            }
            if(obj?.pendingPolicyType) {
                this.pendingPolicyType = new PendingPolicyType(obj.pendingPolicyType);
            }
            if(obj?.orderType) {
                this.orderType = new OrderType(obj.orderType);
            }
        }
    }
}