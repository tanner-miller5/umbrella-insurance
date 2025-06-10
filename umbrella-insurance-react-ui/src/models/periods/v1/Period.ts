import { Game } from "../../games/v1/Game";
import { PeriodStatus } from "../periodStatuses/v1/PeriodStatus";
import { PeriodType } from "../periodTypes/v1/PeriodType";

export class Period {
    id?: number;
    game?: Game;
    periodType?: PeriodType;
    periodNumber?: number;
    periodStatus?:PeriodStatus;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.game) {
                this.game = new Game(obj.game);
            }
            if(obj?.periodType) {
                this.periodType = new PeriodType(obj.periodType);
            }
            if(obj?.periodNumber) {
                this.periodNumber = obj.periodNumber;
            }
            if(obj?.periodStatus) {
                this.periodStatus = new PeriodStatus(obj.periodStatus);
            }
        }
    }
}