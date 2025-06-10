import { Schedule } from "../../schedules/v1/Schedule";

export class Week {
    id?: number;
    weekNumber?: number;
    weekTitle?: string;
    weekStartDate?: string;
    weekEndDate?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.weekNumber) {
                this.weekNumber = obj.weekNumber;
            }
            if(obj?.weekTitle) {
                this.weekTitle = obj.weekTitle;
            }
            if(obj?.weekStartDate) {
                this.weekStartDate = obj.weekStartDate;
            }
            if(obj?.weekEndDate) {
                this.weekEndDate = obj.weekEndDate;
            }
        }
    }
}