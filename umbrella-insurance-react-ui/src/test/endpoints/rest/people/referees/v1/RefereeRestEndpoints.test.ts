import { callCreateRefereeRestEndpoints, callReadRefereeRestEndpointsByRefereeId, callDeleteRefereeRestEndpointsByRefereeId, callUpdateRefereeRestEndpoints } from "../../../../../../endpoints/rest/people/referees/v1/RefereeRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { Referee } from "../../../../../../models/people/referees/v1/Referee";
import { Person } from "../../../../../../models/people/v1/Person";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('referee endpoint tests', () => {
    var refereeId: number | undefined; 
    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1";
    
    var referee: Referee = new Referee();


    var updatedReferee: Referee = new Referee();

    var domain: string = "http://localhost:8080";


    test('call create referee', async () => {
        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        person.id = personResponse.id;
        referee.person = person;
        updatedReferee.person = person;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);
        
        var refereeResponse: Referee = await callCreateRefereeRestEndpoints(
            referee, env, domain);
        refereeId = refereeResponse.id;
        expect(referee.person).toBe(refereeResponse.person);
    });

    test('call read referee', async () => {
        var referees: Referee[] | undefined = await callReadRefereeRestEndpointsByRefereeId(
            refereeId || 1, env, domain) || [];
        expect(referees[0].person).toBe(referee.person);
    });

    test('call update referee', async () => {
        var refereeResponse: Referee[] = await callUpdateRefereeRestEndpoints(
            updatedReferee, env, domain);
        expect(updatedReferee.person).toBe(refereeResponse[0].person);
    });

    test('call delete referee', async () => {
        var deleteRefereeResponse: boolean = await callDeleteRefereeRestEndpointsByRefereeId(
            refereeId || 1, env, domain);
        expect(true).toBe(deleteRefereeResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});