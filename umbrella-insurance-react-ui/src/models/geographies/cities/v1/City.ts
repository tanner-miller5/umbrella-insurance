export class City {
    id?: number;
    cityName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.cityName) {
                this.cityName = obj.cityName;
            }
        }
    }
}