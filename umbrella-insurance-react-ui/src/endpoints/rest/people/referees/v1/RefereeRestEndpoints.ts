import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Referee } from "../../../../../models/people/referees/v1/Referee";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateRefereeRestEndpoints(
    referee: Referee, env: string, domain: string): Promise<Referee>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referees/v1?env=${env}`;
    let data = JSON.stringify(referee as any, replacer);
    let createRefereeResponse: AxiosResponse<Referee> = await axios.post(url, data, config);
    let referee1 = new Referee(createRefereeResponse.data);
    return referee1;
}

export async function callReadRefereeRestEndpointsByRefereeId(
    refereeId: number, env: string, domain: string): Promise<Referee[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referees/v1?env=${env}&refereeId=${refereeId}`;
    let referees: Referee[] | undefined = [];
    try {
        let readRefereeListResponse: AxiosResponse<Referee[]> = await axios.get(url, config);
        let refereeList = readRefereeListResponse.data;
        for (let i = 0; i < refereeList.length; i++) {
            referees[i] = new Referee(refereeList[i]);
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
    return referees;
}

export async function callReadRefereeRestEndpointsByPersonId(
    personId: number, env: string, domain: string): Promise<Referee[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referees/v1?env=${env}&personId=${personId}`;
    let referees: Referee[] | undefined = [];
    try {
        let readRefereeListResponse: AxiosResponse<Referee[]> = await axios.get(url, config);
        let refereeList = readRefereeListResponse.data;
        for (let i = 0; i < refereeList.length; i++) {
            referees[i] = new Referee(refereeList[i]);
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
    return referees;
}

export async function callUpdateRefereeRestEndpoints(
    referee: Referee, env: string, domain: string): Promise<Referee[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referees/v1?env=${env}`;
    let data = JSON.stringify(referee as any, replacer);
    let updateRefereeListResponse: AxiosResponse<Referee[]> = await axios.put(url, data, config);
    let refereeList = updateRefereeListResponse.data;
    let referees: Referee[] | undefined = [];
    for (let i = 0; i < refereeList.length; i++) {
        referees[i] = new Referee(refereeList[i]);
    }
    return referees;
}

export async function callDeleteRefereeRestEndpointsByRefereeId(
    refereeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referees/v1?env=${env}&refereeId=${refereeId}` ;
    let deleteRefereeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRefereeResponse.status == 204;
}

export async function callDeleteRefereeRestEndpointsByPersonId(
    personId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referees/v1?env=${env}&personId=${personId}` ;
    let deleteRefereeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRefereeResponse.status == 204;
}