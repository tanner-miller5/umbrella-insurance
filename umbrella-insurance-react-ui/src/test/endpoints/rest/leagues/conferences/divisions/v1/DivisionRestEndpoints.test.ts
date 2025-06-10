import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateDivisionRestEndpoints, callReadDivisionRestEndpointsByDivisionId, callDeleteDivisionRestEndpointsByDivisionId, callUpdateDivisionRestEndpoints } from "../../../../../../../endpoints/rest/leagues/conferences/divisions/v1/DivisionRestEndpoints";
import { callCreateConferenceRestEndpoints, callDeleteConferenceRestEndpointsByConferenceId } from "../../../../../../../endpoints/rest/leagues/conferences/v1/ConferenceRestEndpoints";
import { callCreateLeagueRestEndpoints, callDeleteLeagueRestEndpointsByLeagueId } from "../../../../../../../endpoints/rest/leagues/v1/LeagueRestEndpoints";
import { GameType } from "../../../../../../../models/gameTypes/v1/GameType";
import { Division } from "../../../../../../../models/leagues/conferences/divisions/v1/Division";
import { Conference } from "../../../../../../../models/leagues/conferences/v1/Conference";
import { League } from "../../../../../../../models/leagues/v1/League";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('division endpoint tests', () => {
    var divisionId: number | undefined; 
    var gameTypeName = "1";
    var gameType: GameType = new GameType();
    gameType.gameTypeName = gameTypeName;
    var league: League = new League();
    league.leagueName = "1";
    var conference: Conference = new Conference();
    conference.conferenceName = "1";
    
    var division: Division = new Division();
    division.divisionName = "1";

    var updatedDivision: Division = new Division();
    updatedDivision.divisionName = "2";

    var domain: string = "http://localhost:8080";

    test('call create division', async () => {
        var gameTypeResponse: GameType = await callCreateGameTypeRestEndpoints(
            gameType, env, domain);
        gameType.id = gameTypeResponse.id;
        league.gameType = gameTypeResponse;
        expect(gameType.gameTypeName).toBe(gameTypeResponse.gameTypeName);

        var leagueResponse: League = await callCreateLeagueRestEndpoints(
            league, env, domain);
        league.id = leagueResponse.id;
        conference.league = leagueResponse;
        expect(league.leagueName).toBe(leagueResponse.leagueName);
        
        var conferenceResponse: Conference = await callCreateConferenceRestEndpoints(
            conference, env, domain);
        conference.id = conferenceResponse.id;
        division.conference = conferenceResponse;
        updatedDivision.conference = conferenceResponse;
        expect(conference.conferenceName).toBe(conferenceResponse.conferenceName);
        expect(conference.league).toBe(conferenceResponse.league);

        var divisionResponse: Division = await callCreateDivisionRestEndpoints(
            division, env, domain);
        divisionId = divisionResponse.id;
        expect(division.divisionName).toBe(divisionResponse.divisionName);
    });

    test('call read division', async () => {
        var divisions: Division[] | undefined = await callReadDivisionRestEndpointsByDivisionId(
            divisionId || 1, env, domain) || [];
        expect(divisions[0].divisionName).toBe(division.divisionName);
    });

    test('call update division', async () => {
        var divisionResponse: Division[] = await callUpdateDivisionRestEndpoints(
            updatedDivision, env, domain);
        expect(updatedDivision.divisionName).toBe(divisionResponse[0].divisionName);
    });

    test('call delete division', async () => {
        var deleteDivisionResponse: boolean = await callDeleteDivisionRestEndpointsByDivisionId(
            divisionId || 1, env, domain);
        expect(true).toBe(deleteDivisionResponse);

        var deleteConferenceResponse: boolean = await callDeleteConferenceRestEndpointsByConferenceId(
            conference.id || 1, env, domain);
        expect(true).toBe(deleteConferenceResponse);

        var deleteLeagueResponse: boolean = await callDeleteLeagueRestEndpointsByLeagueId(
            league.id || 1, env, domain);
        expect(true).toBe(deleteLeagueResponse);

        var deleteGameTypeResponse: boolean = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameType.id || 1, env, domain);
        expect(true).toBe(deleteGameTypeResponse);
    });
});