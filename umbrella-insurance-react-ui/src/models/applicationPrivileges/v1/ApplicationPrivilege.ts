export class ApplicationPrivilege {
    id?: number;
    applicationPrivilegeName?: string;
    constructor(obj?: any) {
        if (obj) {
            if (obj?.id) { 
                this.id = obj.id;
            }
            if (obj?.applicationPrivilegeName) {
                this.applicationPrivilegeName = obj.applicationPrivilegeName;
            }
        }
    }
}