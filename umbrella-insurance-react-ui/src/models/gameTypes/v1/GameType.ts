export class GameType {
    id?: number;
    gameTypeName?: string;
    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.gameTypeName) {
                this.gameTypeName = obj.gameTypeName;
            }
        }
    }
}