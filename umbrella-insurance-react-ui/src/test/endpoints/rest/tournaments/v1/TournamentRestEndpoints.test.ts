import { callCreateTournamentTypeRestEndpoints, callDeleteTournamentTypeRestEndpointsByTournamentTypeId } from "../../../../../endpoints/rest/tournaments/tournamentTypes/v1/TournamentTypeRestEndpoints";
import { callCreateTournamentRestEndpoints, callDeleteTournamentRestEndpointsByTournamentId, callReadTournamentRestEndpointsByTournamentId, callUpdateTournamentRestEndpoints } from "../../../../../endpoints/rest/tournaments/v1/TournamentRestEndpoints";
import { TournamentType } from "../../../../../models/tournaments/tournamentTypes/v1/TournamentType";
import { Tournament } from "../../../../../models/tournaments/v1/Tournament";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('tournament endpoint tests', () => {
    var tournamentId: number | undefined; 

    var tournamentType: TournamentType = new TournamentType();
    tournamentType.tournamentTypeName = "1";

    var tournament: Tournament = new Tournament();
    tournament.tournamentName = "1";

    var updatedTournament: Tournament = new Tournament();
    updatedTournament.tournamentName = "2";

    var domain: string = "http://localhost:8080";

    test('call create tournament', async () => {
        var tournamentTypeResponse: TournamentType = await callCreateTournamentTypeRestEndpoints(
            tournamentType, env, domain);
        tournamentType.id = tournamentTypeResponse.id;
        tournament.tournamentType = tournamentType;
        updatedTournament.tournamentType = tournamentType
        expect(tournamentType.tournamentTypeName).toBe(tournamentTypeResponse.tournamentTypeName);

        var tournamentResponse: Tournament = await callCreateTournamentRestEndpoints(
            tournament, env, domain);
        tournamentId = tournamentResponse.id;
        expect(tournament.tournamentName).toBe(tournamentResponse.tournamentName);
    });

    test('call read tournament', async () => {
        var tournaments: Tournament[] | undefined = await callReadTournamentRestEndpointsByTournamentId(
            tournamentId || 1, env, domain) || [];
        expect(tournaments[0].tournamentName).toBe(tournament.tournamentName);
    });

    test('call update tournament', async () => {
        var tournamentResponse: Tournament[] = await callUpdateTournamentRestEndpoints(
            updatedTournament, env, domain);
        expect(updatedTournament.tournamentName).toBe(tournamentResponse[0].tournamentName);
    });

    test('call delete tournament', async () => {
        var deleteTournamentResponse: boolean = await callDeleteTournamentRestEndpointsByTournamentId(
            tournamentId || 1, env, domain);
        expect(true).toBe(deleteTournamentResponse);

        var deleteTournamentTypeResponse: boolean = await callDeleteTournamentTypeRestEndpointsByTournamentTypeId(
            tournamentType.id || 1, env, domain);
        expect(true).toBe(deleteTournamentTypeResponse);
    });
});