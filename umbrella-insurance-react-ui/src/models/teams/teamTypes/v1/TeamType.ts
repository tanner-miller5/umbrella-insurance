export class TeamType {
      id?: number;
      teamTypeName?: string;

      constructor(obj?:any) {
            if(obj) {
                if(obj?.id) {
                    this.id = obj.id;
                }
                if(obj?.teamTypeName) {
                    this.teamTypeName = obj.teamTypeName;
                }
            }
        }
}