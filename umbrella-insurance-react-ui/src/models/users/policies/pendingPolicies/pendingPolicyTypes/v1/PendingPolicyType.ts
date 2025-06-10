export class PendingPolicyType {
    id?: number;
    pendingPolicyTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.pendingPolicyTypeName) {
                this.pendingPolicyTypeName = obj.pendingPolicyTypeName;
            }
        }
    }
}