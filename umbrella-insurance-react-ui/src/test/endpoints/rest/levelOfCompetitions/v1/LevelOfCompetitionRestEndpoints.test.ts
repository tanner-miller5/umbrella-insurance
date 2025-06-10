import { callCreateLevelOfCompetitionRestEndpoints, callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionId, callReadLevelOfCompetitionRestEndpointsByLevelOfCompetitionId, callUpdateLevelOfCompetitionRestEndpoints } from "../../../../../endpoints/rest/levelOfCompetitions/v1/LevelOfCompetitionRestEndpoints";
import { LevelOfCompetition } from "../../../../../models/levelOfCompetitions/v1/LevelOfCompetition";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('levelOfCompetition endpoint tests', () => {
    var levelOfCompetitionId: number | undefined; 
    var levelOfCompetition: LevelOfCompetition = new LevelOfCompetition();
    levelOfCompetition.levelOfCompetitionName = "1";

    var updatedLevelOfCompetition: LevelOfCompetition = new LevelOfCompetition();
    updatedLevelOfCompetition.levelOfCompetitionName = "2";

    var domain: string = "http://localhost:8080";

    test('call create levelOfCompetition', async () => {
        var levelOfCompetitionResponse: LevelOfCompetition = await callCreateLevelOfCompetitionRestEndpoints(
            levelOfCompetition, env, domain);
        levelOfCompetitionId = levelOfCompetitionResponse.id;
        expect(levelOfCompetition.levelOfCompetitionName).toBe(levelOfCompetitionResponse.levelOfCompetitionName);
    });

    test('call read levelOfCompetition', async () => {
        var levelOfCompetitions: LevelOfCompetition[] | undefined = await callReadLevelOfCompetitionRestEndpointsByLevelOfCompetitionId(
            levelOfCompetitionId || 1, env, domain) || [];
        expect(levelOfCompetitions[0].levelOfCompetitionName).toBe(levelOfCompetition.levelOfCompetitionName);
    });

    test('call update levelOfCompetition', async () => {
        var levelOfCompetitionResponse: LevelOfCompetition[] = await callUpdateLevelOfCompetitionRestEndpoints(
            updatedLevelOfCompetition, env, domain);
        expect(updatedLevelOfCompetition.levelOfCompetitionName).toBe(levelOfCompetitionResponse[0].levelOfCompetitionName);
    });

    test('call delete levelOfCompetition', async () => {
        var deleteLevelOfCompetitionResponse: boolean = await callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionId(
            levelOfCompetitionId || 1, env, domain);
        expect(true).toBe(deleteLevelOfCompetitionResponse);
    });
});