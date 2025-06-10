export class State {
    id?: number;
    stateName?: string;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.stateName) {
                this.stateName = obj.stateName;
            }
        }
    }
}