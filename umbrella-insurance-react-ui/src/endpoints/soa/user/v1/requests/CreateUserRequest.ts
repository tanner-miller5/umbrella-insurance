export class CreateUserRequest {
    emailAddress?: string;
    phoneNumber?: string;
    username?: string;
    firstName?: string;
    middleName?: string;
    surname?: string;
    dateOfBirth?: string;
    ssn?: string;
    password?: string;
    twoFactorMethod?: string;
    consentedToEmails?: boolean;
    consentedToTexts?: boolean;
    consentedToTermsAndConditions?: boolean;

    constructor(obj?: any) {
        if (obj) {
            if (obj?.emailAddress) {
                this.emailAddress = obj.emailAddress;
            }
            if (obj?.phoneNumber) {
                this.phoneNumber = obj.phoneNumber;
            }
            if (obj?.username) {
                this.username = obj.username;
            }
            if (obj?.firstName) {
                this.firstName = obj.firstName;
            }
            if (obj?.middleName) {
                this.middleName = obj.middleName;
            }
            if (obj?.surname) {
                this.surname = obj.surname;
            }
            if (obj?.dateOfBirth) {
                this.dateOfBirth = obj.dateOfBirth;
            }
            if (obj?.ssn) {
                this.ssn = obj.ssn;
            }
            if (obj?.password) {
                this.password = obj.password;
            }
            if (obj?.twoFactorMethod) {
                this.twoFactorMethod = obj.twoFactorMethod;
            }
            if (obj?.consentedToEmails) {
                this.consentedToEmails = obj.consentedToEmails;
            }
            if (obj?.consentedToTexts) {
                this.consentedToTexts = obj.consentedToTexts;
            }
            if (obj?.consentedToTermsAndConditions) {
                this.consentedToTermsAndConditions = obj.consentedToTermsAndConditions;
            }
        }
    }
}