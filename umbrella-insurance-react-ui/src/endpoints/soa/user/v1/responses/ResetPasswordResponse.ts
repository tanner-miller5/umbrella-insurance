export class ResetPasswordResponse {
    isSuccess?: boolean;
    constructor(obj: any) {
        if(obj) {
            if(typeof obj.isSuccess === 'boolean') {
                this.isSuccess = obj.isSuccess;
            }
        }
    }
}