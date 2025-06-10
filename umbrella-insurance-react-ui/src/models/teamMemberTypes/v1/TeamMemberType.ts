export class TeamMemberType {
    id?: number;
    teamMemberTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.teamMemberTypeName) {
                this.teamMemberTypeName = obj.teamMemberTypeName;
            }
        }
    }
}