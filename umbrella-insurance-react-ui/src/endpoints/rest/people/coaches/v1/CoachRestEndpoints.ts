import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Coach } from "../../../../../models/people/coaches/v1/Coach";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateCoachRestEndpoints(
    coach: Coach, env: string, domain: string): Promise<Coach>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/coaches/v1?env=${env}`;
    let data = JSON.stringify(coach as any, replacer);
    let createCoachResponse: AxiosResponse<Coach> = await axios.post(url, data, config);
    let coach1 = new Coach(createCoachResponse.data);
    return coach1;
}

export async function callReadCoachRestEndpointsByCoachId(
    coachId: number, env: string, domain: string): Promise<Coach[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/coaches/v1?env=${env}&coachId=${coachId}`;
    let coachs: Coach[] | undefined = [];
    try {
        let readCoachListResponse: AxiosResponse<Coach[]> = await axios.get(url, config);
        let coachsList = readCoachListResponse.data;
        for(let i = 0; i < coachsList.length; i++) {
            coachs[i] = new Coach(coachsList[i]);
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
    return coachs;
}

export async function callReadCoachRestEndpointsByCoachName(
    coachName: string, env: string, domain: string): Promise<Coach[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/coaches/v1?env=${env}&coachName=${coachName}`;
    let coachs: Coach[] | undefined = [];
    try {
        let readCoachListResponse: AxiosResponse<Coach[]> = await axios.get(url, config);
        let coachsList = readCoachListResponse.data;
        for(let i = 0; i < coachsList.length; i++) {
            coachs[i] = new Coach(coachsList[i]);
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
    return coachs;
}

export async function callUpdateCoachRestEndpoints(
    coach: Coach, env: string, domain: string): Promise<Coach[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/coaches/v1?env=${env}`;
    let data = JSON.stringify(coach as any, replacer);
    let updateCoachListResponse: AxiosResponse<Coach[]> = await axios.put(url, data, config);
    let coachsList = updateCoachListResponse.data;
    let coaches: Coach[] | undefined = [];
    for(let i = 0; i < coachsList.length; i++) {
        coaches[i] = new Coach(coachsList[i]);
    }
    return coaches;
}

export async function callDeleteCoachRestEndpointsByCoachId(
    coachId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/coaches/v1?env=${env}&coachId=${coachId}` ;
    let deleteCoachResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCoachResponse.status == 204;
}

export async function callDeleteCoachRestEndpointsByCoachName(
    coachName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/coaches/v1?env=${env}&coachName=${coachName}` ;
    let deleteCoachResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCoachResponse.status == 204;
}