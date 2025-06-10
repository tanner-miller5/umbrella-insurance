export class ZipCode {
    id?: number;
    zipCodeValue?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.zipCodeValue) {
                this.zipCodeValue = obj.zipCodeValue;
            }
        }
    }
}