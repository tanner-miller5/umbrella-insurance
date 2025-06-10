import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Season } from "../../../../models/seasons/v1/Season";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateSeasonRestEndpoints(
    season: Season, env: string, domain: string): Promise<Season>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasons/v1?env=${env}`;
    let data = JSON.stringify(season as any, replacer);
    let createSeasonResponse: AxiosResponse<Season> = await axios.post(url, data, config);
    let season1 = new Season(createSeasonResponse.data);
    return season1;
}

export async function callReadSeasonRestEndpointsBySeasonId(
    seasonId: number, env: string, domain: string): Promise<Season[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasons/v1?env=${env}&seasonId=${seasonId}`;
    let seasons: Season[] | undefined = [];
    try {
        let readSeasonListResponse: AxiosResponse<Season[]> = await axios.get(url, config);
        let seasonList = readSeasonListResponse.data;
        for (let i = 0; i < seasonList.length; i++) {
            seasons[i] = new Season(seasonList[i]);
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
    return seasons;
}

export async function callReadSeasonRestEndpointsBySeasonName(
    seasonName: string, env: string, domain: string): Promise<Season[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasons/v1?env=${env}&seasonName=${seasonName}`;
    let seasons: Season[] | undefined = [];
    try {
        let readSeasonListResponse: AxiosResponse<Season[]> = await axios.get(url, config);
        let seasonList = readSeasonListResponse.data;
        for (let i = 0; i < seasonList.length; i++) {
            seasons[i] = new Season(seasonList[i]);
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
    return seasons;
}

export async function callUpdateSeasonRestEndpoints(
    season: Season, env: string, domain: string): Promise<Season[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasons/v1?env=${env}`;
    let data = JSON.stringify(season as any, replacer);
    let updateSeasonListResponse: AxiosResponse<Season[]> = await axios.put(url, data, config);
    let seasonList = updateSeasonListResponse.data;
    let seasons: Season[] = [];
    for (let i = 0; i < seasonList.length; i++) {
        seasons[i] = new Season(seasonList[i]);
    }
    return seasons;
}

export async function callDeleteSeasonRestEndpointsBySeasonId(
    seasonId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasons/v1?env=${env}&seasonId=${seasonId}` ;
    let deleteSeasonResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSeasonResponse.status == 204;
}

export async function callDeleteSeasonRestEndpointsBySeasonName(
    seasonName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/seasons/v1?env=${env}&seasonName=${seasonName}` ;
    let deleteSeasonResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSeasonResponse.status == 204;
}