export class Island {
    id?: number;
    islandName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.islandName) {
                this.islandName = obj.islandName;
            }
        }
    }
}