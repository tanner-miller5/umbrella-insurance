import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { League } from "../../../../models/leagues/v1/League";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateLeagueRestEndpoints(
    league: League, env: string, domain: string): Promise<League>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/leagues/v1?env=${env}`;
    let data = JSON.stringify(league as any, replacer);
    let createLeagueResponse: AxiosResponse<League> = await axios.post(url, data, config);
    let league1 = new League(createLeagueResponse.data);
    return league1;
}

export async function callReadLeagueRestEndpointsByLeagueId(
    leagueId: number, env: string, domain: string): Promise<League[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/leagues/v1?env=${env}&leagueId=${leagueId}`;
    let leagues: League[] | undefined = [];
    try {
        let readLeagueListResponse: AxiosResponse<League[]> = await axios.get(url, config);
        let leagueList = readLeagueListResponse.data;
        for(let i = 0; i < leagueList.length; i++) {
            leagues[i] = new League(leagueList[i]);
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
    return leagues;
}

export async function callReadLeagueRestEndpointsByLeagueName(
    leagueName: string, env: string, domain: string): Promise<League[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/leagues/v1?env=${env}&leagueName=${leagueName}`;
    let leagues: League[] | undefined = [];
    try {
        let readLeagueListResponse: AxiosResponse<League[]> = await axios.get(url, config);
        let leagueList = readLeagueListResponse.data;
        for(let i = 0; i < leagueList.length; i++) {
            leagues[i] = new League(leagueList[i]);
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
    return leagues;
}

export async function callUpdateLeagueRestEndpoints(
    league: League, env: string, domain: string): Promise<League[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/leagues/v1?env=${env}`;
    let data = JSON.stringify(league as any, replacer);
    let updateLeagueListResponse: AxiosResponse<League[]> = await axios.put(url, data, config);
    let leagueList = updateLeagueListResponse.data;
    let leagues: League[] = [];
    for(let i = 0; i < leagueList.length; i++) {
        leagues[i] = new League(leagueList[i]);
    }
    return leagues;
}

export async function callDeleteLeagueRestEndpointsByLeagueId(
    leagueId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/leagues/v1?env=${env}&leagueId=${leagueId}` ;
    let deleteLeagueResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteLeagueResponse.status == 204;
}

export async function callDeleteLeagueRestEndpointsByLeagueName(
    leagueName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/leagues/v1?env=${env}&leagueName=${leagueName}` ;
    let deleteLeagueResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteLeagueResponse.status == 204;
}