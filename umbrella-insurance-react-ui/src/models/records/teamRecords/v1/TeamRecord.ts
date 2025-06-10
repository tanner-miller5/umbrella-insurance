import { Season } from "../../../seasons/v1/Season";
import { Team } from "../../../teams/v1/Team";
import { Record } from "../../v1/Record";

export class TeamRecord {
    id?: number;
    team?: Team;
    record?: Record;
    season?: Season;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.team) {
                this.team = new Team(obj.team);
            }
            if(obj?.record) {
                this.record = new Record(obj.record);
            }
            if(obj?.season) {
                this.season = new Season(obj.season);
            }
        }
    }
}