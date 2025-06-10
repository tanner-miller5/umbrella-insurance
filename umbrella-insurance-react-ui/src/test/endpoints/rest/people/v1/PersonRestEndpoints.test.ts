import { Person } from "../../../../../models/people/v1/Person";
import {callCreatePersonRestEndpoints, 
    callDeletePersonRestEndpointsByPersonId,
    callReadPersonRestEndpointsByPersonId,
    callUpdatePersonRestEndpoints
} from "../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
beforeEach((): void => {
    jest.setTimeout(60000);
});
var personId: number | undefined; 
var env: string = "TEST";
describe.skip('person endpoint tests', () => {
    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1"

    var updatedPerson: Person = new Person();
    updatedPerson.dateOfBirth = "1111-11-11";
    updatedPerson.firstName = "2";
    updatedPerson.middleName = "m2";
    updatedPerson.surname = "l2";
    updatedPerson.ssn = "1";

    var domain: string = "http://localhost:8080";

    test('call create person', async () => {
        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        personId = personResponse.id;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);
    });

    test('call read person', async () => {
        var persons: Person[] = await callReadPersonRestEndpointsByPersonId(personId || 1, env, domain) || [];
        expect(person.dateOfBirth).toBe(persons[0].dateOfBirth);
        expect(person.firstName).toBe(persons[0].firstName);
        expect(person.middleName).toBe(persons[0].middleName);
        expect(person.surname).toBe(persons[0].surname);
    });

    test('call update person', async () => {
        var personResponse: Person[] = await callUpdatePersonRestEndpoints(updatedPerson, env, domain);
        expect(updatedPerson.dateOfBirth).toBe(personResponse[0].dateOfBirth);
        expect(updatedPerson.firstName).toBe(personResponse[0].firstName);
        expect(updatedPerson.middleName).toBe(personResponse[0].middleName);
        expect(updatedPerson.surname).toBe(personResponse[0].surname);
    });

    test('call delete person', async () => {
        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(personId || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});