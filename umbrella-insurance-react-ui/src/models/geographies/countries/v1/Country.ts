export class Country {
    id?: number;
    countryName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.countryName) {
                this.countryName = obj.countryName;
            }
        }
    }
}