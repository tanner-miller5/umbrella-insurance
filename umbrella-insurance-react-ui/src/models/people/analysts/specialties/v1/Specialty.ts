export class Specialty {
    id?: number;
    specialtyName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.specialtyName) {
                this.specialtyName = obj.specialtyName;
            }
        }
    }
}