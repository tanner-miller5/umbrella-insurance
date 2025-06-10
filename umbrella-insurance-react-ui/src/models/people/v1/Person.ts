export class Person {
    id?: number;
    firstName?: string;
    middleName?: string;
    surname?: string;
    dateOfBirth?: string; //yyyy-mm-dd
    ssn?: string;

    constructor(obj?: any) {
        if (obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if (obj?.firstName) {
                this.firstName = obj.firstName;
            }
            if (obj?.middleName) {
                this.middleName = obj.middleName;
            }
            if (obj?.surname) {
                this.surname = obj.surname;
            }
            if (obj?.dateOfBirth) {
                this.dateOfBirth = obj.dateOfBirth;
            }
            if (obj?.ssn) {
                this.ssn = obj.ssn;
            }
        }
    }
}