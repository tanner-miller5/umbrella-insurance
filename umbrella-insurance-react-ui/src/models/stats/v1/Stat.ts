export class Stat {
    id?: number;
    statName?: string;
    constructor(obj: any) {
        if (obj.id) {
            this.id = obj.id;
        }
        if (obj.statName) {
            this.statName = obj.statName;
        }
    }

}