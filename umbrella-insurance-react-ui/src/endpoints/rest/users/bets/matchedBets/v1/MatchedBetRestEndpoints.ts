import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { MatchedBet } from "../../../../../../models/users/bets/matchedBets/v1/MatchedBet";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateMatchedBetRestEndpoints(
    matchedBet: MatchedBet, env: string, domain: string): Promise<MatchedBet>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBets/v1?env=${env}`;
    let data = JSON.stringify(matchedBet as any, replacer);
    let createMatchedBetResponse: AxiosResponse<MatchedBet> = await axios.post(url, data, config);
    let matchedBet1 = new MatchedBet(createMatchedBetResponse.data);
    return matchedBet1;
}

export async function callReadMatchedBetRestEndpointsByMatchedBetId(
    matchedBetId: number, env: string, domain: string): Promise<MatchedBet[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBets/v1?env=${env}&matchedBetId=${matchedBetId}`;
    let matchedBets: MatchedBet[] | undefined = [];
    try {
        let readMatchedBetListResponse: AxiosResponse<MatchedBet[]> = await axios.get(url, config);
        let matchedBetList = readMatchedBetListResponse.data;
        for(let i = 0; i < matchedBetList.length; i++) {
            matchedBets[i] = new MatchedBet(matchedBetList[i]);
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
    return matchedBets;
}

export async function callReadMatchedBetRestEndpointsByMatchedBetName(
    matchedBetName: string, env: string, domain: string): Promise<MatchedBet[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBets/v1?env=${env}&matchedBetName=${matchedBetName}`;
    let matchedBets: MatchedBet[] | undefined = [];
    try {
        let readMatchedBetListResponse: AxiosResponse<MatchedBet[]> = await axios.get(url, config);
        let matchedBetList = readMatchedBetListResponse.data;
        for(let i = 0; i < matchedBetList.length; i++) {
            matchedBets[i] = new MatchedBet(matchedBetList[i]);
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
    return matchedBets;
}

export async function callUpdateMatchedBetRestEndpoints(
    matchedBet: MatchedBet, env: string, domain: string): Promise<MatchedBet[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBets/v1?env=${env}`;
    let data = JSON.stringify(matchedBet as any, replacer);
    let updateMatchedBetListResponse: AxiosResponse<MatchedBet[]> = await axios.put(url, data, config);
    let matchedBetList = updateMatchedBetListResponse.data;
    let matchedBets: MatchedBet[] | undefined = [];
    for(let i = 0; i < matchedBetList.length; i++) {
        matchedBets[i] = new MatchedBet(matchedBetList[i]);
    }
    return matchedBets;
}

export async function callDeleteMatchedBetRestEndpointsByMatchedBetId(
    matchedBetId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBets/v1?env=${env}&matchedBetId=${matchedBetId}` ;
    let deleteMatchedBetResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedBetResponse.status == 204;
}

export async function callDeleteMatchedBetRestEndpointsByMatchedBetName(
    matchedBetName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBets/v1?env=${env}&matchedBetName=${matchedBetName}` ;
    let deleteMatchedBetResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedBetResponse.status == 204;
}