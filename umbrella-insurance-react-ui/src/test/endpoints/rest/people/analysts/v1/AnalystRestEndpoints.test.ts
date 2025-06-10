import { callCreateSpecialtyRestEndpoints, callDeleteSpecialtyRestEndpointsBySpecialtyId } from "../../../../../../endpoints/rest/people/analysts/specialties/v1/SpecialtyRestEndpoints";
import { callCreateAnalystRestEndpoints, callReadAnalystRestEndpointsByAnalystId, callDeleteAnalystRestEndpointsByAnalystId, callUpdateAnalystRestEndpoints } from "../../../../../../endpoints/rest/people/analysts/v1/AnalystRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { Specialty } from "../../../../../../models/people/analysts/specialties/v1/Specialty";
import { Analyst } from "../../../../../../models/people/analysts/v1/Analyst";
import { Person } from "../../../../../../models/people/v1/Person";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('analyst endpoint tests', () => {
    var analystId: number | undefined; 
    var specialty: Specialty = new Specialty();
    specialty.specialtyName = "1";

    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1";


    var analyst: Analyst = new Analyst();


    var updatedAnalyst: Analyst = new Analyst();

    var domain: string = "http://localhost:8080";


    test('call create analyst', async () => {
        var specialtyResponse: Specialty = await callCreateSpecialtyRestEndpoints(
            specialty, env, domain);
        specialty.id = specialtyResponse.id;
        analyst.specialty = specialty;
        updatedAnalyst.specialty = specialty;
        expect(specialty.specialtyName).toBe(specialtyResponse.specialtyName);
        
        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        person.id = personResponse.id;
        analyst.person = person;
        updatedAnalyst.person = person;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);
        
        var analystResponse: Analyst = await callCreateAnalystRestEndpoints(
            analyst, env, domain);
        analystId = analystResponse.id;
        expect(analyst.person).toBe(analystResponse.person);
        expect(analyst.specialty).toBe(analystResponse.specialty);
    });

    test('call read analyst', async () => {
        var analysts: Analyst[] | undefined = await callReadAnalystRestEndpointsByAnalystId(
            analystId || 1, env, domain) || [];
        expect(analysts[0].person).toBe(analyst.person);
        expect(analysts[0].specialty).toBe(analyst.specialty);
    });

    test('call update analyst', async () => {
        var analystResponse: Analyst[] = await callUpdateAnalystRestEndpoints(
            updatedAnalyst, env, domain);
        expect(updatedAnalyst.person).toBe(analystResponse[0].person);
        expect(updatedAnalyst.specialty).toBe(analystResponse[0].specialty);
    });

    test('call delete analyst', async () => {
        var deleteAnalystResponse: boolean = await callDeleteAnalystRestEndpointsByAnalystId(
            analystId || 1, env, domain);
        expect(true).toBe(deleteAnalystResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);

        var deleteSpecialtyResponse: boolean = await callDeleteSpecialtyRestEndpointsBySpecialtyId(
            specialty.id || 1, env, domain);
        expect(true).toBe(deleteSpecialtyResponse);
    });
});