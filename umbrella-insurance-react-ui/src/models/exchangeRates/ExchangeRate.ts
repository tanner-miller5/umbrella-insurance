import { Unit } from "../units/v1/Unit";

export class ExchangeRate {
    id?: number;
    unit1?: Unit;
    unit2?: Unit;
    unit1ToUnit2Ratio?: number;
    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.unit1) {
                this.unit1 = new Unit(obj.unit1);
            }
            if(obj?.unit2) {
                this.unit2 = new Unit(obj.unit2);
            }
            if(obj?.unit1ToUnit2Ratio) {
                this.unit1ToUnit2Ratio = obj.unit1ToUnit2Ratio;
            }

        }
    }
}