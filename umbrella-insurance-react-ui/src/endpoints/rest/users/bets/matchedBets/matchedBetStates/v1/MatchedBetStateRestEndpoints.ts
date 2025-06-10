import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { MatchedBetState } from "../../../../../../../models/users/bets/matchedBets/matchedBetStates/v1/MatchedBetState";
import { replacer } from "../../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateMatchedBetStateRestEndpoints(
    matchedBetState: MatchedBetState, env: string, domain: string): Promise<MatchedBetState>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBetStates/v1?env=${env}`;
    let data = JSON.stringify(matchedBetState as any, replacer);
    let createMatchedBetStateResponse: AxiosResponse<MatchedBetState> = await axios.post(url, data, config);
    let matchedBetState1 = new MatchedBetState(createMatchedBetStateResponse.data);
    return matchedBetState1;
}

export async function callReadMatchedBetStateRestEndpointsByMatchedBetStateId(
    matchedBetStateId: number, env: string, domain: string): Promise<MatchedBetState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBetStates/v1?env=${env}&matchedBetStateId=${matchedBetStateId}`;
    let matchedBetStates: MatchedBetState[] | undefined = [];
    try {
        let readMatchedBetStateListResponse: AxiosResponse<MatchedBetState[]> = await axios.get(url, config);
        let matchedBetStateList = readMatchedBetStateListResponse.data;
        for(let i = 0; i < matchedBetStateList.length; i++) {
            matchedBetStates[i] = new MatchedBetState(matchedBetStateList[i]);
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
    return matchedBetStates;
}

export async function callReadMatchedBetStateRestEndpointsByMatchedBetStateName(
    matchedBetStateName: string, env: string, domain: string): Promise<MatchedBetState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBetStates/v1?env=${env}&matchedBetStateName=${matchedBetStateName}`;
    let matchedBetStates: MatchedBetState[] | undefined = [];
    try {
        let readMatchedBetStateListResponse: AxiosResponse<MatchedBetState[]> = await axios.get(url, config);
        let matchedBetStateList = readMatchedBetStateListResponse.data;
        for(let i = 0; i < matchedBetStateList.length; i++) {
            matchedBetStates[i] = new MatchedBetState(matchedBetStateList[i]);
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
    return matchedBetStates;
}

export async function callUpdateMatchedBetStateRestEndpoints(
    matchedBetState: MatchedBetState, env: string, domain: string): Promise<MatchedBetState[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBetStates/v1?env=${env}`;
    let data = JSON.stringify(matchedBetState as any, replacer);
    let updateMatchedBetStateListResponse: AxiosResponse<MatchedBetState[]> = await axios.put(url, data, config);
    let matchedBetStateList = updateMatchedBetStateListResponse.data;
    let matchedBetStates: MatchedBetState[] | undefined = [];
    for(let i = 0; i < matchedBetStateList.length; i++) {
        matchedBetStates[i] = new MatchedBetState(matchedBetStateList[i]);
    }
    return matchedBetStates;
}

export async function callDeleteMatchedBetStateRestEndpointsByMatchedBetStateId(
    matchedBetStateId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBetStates/v1?env=${env}&matchedBetStateId=${matchedBetStateId}` ;
    let deleteMatchedBetStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedBetStateResponse.status == 204;
}

export async function callDeleteMatchedBetStateRestEndpointsByMatchedBetStateName(
    matchedBetStateName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedBetStates/v1?env=${env}&matchedBetStateName=${matchedBetStateName}` ;
    let deleteMatchedBetStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedBetStateResponse.status == 204;
}