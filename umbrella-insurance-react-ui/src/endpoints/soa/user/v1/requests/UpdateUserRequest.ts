export class UpdateUserRequest {
    username?: string;
    emailAddress?: string;
    phoneNumber?: string;
    firstName?: string;
    lastName?: string;
    consentedToEmails?: boolean;
    consentedToTexts?: boolean;
    twoFactorAuthType?: string;
    dateOfBirth?: string;
}