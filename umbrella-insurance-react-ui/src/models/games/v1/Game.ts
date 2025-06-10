import { GameType } from "../../gameTypes/v1/GameType";
import { SeasonType } from "../../seasons/seasonTypes/v1/SeasonType";
import { GameStatus } from "../gameStatuses/v1/GameStatus";
import { Location } from "../../geographies/locations/v1/Location";

export class Game {
    id?: number;
    location?: Location;
    dateTime?: string;
    gameStatus?: GameStatus;
    gameType?: GameType;
    seasonType?: SeasonType;
    gameName?: string;

    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.location) {
                this.location = new Location(obj.location);
            }
            if(obj?.dateTime) {
                this.dateTime = obj.dateTime;
            }
            if(obj?.gameStatus) {
                this.gameStatus = new GameStatus(obj.gameStatus);
            }
            if(obj?.gameType) {
                this.gameType = new GameType(obj.gameType);
            }
            if(obj?.seasonType) {
                this.seasonType = new SeasonType(obj.seasonType);
            }
            if(obj?.gameName) {
                this.gameName = obj.gameName;
            }
        }
    }
}