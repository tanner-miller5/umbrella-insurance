import { callCreateGameTypeRestEndpoints, callDeleteGameTypeRestEndpointsByGameTypeId } from "../../../../../../endpoints/rest/gameTypes/v1/GameTypeRestEndpoints";
import { callCreateConferenceRestEndpoints, callReadConferenceRestEndpointsByConferenceId, callDeleteConferenceRestEndpointsByConferenceId, callUpdateConferenceRestEndpoints } from "../../../../../../endpoints/rest/leagues/conferences/v1/ConferenceRestEndpoints";
import { callCreateLeagueRestEndpoints, callDeleteLeagueRestEndpointsByLeagueId } from "../../../../../../endpoints/rest/leagues/v1/LeagueRestEndpoints";
import { GameType } from "../../../../../../models/gameTypes/v1/GameType";
import { Conference } from "../../../../../../models/leagues/conferences/v1/Conference";
import { League } from "../../../../../../models/leagues/v1/League";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('conference endpoint tests', () => {
    var conferenceId: number | undefined; 
    var gameTypeName = "1";
    var gameType: GameType = new GameType();
    gameType.gameTypeName = gameTypeName;
    var league: League = new League();
    league.leagueName = "1";
    var conference: Conference = new Conference();
    conference.conferenceName = "1";

    var updatedConference: Conference = new Conference();
    updatedConference.conferenceName = "2";

    var domain: string = "http://localhost:8080";

    test('call create conference', async () => {
        var gameTypeResponse: GameType = await callCreateGameTypeRestEndpoints(
            gameType, env, domain);
        gameType.id = gameTypeResponse.id;
        league.gameType = gameTypeResponse;
        expect(gameType.gameTypeName).toBe(gameTypeResponse.gameTypeName);

        var leagueResponse: League = await callCreateLeagueRestEndpoints(
            league, env, domain);
        league.id = leagueResponse.id;
        conference.league = leagueResponse;
        updatedConference.league = leagueResponse;
        expect(league.leagueName).toBe(leagueResponse.leagueName);
        
        var conferenceResponse: Conference = await callCreateConferenceRestEndpoints(
            conference, env, domain);
        conferenceId = conferenceResponse.id;
        expect(conference.conferenceName).toBe(conferenceResponse.conferenceName);
        expect(conference.league).toBe(conferenceResponse.league);
    });

    test('call read conference', async () => {
        var conferences: Conference[] | undefined = await callReadConferenceRestEndpointsByConferenceId(
            conferenceId || 1, env, domain) || [];
        expect(conferences[0].conferenceName).toBe(conference.conferenceName);
        expect(conferences[0].league).toBe(conference.league);
    });

    test('call update conference', async () => {
        var conferenceResponse: Conference[] = await callUpdateConferenceRestEndpoints(
            updatedConference, env, domain);
        expect(updatedConference.conferenceName).toBe(conferenceResponse[0].conferenceName);
    });

    test('call delete conference', async () => {
        var deleteConferenceResponse: boolean = await callDeleteConferenceRestEndpointsByConferenceId(
            conferenceId || 1, env, domain);
        expect(true).toBe(deleteConferenceResponse);

        var deleteLeagueResponse: boolean = await callDeleteLeagueRestEndpointsByLeagueId(
            league.id || 1, env, domain);
        expect(true).toBe(deleteLeagueResponse);

        var deleteGameTypeResponse: boolean = await callDeleteGameTypeRestEndpointsByGameTypeId(
            gameType.id || 1, env, domain);
        expect(true).toBe(deleteGameTypeResponse);
    });
});