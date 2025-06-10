export class Referral {
    id?: number;
    referralName?: string;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.referralName) {
                this.referralName = obj.referralName;
            }
        }
    }
}