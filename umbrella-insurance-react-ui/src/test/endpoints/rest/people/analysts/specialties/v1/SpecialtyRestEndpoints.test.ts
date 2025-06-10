import { callCreateSpecialtyRestEndpoints, callReadSpecialtyRestEndpointsBySpecialtyId, callDeleteSpecialtyRestEndpointsBySpecialtyId, callUpdateSpecialtyRestEndpoints } from "../../../../../../../endpoints/rest/people/analysts/specialties/v1/SpecialtyRestEndpoints";
import { Specialty } from "../../../../../../../models/people/analysts/specialties/v1/Specialty";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('specialty endpoint tests', () => {
    var specialtyId: number | undefined; 
    var specialty: Specialty = new Specialty();
    specialty.specialtyName = "1";

    var updatedSpecialty: Specialty = new Specialty();
    updatedSpecialty.specialtyName = "2";

    var domain: string = "http://localhost:8080";

    test('call create specialty', async () => {
        var specialtyResponse: Specialty = await callCreateSpecialtyRestEndpoints(
            specialty, env, domain);
        specialtyId = specialtyResponse.id;
        expect(specialty.specialtyName).toBe(specialtyResponse.specialtyName);
    });

    test('call read specialty', async () => {
        var specialtys: Specialty[] | undefined = await callReadSpecialtyRestEndpointsBySpecialtyId(
            specialtyId || 1, env, domain) || [];
        expect(specialtys[0].specialtyName).toBe(specialty.specialtyName);
    });

    test('call update specialty', async () => {
        var specialtyResponse: Specialty[] = await callUpdateSpecialtyRestEndpoints(
            updatedSpecialty, env, domain);
        expect(updatedSpecialty.specialtyName).toBe(specialtyResponse[0].specialtyName);
    });

    test('call delete specialty', async () => {
        var deleteSpecialtyResponse: boolean = await callDeleteSpecialtyRestEndpointsBySpecialtyId(
            specialtyId || 1, env, domain);
        expect(true).toBe(deleteSpecialtyResponse);
    });
});