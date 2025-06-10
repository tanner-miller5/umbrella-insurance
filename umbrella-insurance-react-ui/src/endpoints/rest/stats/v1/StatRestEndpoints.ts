import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { Stat } from "../../../../models/stats/v1/Stat";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";

export async function callCreateStatRestEndpoints(
    stat: Stat, env: string, domain: string): Promise<Stat>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/stats/v1?env=${env}`;
    let data = JSON.stringify(stat as any, replacer);
    let createStatResponse: AxiosResponse<Stat> = await axios.post(url, data, config);
    let stat1 = new Stat(createStatResponse.data);
    return stat1;
}

export async function callReadAllStatsRestEndpoint(
    env: string, domain: string): Promise<Stat[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/stats/v1?env=${env}`;
    let stats: Stat[] | undefined = [];
    try {
        let readFaqListResponse: AxiosResponse<Stat[]> = await axios.get(url, config);
        for (let i = 0; i < readFaqListResponse.data.length; i++) {
            stats[i] = new Stat(readFaqListResponse.data[i]);
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
    return stats;
}

export async function callReadStatRestEndpointsByStatId(
    statId: number, env: string, domain: string): Promise<Stat[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/stats/v1?env=${env}&statId=${statId}`;
    let stats: Stat[] | undefined = [];
    try {
        let readStatListResponse: AxiosResponse<Stat[]> = await axios.get(url, config);
        for (let i = 0; i < readStatListResponse.data.length; i++) {
            stats[i] = new Stat(readStatListResponse.data[i]);
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
    return stats;
}

export async function callReadStatRestEndpointsByStatName(
    statName: string, env: string, domain: string): Promise<Stat[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/stats/v1?env=${env}&statName=${statName}`;
    let stats: Stat[] | undefined = [];
    try {
        let readStatListResponse: AxiosResponse<Stat[]> = await axios.get(url, config);
        for (let i = 0; i < readStatListResponse.data.length; i++) {
            stats[i] = new Stat(readStatListResponse.data[i]);
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
    return stats;
}

export async function callUpdateStatRestEndpoints(
    stat: Stat, env: string, domain: string): Promise<Stat[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/stats/v1?env=${env}`;
    let stats: Stat[] = [];
    let data = JSON.stringify(stat as any, replacer);
    let updateStatListResponse: AxiosResponse<Stat[]> = await axios.put(url, data, config);
    for (let i = 0; i < updateStatListResponse.data.length; i++) {
        stats[i] = new Stat(updateStatListResponse.data[i]);
    }
    return stats;
}

export async function callDeleteStatRestEndpointsByStatId(
    statId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/stats/v1?env=${env}&statId=${statId}` ;
    let deleteStatResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteStatResponse.status == 204;
}

export async function callDeleteStatRestEndpointsByStatName(
    statName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/stats/v1?env=${env}&statName=${statName}` ;
    let deleteStatResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteStatResponse.status == 204;
}