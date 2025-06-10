import { Player } from "../../../people/players/v1/Player";
import { Season } from "../../../seasons/v1/Season";
import { Record } from "../../v1/Record";

export class PlayerRecord {
    id?: number;
    player?: Player;
    record?: Record;
    season?: Season;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.player) {
                this.player = new Player(obj.player);
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