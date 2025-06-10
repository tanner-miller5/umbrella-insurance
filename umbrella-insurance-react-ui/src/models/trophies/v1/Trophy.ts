export class Trophy {
    id?: number;
    trophyName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.trophyName) {
                this.trophyName = obj.trophyName;
            }
        }
    }
}