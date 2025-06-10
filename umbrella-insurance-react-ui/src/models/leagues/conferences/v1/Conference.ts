import { League } from "../../v1/League";

export class Conference {
    id?: number;
    conferenceName?: string;
    league?: League;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.conferenceName) {
                this.conferenceName = obj.conferenceName;
            }
            if(obj?.league) {
                this.league = new League(obj.league);
            }
        }
    }
}