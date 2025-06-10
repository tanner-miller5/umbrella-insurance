import { ApplicationRole } from "../../../../../models/applicationRoles/v1/ApplicationRole";
import { Cart } from "../../../../../models/carts/v1/Cart";
import { User } from "../../../../../models/users/v1/User";

export class SignInResponse {
    username?: string;
    emailAddress?: string;
    phoneNumber?: string;
    firstName?: string;
    lastName?: string;
    consentedToEmails?: boolean;
    consentedToTexts?: boolean;
    twoFactorAuthType?: string;
    userAgent?: string;
    dateOfBirth?: string;
    escrowPlayAmount?: number;
    escrowRealAmount?: number;
    playAccountValue?: number;
    realAccountValue?: number;
    sessionCode?: string;
    isEmailAddressVerified?: boolean;
    isPhoneNumberVerified?: boolean;
    authAppDataUrl?: string;
    isAuthAppVerified?: boolean;
    applicationRoles?: ApplicationRole[];
    endDateTime?: string;
    user?: User;
    backendAppVersion?: string;
    cart?: Cart;
    constructor(obj?: any) {
        if (obj) {
            if (obj?.username) {
                this.username = obj.username;
            }
            if (obj?.emailAddress) {
                this.emailAddress = obj.emailAddress;
            }
            if (obj?.phoneNumber) {
                this.phoneNumber = obj.phoneNumber;
            }
            if (obj?.emailAddress) {
                this.emailAddress = obj.emailAddress;
            }
            if (obj?.phoneNumber) {
                this.phoneNumber = obj.phoneNumber;
            }
            if (obj?.firstName) {
                this.firstName = obj.firstName;
            }
            if (obj?.lastName) {
                this.lastName = obj.lastName;
            }
            if (typeof obj?.consentedToEmails === 'boolean') {
                this.consentedToEmails = obj.consentedToEmails;
            }
            if (typeof obj?.consentedToTexts === 'boolean') {
                this.consentedToTexts = obj.consentedToTexts;
            }
            if (obj?.twoFactorAuthType) {
                this.twoFactorAuthType = obj.twoFactorAuthType;
            }
            if (obj?.userAgent) {
                this.userAgent = obj.userAgent;
            }
            if (obj?.dateOfBirth) {
                this.dateOfBirth = obj.dateOfBirth;
            }
            if (obj?.escrowPlayAmount) {
                this.escrowPlayAmount = obj.escrowPlayAmount;
            }
            if (obj?.escrowRealAmount) {
                this.escrowRealAmount = obj.escrowRealAmount;
            }
            if (obj?.playAccountValue) {
                this.playAccountValue = obj.playAccountValue;
            }
            if (obj?.realAccountValue) {
                this.realAccountValue = obj.realAccountValue;
            }
            if (obj?.sessionCode) {
                this.sessionCode = obj.sessionCode;
            }
            if (typeof obj?.isEmailAddressVerified === 'boolean') {
                this.isEmailAddressVerified = obj.isEmailAddressVerified;
            }
            if (typeof obj?.isPhoneNumberVerified === 'boolean') {
                this.sessionCode = obj.sessionCode;
            }
            if (typeof obj?.isAuthAppVerified === 'boolean') {
                this.isAuthAppVerified = obj.isAuthAppVerified;
            }
            if (obj?.applicationRoles) {
                let applicationRoles: ApplicationRole[] = [];
                for(let i = 0; i < obj?.applicationRoles.length; i++) {
                    applicationRoles.push(new ApplicationRole(obj?.applicationRoles[i]));
                }
                this.applicationRoles = applicationRoles;
            }
            if (obj?.endDateTime) {
                this.endDateTime = obj.endDateTime;
            }
            if (obj?.user) {
                this.user = new User(obj.user);
            }
            if (obj?.backendAppVersion) {
                this.backendAppVersion = obj.backendAppVersion;
            }
            if (obj?.cart) {
                this.cart = new Cart(obj.cart);
            }
        }
    }

    
}