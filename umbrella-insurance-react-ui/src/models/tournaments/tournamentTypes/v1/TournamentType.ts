export class TournamentType {
    id?: number;
    tournamentTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.tournamentTypeName) {
                this.tournamentTypeName = obj.tournamentTypeName;
            }
        }
    }
}