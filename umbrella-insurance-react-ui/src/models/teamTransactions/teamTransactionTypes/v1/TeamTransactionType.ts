export class TeamTransactionType {
    id?: number;
    teamTransactionTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.teamTransactionTypeName) {
                this.teamTransactionTypeName = obj.teamTransactionTypeName;
            }
        }
    }
}