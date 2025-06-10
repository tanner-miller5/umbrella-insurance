export class VerifyOtpResponse {
    isOtpCorrect?: boolean;
    constructor(obj: any) {
        if(obj) {
            if(typeof obj.isOtpCorrect === 'boolean') {
                this.isOtpCorrect = obj.isOtpCorrect;
            }
        }
    }
}