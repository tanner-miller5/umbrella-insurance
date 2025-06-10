export class PendingBetState {
    id?: number;
    pendingBetStateName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.pendingBetStateName) {
                this.pendingBetStateName = obj.pendingBetStateName;
            }
        }
    }
}