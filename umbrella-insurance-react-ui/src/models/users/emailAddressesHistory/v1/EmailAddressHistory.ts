import { User } from "../../v1/User"

export class EmailAddressHistory {
      id?: number
      user?: User
      emailAddress?: string
      createdDateTime?: string

      constructor(obj?:any) {
            if(obj) {
                if(obj?.id) {
                    this.id = obj.id;
                }
                if(obj?.user) {
                    this.user = new User(obj.user);
                }
                if(obj?.emailAddress) {
                    this.emailAddress = obj.emailAddress;
                }
                if(obj?.createdDateTime) {
                    this.createdDateTime = obj.createdDateTime;
                }
            }
        }
}