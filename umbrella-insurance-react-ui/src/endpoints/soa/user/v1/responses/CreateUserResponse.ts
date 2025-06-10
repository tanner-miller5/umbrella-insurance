import { ApplicationRole } from "../../../../../models/applicationRoles/v1/ApplicationRole";
import { User } from "../../../../../models/users/v1/User";
import { AccountBalance } from "../../../../../models/users/accountBalances/v1/AccountBalance";
import { VerificationMethod } from "../../../../../models/users/verificationCodes/verificationMethods/v1/VerificationMethod";

export class CreateUserResponse {
    user?: User;
    createdDateTime?: string;
    emailAddress?: string;
    phoneNumber?: string;
    username?: string;
    isEmailAddressVerified?: boolean;
    isPhoneNumberVerified?: boolean;
    verificationMethod?: VerificationMethod;
    authAppDataUrl?: string;
    usdAccountBalance?: AccountBalance;
    usdEscrowAccountBalance?: AccountBalance;
    butterBucksAccountBalance?: AccountBalance;
    butterBucksEscrowAccountBalance?: AccountBalance;
    sessionCode?: string;
    applicationRoles?: ApplicationRole[];
    endDateTime?: string;
    backendAppVersion?: string;
    constructor(obj?: any) {
        if (obj) {
            if (obj?.user) {
                this.user = new User(obj.user);
            }
            if (obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if (obj?.verificationMethod) {
                this.verificationMethod = new VerificationMethod(obj.verificationMethod);
            }
            if (obj?.emailAddress) {
                this.emailAddress = obj.emailAddress;
            }
            if (obj?.phoneNumber) {
                this.phoneNumber = obj.phoneNumber;
            }
            if (obj?.username) {
                this.username = obj.username;
            }
            if (typeof obj?.isEmailAddressVerified === 'boolean') {
                this.isEmailAddressVerified = obj.isEmailAddressVerified;
            }
            if (typeof obj?.isPhoneNumberVerified === 'boolean') {
                this.isPhoneNumberVerified = obj.isPhoneNumberVerified;
            }
            if (obj?.authAppDataUrl) {
                this.authAppDataUrl = obj.authAppDataUrl;
            }
            if (obj?.usdAccountBalance) {
                this.usdAccountBalance = new AccountBalance(obj.usdAccountBalance);
            }
            if (obj?.usdEscrowAccountBalance) {
                this.usdEscrowAccountBalance = new AccountBalance(obj.usdEscrowAccountBalance);
            }
            if (obj?.butterBucksAccountBalance) {
                this.butterBucksAccountBalance = new AccountBalance(obj.butterBucksAccountBalance);
            }
            if (obj?.butterBucksEscrowAccountBalance) {
                this.butterBucksEscrowAccountBalance = new AccountBalance(obj.butterBucksEscrowAccountBalance);
            }
            if (obj?.sessionCode) {
                this.sessionCode = obj.sessionCode;
            }
            if (obj?.applicationRoles) {
                this.applicationRoles = obj.applicationRoles;
            }
            if (obj?.endDateTime) {
                this.endDateTime = obj.endDateTime;
            }
            if (obj?.backendAppVersion) {
                this.backendAppVersion = obj.backendAppVersion;
            }
        }
    }
}