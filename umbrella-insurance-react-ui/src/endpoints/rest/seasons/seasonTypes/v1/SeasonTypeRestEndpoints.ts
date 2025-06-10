import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { SeasonType } from "../../../../../models/seasons/seasonTypes/v1/SeasonType";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateSeasonTypeRestEndpoints(
    seasonType: SeasonType, env: string, domain: string): Promise<SeasonType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasonTypes/v1?env=${env}`;
    let data = JSON.stringify(seasonType as any, replacer);
    let createSeasonTypeResponse: AxiosResponse<SeasonType> = await axios.post(url, data, config);
    let seasonType1 = new SeasonType(createSeasonTypeResponse.data);
    return seasonType1;
}

export async function callReadSeasonTypeRestEndpointsBySeasonTypeId(
    seasonTypeId: number, env: string, domain: string): Promise<SeasonType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasonTypes/v1?env=${env}&seasonTypeId=${seasonTypeId}`;
    let seasonTypes: SeasonType[] | undefined = [];
    try {
        let readSeasonTypeListResponse: AxiosResponse<SeasonType[]> = await axios.get(url, config);
        let seasonTypesList = readSeasonTypeListResponse.data;
        for(let i = 0; i < seasonTypesList.length; i++) {
            seasonTypes[i] = new SeasonType(seasonTypesList[i]);
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
    return seasonTypes;
}

export async function callReadSeasonTypeRestEndpointsBySeasonTypeName(
    seasonTypeName: string, env: string, domain: string): Promise<SeasonType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasonTypes/v1?env=${env}&seasonTypeName=${seasonTypeName}`;
    let seasonTypes: SeasonType[] | undefined = [];
    try {
        let readSeasonTypeListResponse: AxiosResponse<SeasonType[]> = await axios.get(url, config);
        let seasonTypesList = readSeasonTypeListResponse.data;
        for(let i = 0; i < seasonTypesList.length; i++) {
            seasonTypes[i] = new SeasonType(seasonTypesList[i]);
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
    return seasonTypes;
}

export async function callUpdateSeasonTypeRestEndpoints(
    seasonType: SeasonType, env: string, domain: string): Promise<SeasonType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasonTypes/v1?env=${env}`;
    let data = JSON.stringify(seasonType as any, replacer);
    let updateSeasonTypeListResponse: AxiosResponse<SeasonType[]> = await axios.put(url, data, config);
    let seasonTypesList = updateSeasonTypeListResponse.data;
    let seasonTypes: SeasonType[] | undefined = [];
    for(let i = 0; i < seasonTypesList.length; i++) {
        seasonTypes[i] = new SeasonType(seasonTypesList[i]);
    }
    return seasonTypes;
}

export async function callDeleteSeasonTypeRestEndpointsBySeasonTypeId(
    seasonTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasonTypes/v1?env=${env}&seasonTypeId=${seasonTypeId}` ;
    let deleteSeasonTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSeasonTypeResponse.status == 204;
}

export async function callDeleteSeasonTypeRestEndpointsBySeasonTypeName(
    seasonTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasonTypes/v1?env=${env}&seasonTypeName=${seasonTypeName}` ;
    let deleteSeasonTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSeasonTypeResponse.status == 204;
}