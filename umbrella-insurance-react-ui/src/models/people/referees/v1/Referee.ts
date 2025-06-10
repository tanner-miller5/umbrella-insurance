import { Person } from "../../v1/Person";

export class Referee {
    id?: number;
    person?: Person;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.person) {
                this.person = new Person(obj.person);
            }
        }
    }
}