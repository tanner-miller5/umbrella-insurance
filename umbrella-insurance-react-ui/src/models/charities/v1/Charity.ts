export class Charity {
    id?: number;
    charityName?: string;

    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj?.id;
            }
            if(obj?.charityName) {
                this.charityName = obj?.charityName;
            }
        }
    }
}