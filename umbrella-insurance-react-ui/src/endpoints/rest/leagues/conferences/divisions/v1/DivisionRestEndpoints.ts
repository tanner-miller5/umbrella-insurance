import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { Division } from "../../../../../../models/leagues/conferences/divisions/v1/Division";
import { replacer } from "../../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateDivisionRestEndpoints(
    division: Division, env: string, domain: string): Promise<Division>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/divisions/v1?env=${env}`;
    let data = JSON.stringify(division as any, replacer);
    let createDivisionResponse: AxiosResponse<Division> = await axios.post(url, data, config);
    let division1 = new Division(createDivisionResponse.data);
    return division1;
}

export async function callReadDivisionRestEndpointsByDivisionId(
    divisionId: number, env: string, domain: string): Promise<Division[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/divisions/v1?env=${env}&divisionId=${divisionId}`;
    let divisions: Division[] | undefined = [];
    try {
        let readDivisionListResponse: AxiosResponse<Division[]> = await axios.get(url, config);
        let divisionList = readDivisionListResponse.data;
        for(let i = 0; i < divisionList.length; i++) {
            divisions[i] = new Division(divisionList[i]);
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
    return divisions;
}

export async function callReadDivisionRestEndpointsByDivisionName(
    divisionName: string, env: string, domain: string): Promise<Division[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/divisions/v1?env=${env}&divisionName=${divisionName}`;
    let divisions: Division[] | undefined = [];
    try {
        let readDivisionListResponse: AxiosResponse<Division[]> = await axios.get(url, config);
        let divisionList = readDivisionListResponse.data;
        for(let i = 0; i < divisionList.length; i++) {
            divisions[i] = new Division(divisionList[i]);
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
    return divisions;
}

export async function callUpdateDivisionRestEndpoints(
    division: Division, env: string, domain: string): Promise<Division[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/divisions/v1?env=${env}`;
    let data = JSON.stringify(division as any, replacer);
    let updateDivisionListResponse: AxiosResponse<Division[]> = await axios.put(url, data, config);
    let divisionList = updateDivisionListResponse.data;
    let divisions: Division[] | undefined = [];
    for(let i = 0; i < divisionList.length; i++) {
        divisions[i] = new Division(divisionList[i]);
    }
    return divisions;
}

export async function callDeleteDivisionRestEndpointsByDivisionId(
    divisionId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/divisions/v1?env=${env}&divisionId=${divisionId}` ;
    let deleteDivisionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteDivisionResponse.status == 204;
}

export async function callDeleteDivisionRestEndpointsByDivisionName(
    divisionName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/divisions/v1?env=${env}&divisionName=${divisionName}` ;
    let deleteDivisionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteDivisionResponse.status == 204;
}