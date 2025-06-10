import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { FootballTeamStats } from "../../../../../../models/stats/teamStats/footballTeamStats/v1/FootballTeamStats";
import { replacer } from "../../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateFootballTeamStatsRestEndpoints(
    footballTeamStats: FootballTeamStats, env: string, domain: string): Promise<FootballTeamStats>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballTeamStats/v1?env=${env}`;
    let data = JSON.stringify(footballTeamStats as any, replacer);
    let createFootballTeamStatsResponse: AxiosResponse<FootballTeamStats> = await axios.post(url, data, config);
    let footballTeamStats1 = new FootballTeamStats(createFootballTeamStatsResponse.data);
    return footballTeamStats1;
}

export async function callReadFootballTeamStatsRestEndpointsByFootballTeamStatsId(
    footballTeamStatsId: number, env: string, domain: string): Promise<FootballTeamStats[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballTeamStats/v1?env=${env}&footballTeamStatsId=${footballTeamStatsId}`;
    let footballTeamStatss: FootballTeamStats[] | undefined = [];
    try {
        let readFootballTeamStatsListResponse: AxiosResponse<FootballTeamStats[]> = await axios.get(url, config);
        let footballTeamStatsList = readFootballTeamStatsListResponse.data;
        for(let i = 0; i < footballTeamStatsList.length; i++) {
            footballTeamStatss[i] = new FootballTeamStats(footballTeamStatsList[i]);
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
    return footballTeamStatss;
}

export async function callReadFootballTeamStatsRestEndpointsByFootballTeamStatsName(
    footballTeamStatsName: string, env: string, domain: string): Promise<FootballTeamStats[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballTeamStats/v1?env=${env}&footballTeamStatsName=${footballTeamStatsName}`;
    let footballTeamStatss: FootballTeamStats[] | undefined = [];
    try {
        let readFootballTeamStatsListResponse: AxiosResponse<FootballTeamStats[]> = await axios.get(url, config);
        let footballTeamStatsList = readFootballTeamStatsListResponse.data;
        for(let i = 0; i < footballTeamStatsList.length; i++) {
            footballTeamStatss[i] = new FootballTeamStats(footballTeamStatsList[i]);
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
    return footballTeamStatss;
}

export async function callUpdateFootballTeamStatsRestEndpoints(
    footballTeamStats: FootballTeamStats, env: string, domain: string): Promise<FootballTeamStats[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballTeamStats/v1?env=${env}`;
    let data = JSON.stringify(footballTeamStats as any, replacer);
    let updateFootballTeamStatsListResponse: AxiosResponse<FootballTeamStats[]> = await axios.put(url, data, config);
    let footballTeamStatsList = updateFootballTeamStatsListResponse.data;
    let footballTeamStatss: FootballTeamStats[] | undefined = [];
    for(let i = 0; i < footballTeamStatsList.length; i++) {
        footballTeamStatss[i] = new FootballTeamStats(footballTeamStatsList[i]);
    }
    return footballTeamStatss;
}

export async function callDeleteFootballTeamStatsRestEndpointsByFootballTeamStatsId(
    footballTeamStatsId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballTeamStats/v1?env=${env}&footballTeamStatsId=${footballTeamStatsId}` ;
    let deleteFootballTeamStatsResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFootballTeamStatsResponse.status == 204;
}

export async function callDeleteFootballTeamStatsRestEndpointsByFootballTeamStatsName(
    footballTeamStatsName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/footballTeamStats/v1?env=${env}&footballTeamStatsName=${footballTeamStatsName}` ;
    let deleteFootballTeamStatsResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFootballTeamStatsResponse.status == 204;
}