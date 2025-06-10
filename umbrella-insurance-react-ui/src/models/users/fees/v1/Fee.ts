import { Unit } from "../../../units/v1/Unit";

export class Fee {
    id?: number;
    feePercent?: number;
    fixedFee?: number;
    unit?: Unit;//currency
    feeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.feePercent) {
                this.feePercent = obj.feePercent;
            }
            if(obj?.fixedFee) {
                this.fixedFee = obj.fixedFee;
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
            if(obj?.feeName) {
                this.feeName = obj.feeName;
            }
        }
    }
}