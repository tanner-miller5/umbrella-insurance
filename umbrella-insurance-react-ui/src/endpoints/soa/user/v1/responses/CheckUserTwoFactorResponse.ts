export class CheckUserTwoFactorResponse {
    twoFactorAuthType?: string;
    constructor(obj: any) {
        if(obj) {
            if(obj.twoFactorAuthType) {
                this.twoFactorAuthType = obj.twoFactorAuthType;
            }
        }
    }
}