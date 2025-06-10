import { User } from "../../v1/User";

export class UserAgreement {
    id?: number;
    user?: User;
    userAgreementName?: string;
    userAgreementText?: string;
    updatedDateTime?: string;
    didAgree?: boolean;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.userAgreementName) {
                this.userAgreementName = obj.userAgreementName;
            }
            if(obj?.userAgreementText) {
                this.userAgreementText = obj.userAgreementText;
            }
            if(obj?.updatedDateTime) {
                this.updatedDateTime = obj.updatedDateTime;
            }
            if(obj?.didAgree) {
                this.didAgree = obj.didAgree;
            }
        }
    }
}