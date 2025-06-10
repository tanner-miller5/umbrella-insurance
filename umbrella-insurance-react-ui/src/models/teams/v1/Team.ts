import { GameType } from "../../gameTypes/v1/GameType";
import { LevelOfCompetition } from "../../levelOfCompetitions/v1/LevelOfCompetition";
import { Season } from "../../seasons/v1/Season";
import { Location } from "../../geographies/locations/v1/Location";
import { TeamType } from "../teamTypes/v1/TeamType";

export class Team {
    id?: number;
    teamName?: string;
    teamType?: TeamType;
    location?: Location;
    firstSeason?: Season;
    lastSeason?: Season;
    logoName?: string;
    logoImage?: number[];
    levelOfCompetition?: LevelOfCompetition;
    gameType?: GameType;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.teamName) {
                this.teamName = obj.teamName;
            }
            if(obj?.teamType) {
                this.teamType = new TeamType(obj.teamType);
            }
            if(obj?.location) {
                this.location = new Location(obj.location);
            }
            if(obj?.firstSeason) {
                this.firstSeason = new Season(obj.firstSeason);
            }
            if(obj?.lastSeason) {
                this.lastSeason = new Season(obj.lastSeason);
            }
            if(obj?.logoName) {
                this.logoName = obj.logoName;
            }
            if(obj?.logoImage) {
                this.logoImage = obj.logoImage;
            }
            if(obj?.levelOfCompetition) {
                this.levelOfCompetition = new LevelOfCompetition(obj.levelOfCompetition);
            }
            if(obj?.gameType) {
                this.gameType = new GameType(obj.gameType);
            }
        }
    }
}