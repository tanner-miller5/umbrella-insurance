import { callReadAllAnnouncementsRestEndpoint } from "../../../../../endpoints/rest/announcements/v1/AnnouncementRestEndpoints";
import { Announcement } from "../../../../../models/announcements/v1/Announcement";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var announcementId: number | undefined; 
var env: string = "TEST";
const SECONDS = 1000;
jest.setTimeout(70 * SECONDS)
describe('announcement endpoint tests', () => {
    var announcement: Announcement = new Announcement();
    announcement.message = "1";
    announcement.announcementName = "1";
    announcement.createdDateTime = "2022-11-11T11:11:11Z";

    var updatedAnnouncement: Announcement = new Announcement();
    updatedAnnouncement.message = "2";
    updatedAnnouncement.announcementName = "2";
    updatedAnnouncement.createdDateTime = "2022-11-12T11:11:11Z";

    //var domain: string = "http://localhost:8080";
    let domain: string = process.env.REACT_APP_DOMAIN || "http://localhost:8080";
    console.log(`domain:${domain}`);
    test('call get annoucenments', async () => {
        let announcementResponse: Announcement[] | undefined = await callReadAllAnnouncementsRestEndpoint(env, domain);
        if (announcementResponse) {
            for(let i = 0; i < announcementResponse?.length; i++) {
                console.log(`${announcementResponse[i].announcementName}:
                    ${announcementResponse[i].id}:
                    ${announcementResponse[i].message}:
                    ${announcementResponse[i].createdDateTime}:
                    ${announcementResponse[i].session}`)
            }
        }
        expect(announcementResponse).toBeTruthy();
    });
    /*
    //session code fix me
    let sessionCode = "f";

    test('call create announcement', async () => {
        var announcementResponse: Announcement = await callCreateAnnouncementRestEndpoints(announcement, env, domain, sessionCode);
        announcementId = announcementResponse.announcementId;
        expect(announcement.message).toBe(announcementResponse.message);
        expect(announcement.announcementName).toBe(announcementResponse.announcementName);
        expect(announcement.createdDateTime).toBe(announcementResponse.createdDateTime);
    });

    test('call read announcement', async () => {
        var announcements: Announcement[] | undefined = await callReadAnnouncementRestEndpointsByAnnouncementId(announcementId || 1, env, domain) || [];
        expect(announcements[0].message).toBe(announcement.message);
        expect(announcements[0].announcementName).toBe(announcement.announcementName);
        expect(announcements[0].createdDateTime).toBe(announcement.createdDateTime);
    });

    test('call update announcement', async () => {
        var announcementResponse: Announcement[] = await callUpdateAnnouncementRestEndpointsByAnnouncementId(
            updatedAnnouncement, announcementId || 1, env, domain, sessionCode);
        expect(updatedAnnouncement.message).toBe(announcementResponse[0].message);
        expect(updatedAnnouncement.announcementName).toBe(announcementResponse[0].announcementName);
        expect(updatedAnnouncement.createdDateTime).toBe(announcementResponse[0].createdDateTime);
    });

    test('call delete announcement', async () => {
        var deleteAnnouncementResponse: boolean = await callDeleteAnnouncementRestEndpointsByAnnouncementId(announcementId || 1, env, domain, "fixme");
        expect(true).toBe(deleteAnnouncementResponse);
    });
    */
});