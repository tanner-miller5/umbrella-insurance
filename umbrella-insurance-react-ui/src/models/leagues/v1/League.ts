import { GameType } from "../../gameTypes/v1/GameType";

export class League {
    id?: number;
    leagueName?: string;
    gameType?: GameType;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.leagueName) {
                this.leagueName = obj.leagueName;
            }
            if(obj?.gameType) {
                this.gameType = new GameType(obj.gameType);
            }
        }
    }
}