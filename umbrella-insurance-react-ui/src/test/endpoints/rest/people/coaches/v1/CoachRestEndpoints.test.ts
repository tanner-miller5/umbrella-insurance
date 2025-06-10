import { callCreateCoachRestEndpoints, callReadCoachRestEndpointsByCoachId, callDeleteCoachRestEndpointsByCoachId, callUpdateCoachRestEndpoints } from "../../../../../../endpoints/rest/people/coaches/v1/CoachRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { Coach } from "../../../../../../models/people/coaches/v1/Coach";
import { Person } from "../../../../../../models/people/v1/Person";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('coach endpoint tests', () => {
    var coachId: number | undefined; 

    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1";
    
    var coach: Coach = new Coach();

    var updatedCoach: Coach = new Coach();

    var domain: string = "http://localhost:8080";

    test('call create coach', async () => {
        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        person.id = personResponse.id;
        coach.person = person;
        updatedCoach.person = person;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);

        var coachResponse: Coach = await callCreateCoachRestEndpoints(
            coach, env, domain);
        coachId = coachResponse.id;
        expect(coach.person).toBe(coachResponse.person);
    });

    test('call read coach', async () => {
        var coachs: Coach[] | undefined = await callReadCoachRestEndpointsByCoachId(
            coachId || 1, env, domain) || [];
        expect(coachs[0].person).toBe(coach.person);
    });

    test('call update coach', async () => {
        var coachResponse: Coach[] = await callUpdateCoachRestEndpoints(
            updatedCoach, env, domain);
        expect(updatedCoach.person).toBe(coachResponse[0].person);
    });

    test('call delete coach', async () => {
        var deleteCoachResponse: boolean = await callDeleteCoachRestEndpointsByCoachId(
            coachId || 1, env, domain);
        expect(true).toBe(deleteCoachResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});