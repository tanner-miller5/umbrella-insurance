import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Continent } from "../../../../../models/geographies/continents/v1/Continent";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateContinentRestEndpoints(
    continent: Continent, env: string, domain: string): Promise<Continent>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/continents/v1?env=${env}`;
    let data = JSON.stringify(continent as any, replacer);
    let createContinentResponse: AxiosResponse<Continent> = await axios.post(url, data, config);
    let continent1 = new Continent(createContinentResponse.data);
    return continent1;
}

export async function callReadContinentRestEndpointsByContinentId(
    continentId: number, env: string, domain: string): Promise<Continent[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/continents/v1?env=${env}&continentId=${continentId}`;
    let continents: Continent[] | undefined = [];
    try {
        let readContinentListResponse: AxiosResponse<Continent[]> = await axios.get(url, config);
        let continentsList = readContinentListResponse.data;
        for(let i = 0; i < continentsList.length; i++) {
            continents[i] = new Continent(continentsList[i]);
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
    return continents;
}

export async function callReadContinentRestEndpointsByContinentName(
    continentName: string, env: string, domain: string): Promise<Continent[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/continents/v1?env=${env}&continentName=${continentName}`;
    let continents: Continent[] | undefined = [];
    try {
        let readContinentListResponse: AxiosResponse<Continent[]> = await axios.get(url, config);
        let continentsList = readContinentListResponse.data;
        for(let i = 0; i < continentsList.length; i++) {
            continents[i] = new Continent(continentsList[i]);
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
    return continents;
}

export async function callUpdateContinentRestEndpoints(
    continent: Continent, env: string, domain: string): Promise<Continent[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/continents/v1?env=${env}`;
    let data = JSON.stringify(continent as any, replacer);
    let updateContinentListResponse: AxiosResponse<Continent[]> = await axios.put(url, data, config);
    let continents: Continent[] | undefined = [];
    let continentsList = updateContinentListResponse.data;
    for(let i = 0; i < continentsList.length; i++) {
        continents[i] = new Continent(continentsList[i]);
    }
    return continents;
}

export async function callDeleteContinentRestEndpointsByContinentId(
    continentId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/continents/v1?env=${env}&continentId=${continentId}` ;
    let deleteContinentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteContinentResponse.status == 204;
}

export async function callDeleteContinentRestEndpointsByContinentName(
    continentName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/continents/v1?env=${env}&continentName=${continentName}` ;
    let deleteContinentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteContinentResponse.status == 204;
}