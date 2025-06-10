import { GameType } from "../../../../v1/GameType";

export class Soccer extends GameType {
    static NUMBER_OF_TEAM_MEMBERS?: number = 11;
    static IS_MIXED_SEX_COMPETITION?:boolean = false;
    static IS_CONTACT?:boolean = true;
    static IS_TEAM?:boolean = true;
    static TYPE?: string = "ball";
    static equipment?:string[]  = ["soccer ball", "shin pads"];
    static VENUE?:String = "soccer field";
    static NUMBER_OF_TEAMS?: number = 2;
    static HAS_COIN_TOSS?: boolean = true;
}