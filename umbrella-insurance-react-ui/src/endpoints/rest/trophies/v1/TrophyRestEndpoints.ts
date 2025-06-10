import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Trophy } from "../../../../models/trophies/v1/Trophy";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateTrophyRestEndpoints(
    trophy: Trophy, env: string, domain: string): Promise<Trophy>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/trophies/v1?env=${env}`;
    let data = JSON.stringify(trophy as any, replacer);
    let createTrophyResponse: AxiosResponse<Trophy> = await axios.post(url, data, config);
    let trophy1 = new Trophy(createTrophyResponse.data);
    return trophy1;
}

export async function callReadTrophyRestEndpointsByTrophyId(
    trophyId: number, env: string, domain: string): Promise<Trophy[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/trophies/v1?env=${env}&trophyId=${trophyId}`;
    let trophys: Trophy[] | undefined = [];
    try {
        let readTrophyListResponse: AxiosResponse<Trophy[]> = await axios.get(url, config);
        let trophyList = readTrophyListResponse.data;
        for(let i = 0; i < trophyList.length; i++) {
            trophys[i] = new Trophy(trophyList[i]);
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
    return trophys;
}

export async function callReadTrophyRestEndpointsByTrophyName(
    trophyName: string, env: string, domain: string): Promise<Trophy[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/trophies/v1?env=${env}&trophyName=${trophyName}`;
    let trophys: Trophy[] | undefined = [];
    try {
        let readTrophyListResponse: AxiosResponse<Trophy[]> = await axios.get(url, config);
        let trophyList = readTrophyListResponse.data;
        for(let i = 0; i < trophyList.length; i++) {
            trophys[i] = new Trophy(trophyList[i]);
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
    return trophys;
}

export async function callUpdateTrophyRestEndpoints(
    trophy: Trophy, env: string, domain: string): Promise<Trophy[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/trophies/v1?env=${env}`;
    let data = JSON.stringify(trophy as any, replacer);
    let updateTrophyListResponse: AxiosResponse<Trophy[]> = await axios.put(url, data, config);
    let trophyList = updateTrophyListResponse.data;
    let trophys: Trophy[] | undefined = [];
    for(let i = 0; i < trophyList.length; i++) {
        trophys[i] = new Trophy(trophyList[i]);
    }
    return trophys;
}

export async function callDeleteTrophyRestEndpointsByTrophyId(
    trophyId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/trophies/v1?env=${env}&trophyId=${trophyId}` ;
    let deleteTrophyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTrophyResponse.status == 204;
}

export async function callDeleteTrophyRestEndpointsByTrophyName(
    trophyName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/trophies/v1?env=${env}&trophyName=${trophyName}` ;
    let deleteTrophyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTrophyResponse.status == 204;
}