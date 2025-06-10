import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { FootballPlayerStats } from "../../../../../../models/stats/playerStats/footballPlayerStats/v1/FootballPlayerStats";
import { replacer } from "../../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";


export async function callCreateFootballPlayerStatsRestEndpoints(
    footballPlayerStats: FootballPlayerStats, env: string, domain: string): Promise<FootballPlayerStats>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballPlayerStats/v1?env=${env}`;
    let data = JSON.stringify(footballPlayerStats as any, replacer);
    let createFootballPlayerStatsResponse: AxiosResponse<FootballPlayerStats> = await axios.post(url, data, config);
    let footballPlayerStats1 = new FootballPlayerStats(createFootballPlayerStatsResponse.data);
    return footballPlayerStats1;
}

export async function callReadFootballPlayerStatsRestEndpointsByFootballPlayerStatsId(
    footballPlayerStatsId: number, env: string, domain: string): Promise<FootballPlayerStats[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballPlayerStats/v1?env=${env}&footballPlayerStatsId=${footballPlayerStatsId}`;
    let footballPlayerStatss: FootballPlayerStats[] | undefined = [];
    try {
        let readFootballPlayerStatsListResponse: AxiosResponse<FootballPlayerStats[]> = await axios.get(url, config);
        let footballPlayerStatsList = readFootballPlayerStatsListResponse.data;
        for (let i = 0; i < footballPlayerStatsList.length; i++) {
            footballPlayerStatss[i] = new FootballPlayerStats(footballPlayerStatsList[i]);
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
    return footballPlayerStatss;
}

export async function callReadFootballPlayerStatsRestEndpointsByFootballPlayerStatsName(
    footballPlayerStatsName: string, env: string, domain: string): Promise<FootballPlayerStats[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballPlayerStats/v1?env=${env}&footballPlayerStatsName=${footballPlayerStatsName}`;
    let footballPlayerStatss: FootballPlayerStats[] | undefined = [];
    try {
        let readFootballPlayerStatsListResponse: AxiosResponse<FootballPlayerStats[]> = await axios.get(url, config);
        let footballPlayerStatsList = readFootballPlayerStatsListResponse.data;
        for (let i = 0; i < footballPlayerStatsList.length; i++) {
            footballPlayerStatss[i] = new FootballPlayerStats(footballPlayerStatsList[i]);
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
    return footballPlayerStatss;
}

export async function callUpdateFootballPlayerStatsRestEndpoints(
    footballPlayerStats: FootballPlayerStats, env: string, domain: string): Promise<FootballPlayerStats[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballPlayerStats/v1?env=${env}`;
    let data = JSON.stringify(footballPlayerStats as any, replacer);
    let updateFootballPlayerStatsListResponse: AxiosResponse<FootballPlayerStats[]> = await axios.put(url, data, config);
    let footballPlayerStatss: FootballPlayerStats[] | undefined = [];
    let footballPlayerStatsList = updateFootballPlayerStatsListResponse.data;
    for (let i = 0; i < footballPlayerStatsList.length; i++) {
        footballPlayerStatss[i] = new FootballPlayerStats(footballPlayerStatsList[i]);
    }
    return footballPlayerStatss;
}

export async function callDeleteFootballPlayerStatsRestEndpointsByFootballPlayerStatsId(
    footballPlayerStatsId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballPlayerStats/v1?env=${env}&footballPlayerStatsId=${footballPlayerStatsId}` ;
    let deleteFootballPlayerStatsResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFootballPlayerStatsResponse.status == 204;
}

export async function callDeleteFootballPlayerStatsRestEndpointsByFootballPlayerStatsName(
    footballPlayerStatsName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballPlayerStats/v1?env=${env}&footballPlayerStatsName=${footballPlayerStatsName}` ;
    let deleteFootballPlayerStatsResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFootballPlayerStatsResponse.status == 204;
}