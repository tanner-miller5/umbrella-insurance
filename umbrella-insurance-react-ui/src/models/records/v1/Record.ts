import { Season } from "../../seasons/v1/Season";

export class Record {
    id?: number;
    wins?: number;
    ties?: number;
    losses?: number;
    recordName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.wins) {
                this.wins = obj.wins;
            }
            if(obj?.ties) {
                this.ties = obj.ties;
            }
            if(obj?.losses) {
                this.losses = obj.losses;
            }
            if(obj?.recordName) {
                this.recordName = obj.recordName;
            }
        }
    }
}