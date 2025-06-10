import { User } from "../../v1/User";

export class PhoneNumberHistory {
      id?: number;
      user?: User;
      phoneNumber?: string;
      createdDateTime?: string;

      constructor(obj?:any) {
            if(obj) {
                if(obj?.id) {
                    this.id = obj.id;
                }
                if(obj?.user) {
                    this.user = new User(obj.user);
                }
                if(obj?.phoneNumber) {
                    this.phoneNumber = obj.phoneNumber;
                }
                if(obj?.createdDateTime) {
                    this.createdDateTime = obj.createdDateTime;
                }
            }
        }
}