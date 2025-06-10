import { callCreateEventTypeRestEndpoints, callReadEventTypeRestEndpointsByEventTypeId, callDeleteEventTypeRestEndpointsByEventTypeId, callUpdateEventTypeRestEndpoints } from "../../../../../../../endpoints/rest/users/events/eventTypes/v1/EventTypeRestEndpoints";
import { EventType } from "../../../../../../../models/users/events/eventTypes/v1/EventType";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('eventType endpoint tests', () => {
    var eventTypeId: number | undefined; 
    var eventType: EventType = new EventType();
    eventType.eventTypeName = "1";

    var updatedEventType: EventType = new EventType();
    updatedEventType.eventTypeName = "2";

    var domain: string = "http://localhost:8080";

    test('call create eventType', async () => {
        var eventTypeResponse: EventType = await callCreateEventTypeRestEndpoints(
            eventType, env, domain);
        eventTypeId = eventTypeResponse.id;
        expect(eventType.eventTypeName).toBe(eventTypeResponse.eventTypeName);
    });

    test('call read eventType', async () => {
        var eventTypes: EventType[] | undefined = await callReadEventTypeRestEndpointsByEventTypeId(
            eventTypeId || 1, env, domain) || [];
        expect(eventTypes[0].eventTypeName).toBe(eventType.eventTypeName);
    });

    test('call update eventType', async () => {
        var eventTypeResponse: EventType[] = await callUpdateEventTypeRestEndpoints(
            updatedEventType, env, domain);
        expect(updatedEventType.eventTypeName).toBe(eventTypeResponse[0].eventTypeName);
    });

    test('call delete eventType', async () => {
        var deleteEventTypeResponse: boolean = await callDeleteEventTypeRestEndpointsByEventTypeId(
            eventTypeId || 1, env, domain);
        expect(true).toBe(deleteEventTypeResponse);
    });
});