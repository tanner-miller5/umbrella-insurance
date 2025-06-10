export class Company {
    id?: number;
    companyName?: string;
    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.companyName) {
                this.companyName = obj.companyName;
            }
        }
    }
}