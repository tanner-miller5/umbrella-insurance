export class Season {
    id?: number;
    seasonName?: string;
    startDate?: string;
    endDate?: string;    

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.seasonName) {
                this.seasonName = obj.seasonName;
            }
            if(obj?.startDate) {
                this.startDate = obj.startDate;
            }
            if(obj?.endDate) {
                this.endDate = obj.endDate;
            }
        }
    }  
}