import { TournamentType } from "../tournamentTypes/v1/TournamentType";

export class Tournament {
    id?: number;
    tournamentName?: string;
    tournamentType?: TournamentType;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.tournamentName) {
                this.tournamentName = obj.tournamentName;
            }
            if(obj?.tournamentType) {
                this.tournamentType = new TournamentType(obj.tournamentType);
            }
        }
    }
}