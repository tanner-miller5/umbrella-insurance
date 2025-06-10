export class SignInRequest {
    username?: string;
    password?: string;
    totp?: string;
    verificationMethodId?: number;
    verificationCode?: string;
}