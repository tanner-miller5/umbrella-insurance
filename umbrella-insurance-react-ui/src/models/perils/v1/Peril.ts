export class Peril {
    id?: number;
    perilName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.perilName) {
                this.perilName = obj.perilName;
            }
        }
    }
}