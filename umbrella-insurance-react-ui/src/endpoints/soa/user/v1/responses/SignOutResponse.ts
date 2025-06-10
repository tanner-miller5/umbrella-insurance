export class SignOutResponse {
    isSuccess?: boolean;
    error?: string;
    constructor(obj: any) {
        if(obj) {
            if(typeof obj.isSuccess === 'boolean') {
                this.isSuccess = obj.isSuccess;
            }
            if(obj.error) {
                this.error = obj.error;
            }
        }
    }
}