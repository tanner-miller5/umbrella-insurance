export class ApplicationRole {
    id?: number;
    applicationRoleName?: string;

    constructor(obj?: any) {
        if (obj) {
            if (obj?.id) {
                this.id = obj.id;
            }
            if (obj?.applicationRoleName) {
                this.applicationRoleName = obj.applicationRoleName;
            }
        }
    }
}