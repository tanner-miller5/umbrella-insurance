import { Person } from "../../../people/v1/Person";
import { User } from "../../v1/User";

export class BankAccount {
    id?: number;
    createdDateTime?: string;
    accountNumber?: string;
    routingNumber?: string;
    user?: User;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.accountNumber) {
                this.accountNumber = obj.accountNumber;
            }
            if(obj?.routingNumber) {
                this.routingNumber = obj.routingNumber;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
        }
    }
}