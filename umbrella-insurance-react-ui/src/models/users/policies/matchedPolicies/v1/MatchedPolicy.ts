import { Unit } from "../../../../units/v1/Unit";
import { Fee } from "../../../fees/v1/Fee";
import { PendingPolicy } from "../../pendingPolicies/v1/PendingPolicy";
import { MatchedPolicyState } from "../matchedPolicyStates/v1/MatchedPolicyState";

export class MatchedPolicy {
    id?: number;
    createdDateTime?: string
    pendingInsuredPolicy?: PendingPolicy;
    pendingInsurerPolicy?: PendingPolicy;
    premium?: number;
    coverageAmount?: number;
    matchedPolicyState?: MatchedPolicyState;
    insuredFee?: Fee;
    insurerFee?: Fee;
    impliedProbability?: number;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.pendingInsuredPolicy) {
                this.pendingInsuredPolicy = new PendingPolicy(obj.pendingInsuredPolicy);
            }
            if(obj?.pendingInsurerPolicy) {
                this.pendingInsurerPolicy = new PendingPolicy(obj.pendingInsurerPolicy);
            }
            if(obj?.premium) {
                this.premium = obj.premium;
            }
            if(obj?.coverageAmount) {
                this.coverageAmount = obj.coverageAmount;
            }
            if(obj?.matchedPolicyState) {
                this.matchedPolicyState = new MatchedPolicyState(obj.matchedPolicyState);
            }
            if(obj?.insuredFee) {
                this.insuredFee = new Fee(obj.insuredFee);
            }
            if(obj?.insurerFee) {
                this.insurerFee = new Fee(obj.insurerFee);
            }
            if(obj?.impliedProbability) {
                this.impliedProbability = obj.impliedProbability;
            }
        }
    }
}