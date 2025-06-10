import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PendingBetState } from "../../../../../../../models/users/bets/pendingBets/pendingBetStates/v1/PendingBetState";
import { replacer } from "../../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePendingBetStateRestEndpoints(
    pendingBetState: PendingBetState, env: string, domain: string): Promise<PendingBetState>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBetStates/v1?env=${env}`;
    let data = JSON.stringify(pendingBetState as any, replacer);
    let createPendingBetStateResponse: AxiosResponse<PendingBetState> = await axios.post(url, data, config);
    let pendingBetState1 = new PendingBetState(createPendingBetStateResponse.data);
    return pendingBetState1;
}

export async function callReadPendingBetStateRestEndpointsByPendingBetStateId(
    pendingBetStateId: number, env: string, domain: string): Promise<PendingBetState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBetStates/v1?env=${env}&pendingBetStateId=${pendingBetStateId}`;
    let pendingBetStates: PendingBetState[] | undefined = [];
    try {
        let readPendingBetStateListResponse: AxiosResponse<PendingBetState[]> = await axios.get(url, config);
        let pendingBetStateList = readPendingBetStateListResponse.data;
        for(let i = 0; i < pendingBetStateList.length; i++) {
            pendingBetStates[i] = new PendingBetState(pendingBetStateList[i]);
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
    return pendingBetStates;
}

export async function callReadPendingBetStateRestEndpointsByPendingBetStateName(
    pendingBetStateName: string, env: string, domain: string): Promise<PendingBetState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBetStates/v1?env=${env}&pendingBetStateName=${pendingBetStateName}`;
    let pendingBetStates: PendingBetState[] | undefined = [];
    try {
        let readPendingBetStateListResponse: AxiosResponse<PendingBetState[]> = await axios.get(url, config);
        let pendingBetStateList = readPendingBetStateListResponse.data;
        for(let i = 0; i < pendingBetStateList.length; i++) {
            pendingBetStates[i] = new PendingBetState(pendingBetStateList[i]);
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
    return pendingBetStates;
}

export async function callUpdatePendingBetStateRestEndpoints(
    pendingBetState: PendingBetState, env: string, domain: string): Promise<PendingBetState[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBetStates/v1?env=${env}`;
    let data = JSON.stringify(pendingBetState as any, replacer);
    let updatePendingBetStateListResponse: AxiosResponse<PendingBetState[]> = await axios.put(url, data, config);
    let pendingBetStateList = updatePendingBetStateListResponse.data;
    let pendingBetStates: PendingBetState[] | undefined = [];
    for(let i = 0; i < pendingBetStateList.length; i++) {
        pendingBetStates[i] = new PendingBetState(pendingBetStateList[i]);
    }
    return pendingBetStates;
}

export async function callDeletePendingBetStateRestEndpointsByPendingBetStateId(
    pendingBetStateId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBetStates/v1?env=${env}&pendingBetStateId=${pendingBetStateId}` ;
    let deletePendingBetStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingBetStateResponse.status == 204;
}

export async function callDeletePendingBetStateRestEndpointsByPendingBetStateName(
    pendingBetStateName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBetStates/v1?env=${env}&pendingBetStateName=${pendingBetStateName}` ;
    let deletePendingBetStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingBetStateResponse.status == 204;
}