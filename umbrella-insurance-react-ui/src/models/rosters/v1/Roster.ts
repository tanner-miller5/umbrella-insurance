import { Person } from "../../people/v1/Person";
import { Season } from "../../seasons/v1/Season";
import { TeamMemberType } from "../../teamMemberTypes/v1/TeamMemberType";
import { Team } from "../../teams/v1/Team";

export class Roster {
    id?: number;
    team?: Team;
    person?: Person;
    season?: Season;
    rosterName?: string;
    teamMemberType?: TeamMemberType;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.team) {
                this.team = new Team(obj.team);
            }
            if(obj?.person) {
                this.person = new Person(obj.person);
            }
            if(obj?.season) {
                this.season = new Season(obj.season);
            }
            if(obj?.rosterName) {
                this.rosterName = obj.rosterName;
            }
            if(obj?.teamMemberType) {
                this.teamMemberType = new TeamMemberType(obj.teamMemberType);
            }
        }
    }
}