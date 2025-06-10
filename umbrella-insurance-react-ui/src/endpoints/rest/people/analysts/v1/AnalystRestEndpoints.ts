import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Analyst } from "../../../../../models/people/analysts/v1/Analyst";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateAnalystRestEndpoints(
    analyst: Analyst, env: string, domain: string): Promise<Analyst>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/analysts/v1?env=${env}`;
    let data = JSON.stringify(analyst as any, replacer);
    let createAnalystResponse: AxiosResponse<Analyst> = await axios.post(url, data, config);
    let analyst1 = new Analyst(createAnalystResponse.data);
    return analyst1;
}

export async function callReadAnalystRestEndpointsByAnalystId(
    analystId: number, env: string, domain: string): Promise<Analyst[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/analysts/v1?env=${env}&analystId=${analystId}`;
    let analysts: Analyst[] | undefined = [];
    try {
        let readAnalystListResponse: AxiosResponse<Analyst[]> = await axios.get(url, config);
        let analystsList = readAnalystListResponse.data;
        for(let i = 0; i < analystsList.length; i++) {
            analysts[i] = new Analyst(analystsList[i]);
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
    return analysts;
}

export async function callReadAnalystRestEndpointsByAnalystName(
    analystName: string, env: string, domain: string): Promise<Analyst[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/analysts/v1?env=${env}&analystName=${analystName}`;
    let analysts: Analyst[] | undefined = [];
    try {
        let readAnalystListResponse: AxiosResponse<Analyst[]> = await axios.get(url, config);
        let analystsList = readAnalystListResponse.data;
        for(let i = 0; i < analystsList.length; i++) {
            analysts[i] = new Analyst(analystsList[i]);
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
    return analysts;
}

export async function callUpdateAnalystRestEndpoints(
    analyst: Analyst, env: string, domain: string): Promise<Analyst[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/analysts/v1?env=${env}`;
    let data = JSON.stringify(analyst as any, replacer);
    let updateAnalystListResponse: AxiosResponse<Analyst[]> = await axios.put(url, data, config);
    let analystsList = updateAnalystListResponse.data;
    let analysts: Analyst[] | undefined = [];
    for(let i = 0; i < analystsList.length; i++) {
        analysts[i] = new Analyst(analystsList[i]);
    }
    return analysts;
}

export async function callDeleteAnalystRestEndpointsByAnalystId(
    analystId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/analysts/v1?env=${env}&analystId=${analystId}` ;
    let deleteAnalystResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAnalystResponse.status == 204;
}

export async function callDeleteAnalystRestEndpointsByAnalystName(
    analystName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/analysts/v1?env=${env}&analystName=${analystName}` ;
    let deleteAnalystResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAnalystResponse.status == 204;
}