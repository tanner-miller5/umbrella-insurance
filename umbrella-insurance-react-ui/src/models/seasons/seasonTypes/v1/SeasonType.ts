export class SeasonType {
    id?: number;
    seasonTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.seasonTypeName) {
                this.seasonTypeName = obj.seasonTypeName;
            }
        }
    }
}