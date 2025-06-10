export class StreetAddress {
    id?: number;
    streetAddressLine1?: string;
    streetAddressLine2?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.streetAddressLine1) {
                this.streetAddressLine1 = obj.streetAddressLine1;
            }
            if(obj?.streetAddressLine2) {
                this.streetAddressLine2 = obj.streetAddressLine2;
            }
        }
    }
}