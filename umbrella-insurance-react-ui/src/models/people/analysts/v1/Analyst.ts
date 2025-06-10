import { Person } from "../../v1/Person";
import { Specialty } from "../specialties/v1/Specialty";

export class Analyst {
    id?: number;
    specialty?: Specialty;
    person?: Person;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.specialty) {
                this.specialty = new Specialty(obj.specialty);
            }
            if(obj?.person) {
                this.person = new Person(obj.person);
            }
        }
    }
}