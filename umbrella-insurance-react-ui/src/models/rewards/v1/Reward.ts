export class Reward {
    id?: number;
    rewardName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.rewardName) {
                this.rewardName = obj.rewardName;
            }
        }
    }
}