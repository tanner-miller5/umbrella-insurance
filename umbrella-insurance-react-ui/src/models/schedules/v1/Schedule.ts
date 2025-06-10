import { Game } from "../../games/v1/Game";
import { Season } from "../../seasons/v1/Season";

export class Schedule {
    id?: number;
    season?: Season;
    game?: Game;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.season) {
                this.season = new Season(obj.season);
            }
            if(obj?.game) {
                this.game = new Game(obj.game);
            }
        }
    }
}