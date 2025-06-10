import { Fee } from "../../../fees/v1/Fee";
import { PendingBet } from "../../pendingBets/v1/PendingBet";
import { MatchedBetState } from "../matchedBetStates/v1/MatchedBetState";

export class MatchedBet {
    id?: number;
    createdDateTime?: string;
    pendingBet1?: PendingBet;
    pendingBet2?: PendingBet;
    odds1?: number;
    odds2?: number;
    wagerAmount1?: number;
    wagerAmount2?: number;
    fee1?: Fee;
    fee2?: Fee;
    matchedBetState?: MatchedBetState;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.pendingBet1) {
                this.pendingBet1 = new PendingBet(obj.pendingBet1);
            }
            if(obj?.pendingBet2) {
                this.pendingBet2 = new PendingBet(obj.pendingBet2);
            }
            if(obj?.odds1) {
                this.odds1 = obj.odds1;
            }
            if(obj?.odds2) {
                this.odds2 = obj.odds2;
            }
            if(obj?.wagerAmount1) {
                this.wagerAmount1 = obj.wagerAmount1;
            }
            if(obj?.wagerAmount2) {
                this.wagerAmount2 = obj.wagerAmount2;
            }
            if(obj?.fee1) {
                this.fee1 = new Fee(obj.fee1);
            }
            if(obj?.fee2) {
                this.fee2 = new Fee(obj.fee2);
            }
            if(obj?.matchedBetState) {
                this.matchedBetState = new MatchedBetState(obj.matchedBetState);
            }
        }
    }
}