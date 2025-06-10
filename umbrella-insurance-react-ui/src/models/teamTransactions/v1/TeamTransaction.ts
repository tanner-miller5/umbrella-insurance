import { Team } from "../../teams/v1/Team";
import { TeamTransactionType } from "../teamTransactionTypes/v1/TeamTransactionType";

export class TeamTransaction {
    id?: number;
    dateOfTeamTransaction?: string;
    teamTransactionType?: TeamTransactionType;
    description?: string;
    team?: Team;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.dateOfTeamTransaction) {
                this.dateOfTeamTransaction = obj.dateOfTeamTransaction;
            }
            if(obj?.teamTransactionType) {
                this.teamTransactionType = new TeamTransactionType(obj.teamTransactionType);
            }
            if(obj?.description) {
                this.description = obj.description;
            }
            if(obj?.team) {
                this.team = new Team(obj.team);
            }
        }
    }
}