import { GameStatusEnum } from "./GameStatusEnum";

export class GameStatus {
    id?: number;
    gameStatusName?: string;
    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.gameStatusName) {
                this.gameStatusName = obj.gameStatusName;
            }
        }
    }
}