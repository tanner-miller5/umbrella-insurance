export class Unit {
    id?: number;
    unitName?: string;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.unitName) {
                this.unitName = obj.unitName;
            }
        }
    }
    /*
    toJSON() {
        return {
            "id": this.id ? this.id?.toString() : null,
            "unitName": this.unitName ? this.unitName?.toString() : null,
        }
    }
        */
}