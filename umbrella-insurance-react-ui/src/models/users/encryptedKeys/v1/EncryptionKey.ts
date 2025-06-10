import { User } from "../../v1/User";

export class EncryptionKey {
    id?: number;
    user?: User;
    encryptionKeyPrivateKey?: string;
    encryptionKeyPublicKey?: string;
    createdDateTime?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.user) {
                this.user = new User(obj.user);
            }
            if(obj?.encryptionKeyPrivateKey) {
                this.encryptionKeyPrivateKey = obj.encryptionKeyPrivateKey;
            }
            if(obj?.encryptionKeyPublicKey) {
                this.encryptionKeyPublicKey = obj.encryptionKeyPublicKey;
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
        }
    }
}