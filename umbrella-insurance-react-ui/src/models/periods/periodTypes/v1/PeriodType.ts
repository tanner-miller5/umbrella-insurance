export class PeriodType {
    id?: number;
    periodTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.periodTypeName) {
                this.periodTypeName = obj.periodTypeName;
            }
        }
    }
}