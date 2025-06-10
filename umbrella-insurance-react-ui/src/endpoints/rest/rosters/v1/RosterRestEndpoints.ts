import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Roster } from "../../../../models/rosters/v1/Roster";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateRosterRestEndpoints(
    roster: Roster, env: string, domain: string): Promise<Roster>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rosters/v1?env=${env}`;
    let data = JSON.stringify(roster as any, replacer);
    let createRosterResponse: AxiosResponse<Roster> = await axios.post(url, data, config);
    let roster1 = new Roster(createRosterResponse.data);
    return roster1;
}

export async function callReadRosterRestEndpointsByRosterId(
    rosterId: number, env: string, domain: string): Promise<Roster[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rosters/v1?env=${env}&rosterId=${rosterId}`;
    let rosters: Roster[] | undefined = [];
    try {
        let readRosterListResponse: AxiosResponse<Roster[]> = await axios.get(url, config);
        let rosterList = readRosterListResponse.data;
        for(let i = 0; i < rosterList.length; i++) {
            rosters[i] = new Roster(rosterList[i]);
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
    return rosters;
}

export async function callReadRosterRestEndpointsByRosterName(
    rosterName: string, env: string, domain: string): Promise<Roster[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rosters/v1?env=${env}&rosterName=${rosterName}`;
    let rosters: Roster[] | undefined = [];
    try {
        let readRosterListResponse: AxiosResponse<Roster[]> = await axios.get(url, config);
        let rosterList = readRosterListResponse.data;
        for(let i = 0; i < rosterList.length; i++) {
            rosters[i] = new Roster(rosterList[i]);
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
    return rosters;
}

export async function callUpdateRosterRestEndpoints(
    roster: Roster, env: string, domain: string): Promise<Roster[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rosters/v1?env=${env}`;
    let data = JSON.stringify(roster as any, replacer);
    let updateRosterListResponse: AxiosResponse<Roster[]> = await axios.put(url, data, config);
    let rosters: Roster[] | undefined = [];
    let rosterList = updateRosterListResponse.data;
    for(let i = 0; i < rosterList.length; i++) {
        rosters[i] = new Roster(rosterList[i]);
    }
    return rosters;
}

export async function callDeleteRosterRestEndpointsByRosterId(
    rosterId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rosters/v1?env=${env}&rosterId=${rosterId}` ;
    let deleteRosterResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRosterResponse.status == 204;
}

export async function callDeleteRosterRestEndpointsByRosterName(
    rosterName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rosters/v1?env=${env}&rosterName=${rosterName}` ;
    let deleteRosterResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRosterResponse.status == 204;
}