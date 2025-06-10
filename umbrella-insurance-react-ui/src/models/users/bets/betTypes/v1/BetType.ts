export class BetType {
    id?: number;
    betTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.betTypeName) {
                this.betTypeName = obj.betTypeName;
            }
        }
    }
}