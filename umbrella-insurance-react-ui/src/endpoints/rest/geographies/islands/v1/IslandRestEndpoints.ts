import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { Island } from "../../../../../models/geographies/islands/v1/Island";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateIslandRestEndpoints(
    island: Island, env: string, domain: string): Promise<Island>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/islands/v1?env=${env}`;
    let data = JSON.stringify(island as any, replacer);
    let createIslandResponse: AxiosResponse<Island> = await axios.post(url, data, config);
    let island1 = new Island(createIslandResponse.data);
    return island1;
}

export async function callReadIslandRestEndpointsByIslandId(
    islandId: number, env: string, domain: string): Promise<Island[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/islands/v1?env=${env}&islandId=${islandId}`;
    let islands: Island[] | undefined = [];
    try {
        let readIslandListResponse: AxiosResponse<Island[]> = await axios.get(url, config);
        let islandsList = readIslandListResponse.data;
        for (let i = 0; i < islandsList.length; i++) {
            islands[i] = new Island(islandsList[i]);
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
    return islands;
}

export async function callReadIslandRestEndpointsByIslandName(
    islandName: string, env: string, domain: string): Promise<Island[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/islands/v1?env=${env}&islandName=${islandName}`;
    let islands: Island[] | undefined = [];
    try {
        let readIslandListResponse: AxiosResponse<Island[]> = await axios.get(url, config);
        let islandsList = readIslandListResponse.data;
        for (let i = 0; i < islandsList.length; i++) {
            islands[i] = new Island(islandsList[i]);
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
    return islands;
}

export async function callUpdateIslandRestEndpoints(
    island: Island, env: string, domain: string): Promise<Island[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/islands/v1?env=${env}`;
    let data = JSON.stringify(island as any, replacer);
    let updateIslandListResponse: AxiosResponse<Island[]> = await axios.put(url, data, config);
    let islandsList = updateIslandListResponse.data;
    let islands: Island[] | undefined = [];
    for (let i = 0; i < islandsList.length; i++) {
        islands[i] = new Island(islandsList[i]);
    }
    return islands;
}

export async function callDeleteIslandRestEndpointsByIslandId(
    islandId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/islands/v1?env=${env}&islandId=${islandId}` ;
    let deleteIslandResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteIslandResponse.status == 204;
}

export async function callDeleteIslandRestEndpointsByIslandName(
    islandName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/islands/v1?env=${env}&islandName=${islandName}` ;
    let deleteIslandResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteIslandResponse.status == 204;
}