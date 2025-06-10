export class PeriodStatus {
    id?: number;
    periodStatusName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.periodStatusName) {
                this.periodStatusName = obj.periodStatusName;
            }
        }
    }
}