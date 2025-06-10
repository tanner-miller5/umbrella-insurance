export class Equipment {
    id?: number;
    equipmentName?: string;
    constructor(obj?: any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.equipmentName) {
                this.equipmentName = obj.equipmentName;
            }
        }
    }
} 