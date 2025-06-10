import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { MatchedPolicyState } from "../../../../../../../models/users/policies/matchedPolicies/matchedPolicyStates/v1/MatchedPolicyState";
import { replacer } from "../../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateMatchedPolicyStateRestEndpoints(
    matchedPolicyState: MatchedPolicyState, env: string, domain: string): Promise<MatchedPolicyState>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicyStates/v1?env=${env}`;
    let data = JSON.stringify(matchedPolicyState as any, replacer);
    let createMatchedPolicyStateResponse: AxiosResponse<MatchedPolicyState> = await axios.post(url, data, config);
    let matchedPolicyState1 = new MatchedPolicyState(createMatchedPolicyStateResponse.data);
    return matchedPolicyState1;
}

export async function callReadMatchedPolicyStateRestEndpointsByMatchedPolicyStateId(
    matchedPolicyStateId: number, env: string, domain: string): Promise<MatchedPolicyState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicyStates/v1?env=${env}&matchedPolicyStateId=${matchedPolicyStateId}`;
    let matchedPolicyStates: MatchedPolicyState[] | undefined = [];
    try {
        let readMatchedPolicyStateListResponse: AxiosResponse<MatchedPolicyState[]> = await axios.get(url, config);
        let matchedPolicyStateList = readMatchedPolicyStateListResponse.data;
        for(let i = 0; i < matchedPolicyStateList.length; i++) {
            matchedPolicyStates[i] = new MatchedPolicyState(matchedPolicyStateList[i]);
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
    return matchedPolicyStates;
}

export async function callReadMatchedPolicyStateRestEndpointsByMatchedPolicyStateName(
    matchedPolicyStateName: string, env: string, domain: string): Promise<MatchedPolicyState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicyStates/v1?env=${env}&matchedPolicyStateName=${matchedPolicyStateName}`;
    let matchedPolicyStates: MatchedPolicyState[] | undefined = [];
    try {
        let readMatchedPolicyStateListResponse: AxiosResponse<MatchedPolicyState[]> = await axios.get(url, config);
        let matchedPolicyStateList = readMatchedPolicyStateListResponse.data;
        for(let i = 0; i < matchedPolicyStateList.length; i++) {
            matchedPolicyStates[i] = new MatchedPolicyState(matchedPolicyStateList[i]);
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
    return matchedPolicyStates;
}

export async function callUpdateMatchedPolicyStateRestEndpoints(
    matchedPolicyState: MatchedPolicyState, env: string, domain: string): Promise<MatchedPolicyState[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicyStates/v1?env=${env}`;
    let data = JSON.stringify(matchedPolicyState as any, replacer);
    let updateMatchedPolicyStateListResponse: AxiosResponse<MatchedPolicyState[]> = await axios.put(url, data, config);
    let matchedPolicyStateList = updateMatchedPolicyStateListResponse.data;
    let matchedPolicyStates: MatchedPolicyState[] | undefined = [];
    for(let i = 0; i < matchedPolicyStateList.length; i++) {
        matchedPolicyStates[i] = new MatchedPolicyState(matchedPolicyStateList[i]);
    }
    return matchedPolicyStates;
}

export async function callDeleteMatchedPolicyStateRestEndpointsByMatchedPolicyStateId(
    matchedPolicyStateId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicyStates/v1?env=${env}&matchedPolicyStateId=${matchedPolicyStateId}` ;
    let deleteMatchedPolicyStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedPolicyStateResponse.status == 204;
}

export async function callDeleteMatchedPolicyStateRestEndpointsByMatchedPolicyStateName(
    matchedPolicyStateName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicyStates/v1?env=${env}&matchedPolicyStateName=${matchedPolicyStateName}` ;
    let deleteMatchedPolicyStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedPolicyStateResponse.status == 204;
}