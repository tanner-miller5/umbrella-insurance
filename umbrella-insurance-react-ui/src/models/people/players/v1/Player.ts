import { Person } from "../../v1/Person";
import { GameType } from "../../../gameTypes/v1/GameType";

export class Player {
    id?: number;
    gameType?: GameType;
    height?: string;
    weight?: string;
    college?: string;
    draftInfo?: string;
    playerStatus?: string;
    jerseyNumber?: string;
    playerPosition?: string;
    experience?: string;
    birthPlace?: string;
    person?: Person;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.person) {
                this.person = new Person(obj.person);
            }
            if(obj?.gameType) {
                this.gameType = new GameType(obj.gameType);
            }
            if(obj?.height) {
                this.height = obj.height;
            }
            if(obj?.weight) {
                this.weight = obj.weight;
            }
            if(obj?.college) {
                this.college = obj.college;
            }
            if(obj?.draftInfo) {
                this.draftInfo = obj.draftInfo;
            }
            if(obj?.playerStatus) {
                this.playerStatus = obj.playerStatus;
            }
            if(obj?.jerseyNumber) {
                this.jerseyNumber = obj.jerseyNumber;
            }
            if(obj?.playerPosition) {
                this.playerPosition = obj.playerPosition
            }
            if(obj?.experience) {
                this.experience = obj.experience;
            }
            if(obj?.birthPlace) {
                this.birthPlace = obj.birthPlace;
            }
        }
    }
}