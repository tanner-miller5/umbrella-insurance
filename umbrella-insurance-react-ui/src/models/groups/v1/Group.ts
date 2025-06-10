export class Group {
    id?: number;
    groupName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.groupName) {
                this.groupName = obj.groupName;
            }
        }
    }
}