export class EventType {
    id?: number;
    eventTypeName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.eventTypeName) {
                this.eventTypeName = obj.eventTypeName;
            }
        }
    }
}