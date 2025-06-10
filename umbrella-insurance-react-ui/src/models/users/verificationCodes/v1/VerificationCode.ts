
import { Session } from "../../sessions/v1/Session";
import { VerificationMethod } from "../verificationMethods/v1/VerificationMethod";

export class VerificationCode {
    id?: number;
    session?: Session;
    verificationMethod?: VerificationMethod;
    verificationCode?: string;
    isVerified?: boolean;
    expirationDateTime?: string;
    verifiedDateTime?: string;
    minutesToVerify?: number;
    maxAttempts?: number;
    currentAttempt?: number;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.session) {
                this.session = new Session(obj.session);
            }
            if(obj?.verificationMethod) {
                this.verificationMethod = new VerificationMethod(obj.verificationMethod);
            }
            if(obj?.verificationCode) {
                this.verificationCode = obj.verificationCode;
            }
            if(typeof obj?.isVerified === "boolean") {
                this.isVerified = obj.isVerified;
            }
            if(obj?.expirationDateTime) {
                this.expirationDateTime = obj.expirationDateTime;
            }
            if(obj?.verifiedDateTime) {
                this.verifiedDateTime = obj.verifiedDateTime;
            }
            if(obj?.minutesToVerify) {
                this.minutesToVerify = obj.minutesToVerify;
            }
            if(obj?.maxAttempts) {
                this.maxAttempts = obj.maxAttempts;
            }
            if(obj?.currentAttempt) {
                this.currentAttempt = obj.currentAttempt;
            }
        }
    }
}