export class Continent {
    id?: number;
    continentName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.continentName) {
                this.continentName = obj.continentName;
            }
        }
    }
}