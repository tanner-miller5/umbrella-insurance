export class Peril {
    id?: number;
    perilName?: string;
    description?: string;
    scaleName?: string;
    minMagnitude?: number;
    maxMagnitude?: number;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.perilName) {
                this.perilName = obj.perilName;
            }
            if(obj?.description) {
                this.description = obj.description;
            }
            if(obj?.scaleName) {
                this.scaleName = obj.scaleName;
            }
            if(obj?.minMagnitude) {
                this.minMagnitude = obj.minMagnitude;
            }
            if(obj?.maxMagnitude) {
                this.maxMagnitude = obj.maxMagnitude;
            }
        }
    }
}