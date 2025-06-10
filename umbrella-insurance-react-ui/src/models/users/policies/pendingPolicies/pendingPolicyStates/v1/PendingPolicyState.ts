export class PendingPolicyState {
    id?: number;
    pendingPolicyStateName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.pendingPolicyStateName) {
                this.pendingPolicyStateName = obj.pendingPolicyStateName;
            }
        }
    }
}