import { User } from "../../v1/User";
import {Location} from "../../../geographies/locations/v1/Location"

export class CardOnFile {
    id?: number;
    cardNumber?: string;
    expirationDate?: string;
    cvv?: string;
    user?: User;
    phoneNumber?: string;
    location?: Location;//billingAddress
    createdDateTime?: string;
    deletedDateTime?: string;
    isDeleted?: boolean;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.cardNumber) {
                this.cardNumber = obj.cardNumber;
            }
            if(obj?.expirationDate) {
                this.expirationDate = obj.expirationDate;
            }
            if(obj?.cvv) {
                this.cvv = obj.cvv;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.phoneNumber) {
                this.phoneNumber = obj.phoneNumber;
            }
            if(obj?.location) {
                this.location = new Location(obj.location);
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.deletedDateTime) {
                this.deletedDateTime = obj.deletedDateTime;
            }
            if(typeof obj?.isDeleted === 'boolean') {
                this.isDeleted = obj.isDeleted;
            }
        }
    }
}