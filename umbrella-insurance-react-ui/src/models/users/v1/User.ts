import { Person } from "../../people/v1/Person";

export class User {
    id?: number;
    createdDateTime?: string;
    person?: Person;
    emailAddress?: string;
    phoneNumber?: string;
    username?: string;
    isEmailAddressVerified?: boolean;
    isPhoneNumberVerified?: boolean;

    constructor(obj?: any) {
        if (obj) {
            if (obj?.id) {
                this.id = obj.id;
            }
            if (obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if (obj?.person) {
                this.person = new Person(obj.person);
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
        }
    }
    /*
    toJSON() {
        return {
            "id": this.id ? this.id?.toString() : null,
            "createdDateTime": this.createdDateTime ? this.createdDateTime?.toString() : null,
            "person": this.person ? this.person  : null,
            "emailAddress": this.emailAddress ? this.emailAddress : null,
            "phoneNumber": this.phoneNumber ? this.phoneNumber : null,
            "isEmailAddressVerified": this.isEmailAddressVerified ? this.isEmailAddressVerified?.toString() : null,
            "isPhoneNumberVerified": this.isPhoneNumberVerified ? this.isPhoneNumberVerified?.toString(): null
        }
    }
    */
}