import { Conference } from "../../v1/Conference";

export class Division {
    id?: number;
    divisionName?: string;
    conference?: Conference;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.divisionName) {
                this.divisionName = obj.divisionName;
            }
            if(obj?.conference) {
                this.conference = new Conference(obj.conference);
            }
        }
    }
}