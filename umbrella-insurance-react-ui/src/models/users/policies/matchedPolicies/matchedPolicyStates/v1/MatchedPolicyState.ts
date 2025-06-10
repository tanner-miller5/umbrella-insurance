export class MatchedPolicyState {
    id?: number;
    matchedPolicyStateName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.matchedPolicyStateName) {
                this.matchedPolicyStateName = obj.matchedPolicyStateName;
            }
        }
    }
}