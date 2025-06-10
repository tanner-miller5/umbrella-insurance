export class Holiday {
    id?: number;
    holidayName?: string;
    holidayDate?: string; //yyyy-mm-dd
    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.holidayName) {
                this.holidayName = obj.holidayName;
            }
            if(obj?.holidayDate) {
                this.holidayDate = obj.holidayDate;
            }
        }
    }
}