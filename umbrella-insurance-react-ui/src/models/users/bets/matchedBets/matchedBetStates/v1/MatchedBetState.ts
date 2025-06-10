import { MatchedBetStateEnum } from "./MatchedBetStateEnum";

export class MatchedBetState {
    id?: number;
    matchedBetStateName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.matchedBetStateName) {
                this.matchedBetStateName = obj.matchedBetStateName;
            }
        }
    }
}