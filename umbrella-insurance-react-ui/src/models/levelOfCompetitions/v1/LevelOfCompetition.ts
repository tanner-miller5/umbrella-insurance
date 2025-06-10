export class LevelOfCompetition {
    id?: number;
    levelOfCompetitionName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.levelOfCompetitionName) {
                this.levelOfCompetitionName = obj.levelOfCompetitionName;
            }
        }
    }
}