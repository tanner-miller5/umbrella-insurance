import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Record } from "../../../../models/records/v1/Record";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateRecordRestEndpoints(
    record: Record, env: string, domain: string): Promise<Record>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/records/v1?env=${env}`;
    let data = JSON.stringify(record as any, replacer);
    let createRecordResponse: AxiosResponse<Record> = await axios.post(url, data, config);
    let record1 = new Record(createRecordResponse.data);
    return record1;
}

export async function callReadRecordRestEndpointsByRecordId(
    recordId: number, env: string, domain: string): Promise<Record[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/records/v1?env=${env}&recordId=${recordId}`;
    let records: Record[] | undefined = [];
    try {
        let readRecordListResponse: AxiosResponse<Record[]> = await axios.get(url, config);
        let recordList = readRecordListResponse.data;
        for(let i = 0; i < recordList.length; i++) {
            records[i] = new Record(recordList[i]);
        }
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();         
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return records;
}

export async function callReadRecordRestEndpointsByRecordName(
    recordName: string, env: string, domain: string): Promise<Record[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/records/v1?env=${env}&recordName=${recordName}`;
    let records: Record[] | undefined = [];
    try {
        let readRecordListResponse: AxiosResponse<Record[]> = await axios.get(url, config);
        let recordList = readRecordListResponse.data;
        for(let i = 0; i < recordList.length; i++) {
            records[i] = new Record(recordList[i]);
        }
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return records;
}

export async function callUpdateRecordRestEndpoints(
    record: Record, env: string, domain: string): Promise<Record[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/records/v1?env=${env}`;
    let data = JSON.stringify(record as any, replacer);
    let updateRecordListResponse: AxiosResponse<Record[]> = await axios.put(url, data, config);
    let recordList = updateRecordListResponse.data;
    let records: Record[] | undefined = [];
    for(let i = 0; i < recordList.length; i++) {
        records[i] = new Record(recordList[i]);
    }
    return records;
}

export async function callDeleteRecordRestEndpointsByRecordId(
    recordId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/records/v1?env=${env}&recordId=${recordId}` ;
    let deleteRecordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRecordResponse.status == 204;
}

export async function callDeleteRecordRestEndpointsByRecordName(
    recordName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/records/v1?env=${env}&recordName=${recordName}` ;
    let deleteRecordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRecordResponse.status == 204;
}