export class ChangeTwoFactorAuthResponse {
    isSuccess?: boolean;
    authAppDataUrl?: string;
    constructor(obj: any) {
        if(obj) {
            if(typeof obj.isSuccess === 'boolean') {
                this.isSuccess = obj.isSuccess;
            }
            if(obj.authAppDataUrl) {
                this.authAppDataUrl = obj.authAppDataUrl;
            }
        }
    }
}