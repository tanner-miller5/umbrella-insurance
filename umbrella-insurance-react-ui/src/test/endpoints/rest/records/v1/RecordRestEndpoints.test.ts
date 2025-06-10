import { callCreateRecordRestEndpoints, callDeleteRecordRestEndpointsByRecordId, callReadRecordRestEndpointsByRecordId, callUpdateRecordRestEndpoints } from "../../../../../endpoints/rest/records/v1/RecordRestEndpoints";
import { Record } from "../../../../../models/records/v1/Record";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('record record endpoint tests', () => {
    var recordId: number | undefined; 
    var record: Record = new Record();
    record.recordName = "1";
    record.losses = 1;
    record.ties = 2;
    record.wins = 3;

    var updatedRecord: Record = new Record();
    updatedRecord.recordName = "2";
    updatedRecord.losses = 4;
    updatedRecord.ties = 5;
    updatedRecord.wins = 6;

    var domain: string = "http://localhost:8080";

    test('call create record record', async () => {
        var recordResponse: Record = await callCreateRecordRestEndpoints(
            record, env, domain);
        recordId = recordResponse.id;
        expect(record.recordName).toBe(recordResponse.recordName);
        expect(record.losses).toBe(recordResponse.losses);
        expect(record.ties).toBe(recordResponse.ties);
        expect(record.wins).toBe(recordResponse.wins);
    });

    test('call read record record', async () => {
        var records: Record[] | undefined = await callReadRecordRestEndpointsByRecordId(
            recordId || 1, env, domain) || [];
        expect(records[0].recordName).toBe(record.recordName);
        expect(records[0].losses).toBe(record.losses);
        expect(records[0].ties).toBe(record.ties);
        expect(records[0].wins).toBe(record.wins);
    });

    test('call update record record', async () => {
        var recordResponse: Record[] = await callUpdateRecordRestEndpoints(
            updatedRecord, env, domain);
        expect(updatedRecord.recordName).toBe(recordResponse[0].recordName);
        expect(updatedRecord.losses).toBe(recordResponse[0].losses);
        expect(updatedRecord.ties).toBe(recordResponse[0].ties);
        expect(updatedRecord.wins).toBe(recordResponse[0].wins);
    });

    test('call delete record record', async () => {
        var deleteRecordResponse: boolean = await callDeleteRecordRestEndpointsByRecordId(
            recordId || 1, env, domain);
        expect(true).toBe(deleteRecordResponse);
    });
});