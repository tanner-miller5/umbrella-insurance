export class VerificationMethod {
    id?: number;
    verificationMethodName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.verificationMethodName) {
                this.verificationMethodName = obj.verificationMethodName;
            }
        }
    }
}